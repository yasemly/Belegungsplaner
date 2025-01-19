package com.room.booking.presentation;

import com.room.booking.dao.BookingDao;
import com.room.booking.dao.BookingDaoImpl;
import com.room.booking.dao.RoomDao;
import com.room.booking.dao.RoomDaoImpl;
import com.room.booking.model.Booking;
import com.room.booking.model.Room;
import com.room.booking.model.User;
import com.room.booking.util.DateTimePicker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * GUI-Frame für die Benutzersuche nach verfügbaren Räumen und die Buchung eines Raums.
 */
public class UserSearchFrame extends JFrame {

    private JTextField capacityField;
    private JTextField locationField;
    private DateTimePicker availableFromPicker;
    private DateTimePicker availableToPicker;
    private JButton searchButton;
    private JButton bookButton;
    private JTable resultsTable;
    private DefaultTableModel tableModel;

    private RoomDao roomDao;
    private BookingDao bookingDao;
    private User currentUser;

    private List<JCheckBox> featureCheckBoxes;
    private JTextField ratingField;
    private JTextField floorField;

    public UserSearchFrame(User user) {
        this.currentUser = user;
        this.roomDao = new RoomDaoImpl();
        this.bookingDao = new BookingDaoImpl();

        setTitle("Räume suchen");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel searchPanel = createSearchPanel();
        add(searchPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[][]{},
                new String[]{"Raumname", "Kapazität", "Ausstattung", "Standort"});
        resultsTable = new JTable(tableModel);
        add(new JScrollPane(resultsTable), BorderLayout.CENTER);

        JPanel bookPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bookButton = new JButton("Diesen Raum buchen");
        bookPanel.add(bookButton);
        add(bookPanel, BorderLayout.SOUTH);

        searchButton.addActionListener(e -> searchRooms());
        bookButton.addActionListener(e -> bookRoom());
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new GridBagLayout());
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        searchPanel.add(new JLabel("Kapazität:"), gbc);
        gbc.gridx = 1;
        capacityField = new JTextField(10);
        searchPanel.add(capacityField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        searchPanel.add(new JLabel("Standort:"), gbc);
        gbc.gridx = 1;
        locationField = new JTextField(20);
        searchPanel.add(locationField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        searchPanel.add(new JLabel("Verfügbar ab:"), gbc);
        gbc.gridx = 1;
        availableFromPicker = new DateTimePicker();
        searchPanel.add(availableFromPicker, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        searchPanel.add(new JLabel("Verfügbar bis:"), gbc);
        gbc.gridx = 1;
        availableToPicker = new DateTimePicker();
        searchPanel.add(availableToPicker, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        searchPanel.add(new JLabel("Ausstattung:"), gbc);
        gbc.gridx = 1;
        JPanel featuresPanel = new JPanel(new GridLayout(0, 1));
        featureCheckBoxes = new ArrayList<>();
        String[] features = {"Beamer", "Blackboard", "Whiteboard", "WLAN", "Klimaanlage"};
        for (String feature : features) {
            JCheckBox checkBox = new JCheckBox(feature);
            featureCheckBoxes.add(checkBox);
            featuresPanel.add(checkBox);
        }
        searchPanel.add(featuresPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        searchPanel.add(new JLabel("Bewertung:"), gbc);
        gbc.gridx = 1;
        ratingField = new JTextField(10);
        searchPanel.add(ratingField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        searchPanel.add(new JLabel("Stockwerk:"), gbc);
        gbc.gridx = 1;
        floorField = new JTextField(10);
        searchPanel.add(floorField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        searchButton = new JButton("Suchen");
        searchPanel.add(searchButton, gbc);

        return searchPanel;
    }

    private void searchRooms() {
        String capacityText = capacityField.getText();
        String location = locationField.getText();

        LocalDateTime availableFromDate = getLocalDateTimeFromDate(availableFromPicker.getDate());
        LocalDateTime availableToDate = getLocalDateTimeFromDate(availableToPicker.getDate());

        if (availableFromDate == null || availableToDate == null) {
            JOptionPane.showMessageDialog(this, "Bitte gültiges Datum/Uhrzeit wählen.",
                    "Eingabefehler", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int capacity = -1;
        if (!capacityText.isEmpty()) {
            try {
                capacity = Integer.parseInt(capacityText);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Kapazität muss eine Zahl sein.",
                        "Eingabefehler", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        List<String> selectedFeatures = new ArrayList<>();
        for (JCheckBox checkBox : featureCheckBoxes) {
            if (checkBox.isSelected()) {
                selectedFeatures.add(checkBox.getText());
            }
        }

        double rating = -1.0;
        if (!ratingField.getText().isEmpty()) {
            try {
                rating = Double.parseDouble(ratingField.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Bewertung muss eine Zahl sein.",
                        "Eingabefehler", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        int floor = -1;
        if (!floorField.getText().isEmpty()) {
            try {
                floor = Integer.parseInt(floorField.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Stockwerk muss eine Zahl sein.",
                        "Eingabefehler", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        List<Room> rooms = roomDao.searchRooms(capacity, location, selectedFeatures, rating, floor,
                availableFromDate, availableToDate);
        tableModel.setRowCount(0);
        for (Room room : rooms) {
            Object[] row = {
                    room.getRoomName(),
                    room.getCapacity(),
                    String.join(", ", room.getFeatures()),
                    room.getLocation()
            };
            tableModel.addRow(row);
        }
    }

    private void bookRoom() {
        int selectedRow = resultsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Bitte wählen Sie einen Raum zum Buchen aus.",
                    "Kein Raum ausgewählt", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String roomName = (String) tableModel.getValueAt(selectedRow, 0);
        Room room = roomDao.getRoomByName(roomName);
        if (room == null) {
            JOptionPane.showMessageDialog(this, "Ausgewählter Raum nicht gefunden.",
                    "Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Prompt user for start/end times in "yyyy-MM-dd HH:mm"
        LocalDateTime startTime = promptLocalDateTime("Startzeit (yyyy-MM-dd HH:mm)");
        LocalDateTime endTime = promptLocalDateTime("Endzeit (yyyy-MM-dd HH:mm)");

        if (startTime == null || endTime == null || endTime.isBefore(startTime)) {
            JOptionPane.showMessageDialog(this, "Ungültiger Zeitraum.",
                    "Buchungsfehler", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Actually create booking in DB
        Booking booking = new Booking(0, currentUser.getUserId(), room.getRoomId(),
                startTime, endTime, "Meeting", "Bestätigt");
        bookingDao.createBooking(
                booking.getUserId(),
                booking.getRoomId(),
                booking.getStartTime(),
                booking.getEndTime(),
                booking.getPurpose(),
                booking.getStatus()
        );

        JOptionPane.showMessageDialog(this, "Raum " + room.getRoomName() + " erfolgreich gebucht!",
                "Buchungsbestätigung", JOptionPane.INFORMATION_MESSAGE);
    }

    private LocalDateTime getLocalDateTimeFromDate(Date date) {
        if (date == null) return null;
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    private LocalDateTime promptLocalDateTime(String message) {
        String input = JOptionPane.showInputDialog(this, message);
        if (input == null || input.isEmpty()) {
            return null;
        }
        try {
            return LocalDateTime.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ungültiges Datumsformat.",
                    "Eingabefehler", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
