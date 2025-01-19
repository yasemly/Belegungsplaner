package com.room.booking.presentation;

import com.room.booking.dao.BookingDao;
import com.room.booking.dao.BookingDaoImpl;
import com.room.booking.dao.RoomDao;
import com.room.booking.dao.RoomDaoImpl;
import com.room.booking.model.Booking;
import com.room.booking.model.Room;
import com.room.booking.model.User;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Frame for creating a new booking:
 * 1) Pick a room (from a dropdown)
 * 2) Pick a date (from a calendar JDateChooser)
 * 3) Check availability -> show free time slots
 * 4) Book the selected slot
 */
public class AddBookingFrame extends JFrame {

    private final User user;          // The currently logged-in user
    private final RoomDao roomDao;    // For loading room list
    private final BookingDao bookingDao; // For checking existing bookings & creating new ones

    private JComboBox<Room> roomCombo;
    private JDateChooser dateChooser;
    private JButton checkAvailabilityButton;
    private JComboBox<String> timeSlotCombo;
    private JButton bookButton;

    public AddBookingFrame(User user) {
        this.user = user;
        this.roomDao = new RoomDaoImpl();
        this.bookingDao = new BookingDaoImpl();

        setTitle("Book a Room");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 10, 10));

        // 1) Dropdown for rooms
        add(new JLabel("Select Room:"));
        roomCombo = new JComboBox<>();
        loadRoomsIntoCombo();  // fill from DB
        add(roomCombo);

        // 2) Date chooser (requires JCalendar)
        add(new JLabel("Select Date:"));
        dateChooser = new JDateChooser();
        add(dateChooser);

        // 3) Check availability button
        checkAvailabilityButton = new JButton("Check Availability");
        checkAvailabilityButton.addActionListener(this::handleCheckAvailability);
        add(checkAvailabilityButton);

        // 4) Free time slots combo
        add(new JLabel("Available Time Slots:"));
        timeSlotCombo = new JComboBox<>();
        add(timeSlotCombo);

        // 5) "Book Now" button
        bookButton = new JButton("Book Now");
        bookButton.addActionListener(this::handleBookRoom);
        add(bookButton);

        // Just a filler panel or label to keep layout balanced
        add(new JPanel());
    }

    /**
     * Loads all rooms from the DB and populates the combo box.
     */
    private void loadRoomsIntoCombo() {
        List<Room> rooms = roomDao.getAllRooms();
        for (Room r : rooms) {
            roomCombo.addItem(r);
        }
    }

    /**
     * When user clicks "Check Availability", we:
     * 1) See which room is selected
     * 2) See which date is picked
     * 3) Query existing bookings for that room+date
     * 4) Build a list of free time slots (hourly for simplicity)
     * 5) Populate timeSlotCombo with free slots
     */
    private void handleCheckAvailability(ActionEvent e) {
        // 1) get selected room
        Room selectedRoom = (Room) roomCombo.getSelectedItem();
        if (selectedRoom == null) {
            JOptionPane.showMessageDialog(this, "Please select a room.");
            return;
        }

        // 2) get date from JDateChooser
        if (dateChooser.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please select a date.");
            return;
        }
        // Convert java.util.Date -> LocalDate
        LocalDate selectedDate = new java.sql.Date(dateChooser.getDate().getTime()).toLocalDate();

        // 3) get existing bookings
        List<Booking> existingBookings = bookingDao.getBookingsForRoomOnDate(
                selectedRoom.getRoomId(), selectedDate);

        // 4) Build time slots from 8:00 to 17:00
        List<LocalTime> allPossibleSlots = new ArrayList<>();
        for (int hour = 8; hour < 18; hour++) {
            allPossibleSlots.add(LocalTime.of(hour, 0));
        }

        // Remove any slot that overlaps an existing booking
        // We'll do a simple approach: if a booking is from 9:15 to 10:45, we remove 9:00 and 10:00 from free list
        List<LocalTime> freeSlots = new ArrayList<>(allPossibleSlots);
        for (Booking b : existingBookings) {
            LocalTime startHour = b.getStartTime().toLocalTime().withMinute(0);
            while (!startHour.isAfter(b.getEndTime().toLocalTime())) {
                freeSlots.remove(startHour);
                startHour = startHour.plusHours(1);
            }
        }

        // 5) Fill timeSlotCombo with the free slots
        timeSlotCombo.removeAllItems();
        if (freeSlots.isEmpty()) {
            timeSlotCombo.addItem("No free slots");
        } else {
            for (LocalTime slot : freeSlots) {
                timeSlotCombo.addItem(slot.toString()); // e.g. "08:00"
            }
        }
    }

    /**
     * When user clicks "Book Now":
     * 1) get selected room
     * 2) get date
     * 3) get timeslot
     * 4) create booking
     */
    private void handleBookRoom(ActionEvent e) {
        Room selectedRoom = (Room) roomCombo.getSelectedItem();
        if (selectedRoom == null) {
            JOptionPane.showMessageDialog(this, "No room selected.");
            return;
        }

        if (dateChooser.getDate() == null) {
            JOptionPane.showMessageDialog(this, "No date selected.");
            return;
        }
        LocalDate selectedDate = new java.sql.Date(dateChooser.getDate().getTime()).toLocalDate();

        String timeSlotStr = (String) timeSlotCombo.getSelectedItem();
        if (timeSlotStr == null || timeSlotStr.equals("No free slots")) {
            JOptionPane.showMessageDialog(this, "No valid time slot selected.");
            return;
        }

        // We'll assume a 1-hour booking block from e.g. 09:00 to 10:00
        LocalTime startTime = LocalTime.parse(timeSlotStr);
        LocalTime endTime = startTime.plusHours(1);

        LocalDateTime startDateTime = LocalDateTime.of(selectedDate, startTime);
        LocalDateTime endDateTime   = LocalDateTime.of(selectedDate, endTime);

        // Insert booking
        // e.g. "Meeting" as a placeholder purpose, "Confirmed" as status
        bookingDao.createBooking(
                user.getUserId(),
                selectedRoom.getRoomId(),
                startDateTime,
                endDateTime,
                "Meeting",
                "Confirmed"
        );

        JOptionPane.showMessageDialog(this, "Booking created successfully!");

        // Optionally go back to the user overview
        new UserOverviewFrame(user).setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        // For testing
        // You might have a real "User" from your login
        User dummyUser = new User(1, "testuser", "Test User", "test@example.com", "pass123");
        SwingUtilities.invokeLater(() -> {
            AddBookingFrame frame = new AddBookingFrame(dummyUser);
            frame.setVisible(true);
        });
    }
}
