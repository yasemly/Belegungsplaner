package com.room.booking.presentation;

import com.room.booking.dao.BookingDao;
import com.room.booking.dao.BookingDaoImpl;
import com.room.booking.dao.UserDao;
import com.room.booking.dao.UserDaoImpl;
import com.room.booking.model.Booking;
import com.room.booking.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UserOverviewFrame extends JFrame {

    private User user;
    private BookingDao bookingDao;

    public UserOverviewFrame(User user) {
        this.user = user;
        this.bookingDao = new BookingDaoImpl();

        setTitle("User Overview - Existing Bookings");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTextArea bookingsTextArea = new JTextArea();
        bookingsTextArea.setEditable(false);
        bookingsTextArea.append("Existing Bookings:\n");

        // Fetch bookings for the user
        try {
            List<Booking> bookings = bookingDao.getBookingsByUserId(user.getUserId());
            for (Booking booking : bookings) {
                bookingsTextArea.append("Booking ID: " + booking.getBookingId() + "\n");
                bookingsTextArea.append("Room ID: " + booking.getRoomId() + "\n");
                bookingsTextArea.append("Start: " + booking.getStartTime() + "\n");
                bookingsTextArea.append("End: " + booking.getEndTime() + "\n");
                bookingsTextArea.append("Purpose: " + booking.getPurpose() + "\n");
                bookingsTextArea.append("Status: " + booking.getStatus() + "\n\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
            bookingsTextArea.append("Error retrieving bookings.\n");
        }

        JScrollPane scrollPane = new JScrollPane(bookingsTextArea);
        add(scrollPane, BorderLayout.CENTER);

        JButton createBookingButton = new JButton("Create New Booking");
        createBookingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openCreateBookingFrame();
            }
        });
        add(createBookingButton, BorderLayout.SOUTH);
    }

    private void openCreateBookingFrame() {
        // Instantiate UserSearchFrame with user and show it
        UserSearchFrame createBookingFrame = new UserSearchFrame(user);
        createBookingFrame.setVisible(true);
        this.dispose(); // Close current frame
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserDao userDao = new UserDaoImpl();
            User user = userDao.getUserById(1); // Replace with actual user ID retrieval logic
            if (user != null) {
                UserOverviewFrame overviewFrame = new UserOverviewFrame(user);
                overviewFrame.setVisible(true);
            } else {
                System.out.println("User not found!");
            }
        });
    }
}
