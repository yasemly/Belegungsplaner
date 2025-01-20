package com.room.booking.presentation;

import com.room.booking.dao.BookingDao;
import com.room.booking.dao.BookingDaoImpl;
import com.room.booking.model.IBookable;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class BookingEditDialog extends JDialog {

    private int bookingId;
    private IBookable bookable; // Kann User oder Employer sein
    private JDateChooser dateChooser;
    private JTextField startTimeField;
    private JTextField endTimeField;
    private JTextField purposeField;
    private JButton saveButton;
    private JButton cancelButton;

    private BookingDao bookingDao;

    public BookingEditDialog(Frame owner, int bookingId, IBookable bookable) {
        super(owner, "Buchung bearbeiten", true);
        this.bookingId = bookingId;
        this.bookable = bookable;
        bookingDao = new BookingDaoImpl();
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10,10));
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(10,10,10,10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8,8,8,8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Datum auswählen
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(new JLabel("Datum:"), gbc);
        gbc.gridx = 1;
        dateChooser = new JDateChooser();
        mainPanel.add(dateChooser, gbc);

        // Startzeit
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(new JLabel("Startzeit (HH:mm):"), gbc);
        gbc.gridx = 1;
        startTimeField = new JTextField("10:00", 10);
        mainPanel.add(startTimeField, gbc);

        // Endzeit
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(new JLabel("Endzeit (HH:mm):"), gbc);
        gbc.gridx = 1;
        endTimeField = new JTextField("11:00", 10);
        mainPanel.add(endTimeField, gbc);

        // Zweck
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(new JLabel("Zweck:"), gbc);
        gbc.gridx = 1;
        purposeField = new JTextField();
        mainPanel.add(purposeField, gbc);

        add(mainPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        saveButton = new JButton("Speichern");
        cancelButton = new JButton("Abbrechen");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        saveButton.addActionListener((ActionEvent e) -> {
            if (dateChooser.getDate() == null) {
                JOptionPane.showMessageDialog(this, "Bitte wählen Sie ein Datum.");
                return;
            }
            LocalDate date = new Date(dateChooser.getDate().getTime()).toLocalDate();
            LocalTime start, end;
            try {
                start = LocalTime.parse(startTimeField.getText().trim());
                end = LocalTime.parse(endTimeField.getText().trim());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Bitte gültiges Zeitformat eingeben (HH:mm).");
                return;
            }
            if (end.isBefore(start)) {
                JOptionPane.showMessageDialog(this, "Endzeit darf nicht vor Startzeit liegen.");
                return;
            }
            LocalDateTime startDateTime = LocalDateTime.of(date, start);
            LocalDateTime endDateTime = LocalDateTime.of(date, end);
            String purpose = purposeField.getText().trim();
            bookingDao.updateBooking(bookingId, startDateTime, endDateTime, purpose, "Confirmed");
            JOptionPane.showMessageDialog(this, "Buchung aktualisiert.");
            dispose();
        });
        cancelButton.addActionListener(e -> dispose());

        pack();
        setLocationRelativeTo(getOwner());
    }

    public static void main(String[] args) {
        com.room.booking.model.User dummyUser = new com.room.booking.model.User(1, "alice", "Alice Doe", "alice@example.com", "passw0rd");
        SwingUtilities.invokeLater(() -> {
            BookingEditDialog dialog = new BookingEditDialog(null, 123, dummyUser);
            dialog.setVisible(true);
        });
    }
}
