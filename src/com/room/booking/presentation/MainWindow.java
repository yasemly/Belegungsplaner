package com.room.booking.presentation;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    public MainWindow() {
        setTitle("Booking System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        JButton bookRoomButton = new JButton("Book a Room");
        JButton addRoomButton = new JButton("Add a Room");
        bookRoomButton.addActionListener(b -> {
            BookingForm bookingForm = new BookingForm();
            bookingForm.setVisible(true);
        });
        panel.add(bookRoomButton, BorderLayout.NORTH);
        panel.add(addRoomButton, BorderLayout.SOUTH);

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainWindow mainWindow = new MainWindow();
            mainWindow.setVisible(true);
        });
    }
}
