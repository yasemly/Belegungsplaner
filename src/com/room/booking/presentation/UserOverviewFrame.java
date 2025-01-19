package com.room.booking.presentation;

import com.room.booking.dao.BookingDao;
import com.room.booking.dao.BookingDaoImpl;
import com.room.booking.model.Booking;
import com.room.booking.model.User;
import com.room.booking.model.Room;
import com.room.booking.dao.RoomDao;
import com.room.booking.dao.RoomDaoImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * A nicer user overview: shows bookings in a table + a button to create a new booking.
 */
public class UserOverviewFrame extends JFrame {

    private User user;
    private BookingDao bookingDao;
    private RoomDao roomDao;

    private JTable bookingTable;
    private DefaultTableModel tableModel;

    public UserOverviewFrame(User user) {
        this.user = user;
        this.bookingDao = new BookingDaoImpl();
        this.roomDao = new RoomDaoImpl();

        setTitle("User Overview - Bookings");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // center on screen

        // Layout: BorderLayout (table in center, button panel at bottom)
        setLayout(new BorderLayout(10, 10));

        // 1) Create table model
        tableModel = new DefaultTableModel(new Object[] {
                "Booking ID", "Room", "Start Time", "End Time", "Purpose", "Status"
        }, 0);  // 0 = no rows initially

        // 2) Create table from model
        bookingTable = new JTable(tableModel);
        bookingTable.setFillsViewportHeight(true);

        // 3) Put table in a scroll pane
        JScrollPane scrollPane = new JScrollPane(bookingTable);
        add(scrollPane, BorderLayout.CENTER);

        // 4) Load user’s bookings into table
        loadBookingsIntoTable();

        // 5) Bottom panel with a button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton newBookingButton = new JButton("Book a Room");
        newBookingButton.addActionListener(e -> {
            // Open a new booking frame
            new AddBookingFrame(user).setVisible(true);
            // We can either keep this overview open or close it:
            // dispose();
        });
        bottomPanel.add(newBookingButton);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadBookingsIntoTable() {
        // Clear old rows if any
        tableModel.setRowCount(0);

        // Get the user’s bookings
        List<Booking> bookings = bookingDao.getBookingsByUserId(user.getUserId());
        for (Booking b : bookings) {
            // Optional: fetch room name from roomDao if you want to display the room’s name
            String roomName = "Room #" + b.getRoomId();
            try {
                Room room = roomDao.getRoomByName("???"); // If you have a method to get name by ID or vice versa
                // Actually, you might want a getRoomById(...) method instead:
                // Room room = roomDao.getRoomById(b.getRoomId());
                // if (room != null) roomName = room.getRoomName();
            } catch (Exception ex) {
                // or ignore
            }

            // For now let's just show b.getRoomId():
            // If you have a getRoomById(...) method, do that for a nicer name
            Object[] rowData = {
                    b.getBookingId(),
                    b.getRoomId(), // or roomName
                    b.getStartTime(),
                    b.getEndTime(),
                    b.getPurpose(),
                    b.getStatus()
            };
            tableModel.addRow(rowData);
        }
    }

    public static void main(String[] args) {
        // Quick test
        User dummyUser = new User(1, "alice", "Alice Doe", "alice@example.com", "pw");
        UserOverviewFrame frame = new UserOverviewFrame(dummyUser);
        frame.setVisible(true);
    }
}
