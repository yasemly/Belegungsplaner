package com.room.booking.presentation;

import com.room.booking.dao.BookingDao;
import com.room.booking.dao.BookingDaoImpl;
import com.room.booking.dao.RoomDao;
import com.room.booking.dao.RoomDaoImpl;
import com.room.booking.model.Booking;
import com.room.booking.model.Room;
import com.room.booking.model.User;

// If you're using JDateChooser from JCalendar:
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class AddBookingFrame extends JFrame {

    private User user;

    private JComboBox<Room> roomCombo;
    private JDateChooser dateChooser;
    private JTextField startTimeField;  // e.g. "10:00"
    private JTextField endTimeField;    // e.g. "11:00"
    private JTextField purposeField;

    private JButton checkAvailabilityButton;
    private JButton bookButton;

    private BookingDao bookingDao;
    private RoomDao roomDao;

    public AddBookingFrame(User user) {
        this.user = user;

        bookingDao = new BookingDaoImpl();
        roomDao = new RoomDaoImpl();

        setTitle("Book a Room");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Use GridBagLayout for a nicer alignment
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);  // padding around components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 0: "Select Room:" + combo
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Select Room:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        roomCombo = new JComboBox<>();
        loadRoomsIntoCombo();
        add(roomCombo, gbc);

        // Row 1: "Select Date:" + JDateChooser
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Select Date:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        dateChooser = new JDateChooser();
        add(dateChooser, gbc);

        // Row 2: "Start Time:" + field
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Start Time (HH:mm):"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        startTimeField = new JTextField("10:00", 10);
        add(startTimeField, gbc);

        // Row 3: "End Time:" + field
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("End Time (HH:mm):"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        endTimeField = new JTextField("11:00", 10);
        add(endTimeField, gbc);

        // Row 4: "Purpose:" + field
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Purpose:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        purposeField = new JTextField("Meeting", 10);
        add(purposeField, gbc);

        // Row 5: "Check Availability" + "Book" button
        gbc.gridx = 0;
        gbc.gridy = 5;
        checkAvailabilityButton = new JButton("Check Availability");
        checkAvailabilityButton.addActionListener(this::handleCheckAvailability);
        add(checkAvailabilityButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        bookButton = new JButton("Book");
        bookButton.addActionListener(this::handleBook);
        add(bookButton, gbc);

        // That’s it!
    }

    private void loadRoomsIntoCombo() {
        List<Room> rooms = roomDao.getAllRooms();
        for (Room room : rooms) {
            roomCombo.addItem(room);
        }
    }

    /**
     * Optional: a method that checks if the chosen time overlaps existing bookings.
     * If you want advanced logic, you'd implement it here.
     */
    private void handleCheckAvailability(ActionEvent e) {
        Room selectedRoom = (Room) roomCombo.getSelectedItem();
        if (selectedRoom == null) {
            JOptionPane.showMessageDialog(this, "Please select a room");
            return;
        }
        if (dateChooser.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please select a date");
            return;
        }
        // Convert to LocalDate
        LocalDate localDate = new Date(dateChooser.getDate().getTime()).toLocalDate();

        // Parse times
        LocalTime startT;
        LocalTime endT;
        try {
            startT = LocalTime.parse(startTimeField.getText().trim());
            endT   = LocalTime.parse(endTimeField.getText().trim());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid time format (use HH:mm)", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create potential start/end
        LocalDateTime startDateTime = LocalDateTime.of(localDate, startT);
        LocalDateTime endDateTime   = LocalDateTime.of(localDate, endT);

        // Query existing bookings, see if overlap
        List<Booking> existing = bookingDao.getBookingsByUserId(user.getUserId());
        // Or better: bookingDao.getBookingsForRoomOnDate(...).
        // We'll do something simplistic here:
        for (Booking b : existing) {
            if (b.getRoomId() == selectedRoom.getRoomId()) {
                // If there's an overlap, show a warning
                boolean overlap =
                        !b.getEndTime().isBefore(startDateTime) && // b ends after new start
                                !b.getStartTime().isAfter(endDateTime);    // b starts before new end
                if (overlap) {
                    JOptionPane.showMessageDialog(this, "That slot overlaps with an existing booking (ID " + b.getBookingId() + ")", "Overlap", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
        }

        JOptionPane.showMessageDialog(this, "No overlap detected. Looks available!");
    }

    private void handleBook(ActionEvent e) {
        try {
            Room selectedRoom = (Room) roomCombo.getSelectedItem();
            if (selectedRoom == null) {
                JOptionPane.showMessageDialog(this, "Please select a room");
                return;
            }

            if (dateChooser.getDate() == null) {
                JOptionPane.showMessageDialog(this, "Please select a date");
                return;
            }
            LocalDate localDate = new Date(dateChooser.getDate().getTime()).toLocalDate();

            LocalTime startT = LocalTime.parse(startTimeField.getText().trim());
            LocalTime endT   = LocalTime.parse(endTimeField.getText().trim());
            if (endT.isBefore(startT)) {
                JOptionPane.showMessageDialog(this, "End time can't be before start time.");
                return;
            }
            LocalDateTime startDateTime = LocalDateTime.of(localDate, startT);
            LocalDateTime endDateTime   = LocalDateTime.of(localDate, endT);

            String purpose = purposeField.getText().trim();
            bookingDao.createBooking(
                    user.getUserId(),
                    selectedRoom.getRoomId(),
                    startDateTime,
                    endDateTime,
                    purpose,
                    "Confirmed"
            );

            JOptionPane.showMessageDialog(this, "Room booked successfully!");
            // Optionally close or go back to user overview
            // e.g.:
            new UserOverviewFrame(user).setVisible(true);
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error booking room: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // For testing
        User dummyUser = new User(1, "alice", "Alice", "alice@example.com", "pw");
        AddBookingFrame f = new AddBookingFrame(dummyUser);
        f.setVisible(true);
    }
}
