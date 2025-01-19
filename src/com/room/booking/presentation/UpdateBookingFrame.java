package com.room.booking.presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Frame zum Aktualisieren von Buchungsinformationen.
 */
public class UpdateBookingFrame extends JFrame {

    private JTextField bookingIdField;
    private JTextField roomIdField;
    private JTextField userIdField;
    private JTextField startTimeField;
    private JTextField endTimeField;
    private JTextField purposeField;
    private JTextField statusField;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public UpdateBookingFrame() {
        setTitle("Buchung aktualisieren");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 2, 10, 10));

        panel.add(new JLabel("Buchungs-ID:"));
        bookingIdField = new JTextField();
        panel.add(bookingIdField);

        panel.add(new JLabel("Raum-ID:"));
        roomIdField = new JTextField();
        panel.add(roomIdField);

        panel.add(new JLabel("Benutzer-ID:"));
        userIdField = new JTextField();
        panel.add(userIdField);

        panel.add(new JLabel("Startzeit (yyyy-MM-dd HH:mm):"));
        startTimeField = new JTextField();
        panel.add(startTimeField);

        panel.add(new JLabel("Endzeit (yyyy-MM-dd HH:mm):"));
        endTimeField = new JTextField();
        panel.add(endTimeField);

        panel.add(new JLabel("Zweck:"));
        purposeField = new JTextField();
        panel.add(purposeField);

        panel.add(new JLabel("Status:"));
        statusField = new JTextField();
        panel.add(statusField);

        JButton updateButton = new JButton("Aktualisieren");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBooking();
            }
        });
        panel.add(updateButton);

        add(panel);
    }

    private void updateBooking() {
        try {
            int bookingId = Integer.parseInt(bookingIdField.getText());
            int roomId = Integer.parseInt(roomIdField.getText());
            int userId = Integer.parseInt(userIdField.getText());
            LocalDateTime startTime = LocalDateTime.parse(startTimeField.getText(), formatter);
            LocalDateTime endTime = LocalDateTime.parse(endTimeField.getText(), formatter);
            String purpose = purposeField.getText().trim();
            String status = statusField.getText().trim();

            // DB or service call here
            JOptionPane.showMessageDialog(this, "Buchung mit ID " + bookingId + " aktualisiert.");
            clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Bitte geben Sie gültige Werte ein. " + e.getMessage());
        }
    }

    private void clearFields() {
        bookingIdField.setText("");
        roomIdField.setText("");
        userIdField.setText("");
        startTimeField.setText("");
        endTimeField.setText("");
        purposeField.setText("");
        statusField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UpdateBookingFrame frame = new UpdateBookingFrame();
            frame.setVisible(true);
        });
    }
}
