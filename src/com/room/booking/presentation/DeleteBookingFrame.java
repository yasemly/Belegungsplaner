package com.room.booking.presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Frame zum Löschen einer bestehenden Buchung.
 */
public class DeleteBookingFrame extends JFrame {

    private JTextField bookingIdField;

    public DeleteBookingFrame() {
        setTitle("Buchung löschen");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2, 10, 10));

        panel.add(new JLabel("Buchungs-ID:"));
        bookingIdField = new JTextField();
        panel.add(bookingIdField);

        JButton deleteButton = new JButton("Löschen");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteBooking();
            }
        });
        panel.add(deleteButton);

        add(panel);
    }

    private void deleteBooking() {
        try {
            int bookingId = Integer.parseInt(bookingIdField.getText());
            // DB logic or service call
            JOptionPane.showMessageDialog(this, "Buchung mit ID " + bookingId + " gelöscht.");
            clearFields();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Bitte geben Sie eine gültige Buchungs-ID ein.");
        }
    }

    private void clearFields() {
        bookingIdField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DeleteBookingFrame frame = new DeleteBookingFrame();
            frame.setVisible(true);
        });
    }
}
