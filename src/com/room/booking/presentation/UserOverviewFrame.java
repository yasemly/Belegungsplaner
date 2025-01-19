package com.room.booking.presentation;

import com.room.booking.dao.BookingDao;
import com.room.booking.dao.BookingDaoImpl;
import com.room.booking.model.Booking;
import com.room.booking.model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Shows the user's existing bookings right away,
 * plus a button to create another booking.
 */
public class UserOverviewFrame extends JFrame {

    private User user;
    private BookingDao bookingDao;

    public UserOverviewFrame(User user) {
        this.user = user;
        this.bookingDao = new BookingDaoImpl();

        setTitle("User Overview");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTextArea bookingsArea = new JTextArea();
        bookingsArea.setEditable(false);
        bookingsArea.append("Your existing bookings:\n\n");

        try {
            // BookingDao has getBookingsByUserId(int userId)
            List<Booking> bookings = bookingDao.getBookingsByUserId(user.getUserId());
            if (bookings.isEmpty()) {
                bookingsArea.append("No bookings yet.\n");
            } else {
                for (Booking b : bookings) {
                    bookingsArea.append("Booking ID: " + b.getBookingId() + "\n");
                    bookingsArea.append("Room ID: " + b.getRoomId() + "\n");
                    bookingsArea.append("Start: " + b.getStartTime() + "\n");
                    bookingsArea.append("End: " + b.getEndTime() + "\n");
                    bookingsArea.append("Purpose: " + b.getPurpose() + "\n");
                    bookingsArea.append("Status: " + b.getStatus() + "\n\n");
                }
            }
        } catch (Exception e) {
            bookingsArea.append("Error loading bookings.\n" + e.getMessage());
        }

        add(new JScrollPane(bookingsArea), BorderLayout.CENTER);

        // Button to create a new booking
        JButton newBookingButton = new JButton("Create New Booking");
        newBookingButton.addActionListener(e -> {
            // For instance, open an AddBookingFrame or a UserSearchFrame
            new AddBookingFrame(user).setVisible(true);
            dispose(); // close this frame
        });
        add(newBookingButton, BorderLayout.SOUTH);
    }
}
