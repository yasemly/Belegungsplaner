package com.room.booking.presentation;

import com.room.booking.dao.RoomDao;
import com.room.booking.dao.RoomDaoImpl;
import com.room.booking.dao.BookingDao;
import com.room.booking.dao.BookingDaoImpl;
import com.room.booking.model.Room;
import com.room.booking.model.Booking;
import com.room.booking.model.User;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class AddBookingFrame extends JFrame {

    private User user;
    private JComboBox<Room> roomCombo;
    private JDateChooser dateChooser;
    private JTextField startTimeField;
    private JTextField endTimeField;
    private JTextField purposeField;

    private MultiSelectFeaturePanel featurePanel;
    private JTextField minRatingField;

    private JButton filterButton;
    private JButton bookButton;

    private RoomDao roomDao;
    private BookingDao bookingDao;

    private List<Room> allRooms;

    public AddBookingFrame(User user) {
        this.user = user;
        roomDao = new RoomDaoImpl();
        bookingDao = new BookingDaoImpl();
        allRooms = roomDao.getAllRooms();

        setTitle("Raum buchen");
        setSize(850, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel contentPanel = new JPanel(new BorderLayout(10,10));
        contentPanel.setBorder(new EmptyBorder(20,20,20,20));
        setContentPane(contentPanel);

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Raumfilter"));

        filterPanel.add(new JLabel("Ausstattung:"));
        featurePanel = new MultiSelectFeaturePanel();
        filterPanel.add(featurePanel);

        filterPanel.add(new JLabel("Mindestbewertung:"));
        minRatingField = new JTextField(4);
        filterPanel.add(minRatingField);

        filterButton = new JButton("Filter anwenden");
        filterButton.addActionListener((final ActionEvent event) -> {
            applyFilters(minRatingField.getText().trim());
        });
        filterPanel.add(filterButton);
        contentPanel.add(filterPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8,8,8,8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(new JLabel("Wähle Raum:"), gbc);
        gbc.gridx = 1;
        roomCombo = new JComboBox<>();
        loadRoomCombo(allRooms);
        centerPanel.add(roomCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        centerPanel.add(new JLabel("Datum:"), gbc);
        gbc.gridx = 1;
        dateChooser = new JDateChooser();
        centerPanel.add(dateChooser, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        centerPanel.add(new JLabel("Startzeit (HH:mm):"), gbc);
        gbc.gridx = 1;
        startTimeField = new JTextField("10:00", 10);
        centerPanel.add(startTimeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        centerPanel.add(new JLabel("Endzeit (HH:mm):"), gbc);
        gbc.gridx = 1;
        endTimeField = new JTextField("11:00", 10);
        centerPanel.add(endTimeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        centerPanel.add(new JLabel("Zweck:"), gbc);
        gbc.gridx = 1;
        purposeField = new JTextField("Meeting", 10);
        centerPanel.add(purposeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        bookButton = new JButton("Buchen");
        bookButton.addActionListener((final ActionEvent event) -> handleBook());
        centerPanel.add(bookButton, gbc);

        contentPanel.add(centerPanel, BorderLayout.CENTER);
    }

    private void loadRoomCombo(List<Room> rooms) {
        roomCombo.removeAllItems();
        for (Room r : rooms) {
            roomCombo.addItem(r);
        }
    }

    private void applyFilters(String minRatingText) {
        final String featureFilter = String.join(",", featurePanel.getSelectedFeatures()).toLowerCase();
        double minRating = 0;
        if (!minRatingText.isEmpty()) {
            try {
                minRating = Double.parseDouble(minRatingText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Bitte geben Sie eine gültige Zahl für die Mindestbewertung ein.", "Fehler", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        final double finalMinRating = minRating;
        List<Room> filtered = allRooms;
        if (!featureFilter.isEmpty()) {
            filtered = filtered.stream()
                    .filter(room -> room.getFeatures().stream().anyMatch(f -> f.toLowerCase().contains(featureFilter)))
                    .collect(Collectors.toList());
        }
        if (finalMinRating > 0) {
            filtered = filtered.stream()
                    .filter(room -> room.getRating() >= finalMinRating)
                    .collect(Collectors.toList());
        }
        loadRoomCombo(filtered);
        if (filtered.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Keine Räume gefunden, die diesen Kriterien entsprechen.");
        }
    }

    private void handleBook() {
        try {
            Room selectedRoom = (Room) roomCombo.getSelectedItem();
            if (selectedRoom == null || dateChooser.getDate() == null) {
                JOptionPane.showMessageDialog(this, "Bitte wählen Sie Raum und Datum aus.");
                return;
            }
            LocalDate localDate = new Date(dateChooser.getDate().getTime()).toLocalDate();
            LocalTime start = LocalTime.parse(startTimeField.getText().trim());
            LocalTime end = LocalTime.parse(endTimeField.getText().trim());
            if (end.isBefore(start)) {
                JOptionPane.showMessageDialog(this, "Endzeit darf nicht vor Startzeit liegen.");
                return;
            }
            LocalDateTime startDateTime = LocalDateTime.of(localDate, start);
            LocalDateTime endDateTime = LocalDateTime.of(localDate, end);
            // Prüfe, ob bereits eine Buchung in diesem Zeitraum existiert
            List<Booking> existingBookings = bookingDao.getBookingsByUserId(user.getUserId());
            boolean overlap = existingBookings.stream().anyMatch(b ->
                    !b.getEndTime().isBefore(startDateTime) && !b.getStartTime().isAfter(endDateTime)
            );
            if (overlap) {
                JOptionPane.showMessageDialog(this, "Sie haben bereits eine Buchung in diesem Zeitraum.");
                return;
            }
            String purpose = purposeField.getText().trim();
            bookingDao.createBooking(user.getUserId(), selectedRoom.getRoomId(), startDateTime, endDateTime, purpose, "Confirmed");
            JOptionPane.showMessageDialog(this, "Raum erfolgreich gebucht!");
            new UserOverviewFrame(user).setVisible(true);
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Fehler bei der Buchung: " + ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        User dummyUser = new User(1, "alice", "Alice Doe", "alice@example.com", "passw0rd");
        SwingUtilities.invokeLater(() -> new AddBookingFrame(dummyUser).setVisible(true));
    }
}
