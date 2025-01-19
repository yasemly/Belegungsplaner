package com.room.booking.presentation;

import com.room.booking.dao.BookingDao;
import com.room.booking.dao.BookingDaoImpl;
import com.room.booking.model.Booking;
import com.room.booking.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;

public class BookingManagementDialog extends JDialog {
    private int bookingId;
    private User user;
    private BookingDao bookingDao;

    private JTextField startTimeField;
    private JTextField endTimeField;
    private JTextField purposeField;

    public BookingManagementDialog(Frame parent, int bookingId, User user) {
        super(parent, "Buchung bearbeiten / löschen", true);
        this.bookingId = bookingId;
        this.user = user;
        bookingDao = new BookingDaoImpl();

        setSize(450, 300);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Startzeit (z.B. 2025-01-20T10:00):"), gbc);
        startTimeField = new JTextField(20);
        gbc.gridx = 1;
        add(startTimeField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Endzeit (z.B. 2025-01-20T11:00):"), gbc);
        endTimeField = new JTextField(20);
        gbc.gridx = 1;
        add(endTimeField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Zweck:"), gbc);
        purposeField = new JTextField(20);
        gbc.gridx = 1;
        add(purposeField, gbc);

        // Vorbefüllung mit bestehenden Daten:
        Booking existing = bookingDao.getBookingById(bookingId);
        if (existing != null) {
            startTimeField.setText(existing.getStartTime().toString());
            endTimeField.setText(existing.getEndTime().toString());
            purposeField.setText(existing.getPurpose());
        }

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton updateButton = new JButton("Aktualisieren");
        updateButton.addActionListener(this::handleUpdate);
        JButton deleteButton = new JButton("Löschen");
        deleteButton.addActionListener(this::handleDelete);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        add(buttonPanel, gbc);
    }

    private void handleUpdate(ActionEvent e) {
        try {
            LocalDateTime start = LocalDateTime.parse(startTimeField.getText().trim());
            LocalDateTime end = LocalDateTime.parse(endTimeField.getText().trim());
            String purpose = purposeField.getText().trim();

            // Führe das Update aus:
            bookingDao.updateBooking(bookingId, start, end, purpose, "Confirmed");

            // Überprüfe ggf.:
            Booking updated = bookingDao.getBookingById(bookingId);
            System.out.println("Aktualisierte Buchung: " + updated);

            JOptionPane.showMessageDialog(this, "Buchung aktualisiert.");
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Fehler beim Aktualisieren: " + ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleDelete(ActionEvent e) {
        int confirm = JOptionPane.showConfirmDialog(this, "Bist du sicher, dass du die Buchung löschen möchtest?", "Löschen bestätigen", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            bookingDao.deleteBooking(bookingId);
            JOptionPane.showMessageDialog(this, "Buchung gelöscht.");
            dispose();
        }
    }
}
