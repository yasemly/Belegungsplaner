package com.room.booking.presentation;

import com.room.booking.dao.BookingDao;
import com.room.booking.dao.BookingDaoImpl;
import com.room.booking.dao.RoomDao;
import com.room.booking.dao.RoomDaoImpl;
import com.room.booking.model.Room;
import com.room.booking.model.User;
import com.room.booking.model.Booking;
import com.room.booking.util.DateTimePicker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * GUI-Frame für die Benutzersuche nach verfügbaren Räumen und die Buchung eines Raums.
 */
public class UserSearchFrame extends JFrame {

    // Swing-Komponenten
    private JTextField capacityField;
    private JTextField locationField;
    private DateTimePicker availableFromPicker;
    private DateTimePicker availableToPicker;
    private JButton searchButton;
    private JButton bookButton;
    private JTable resultsTable;
    private DefaultTableModel tableModel;

    // Data Access Objects
    private RoomDao roomDao;
    private BookingDao bookingDao;

    // Der aktuell angemeldete Benutzer
    private User currentUser;

    // Liste der Ausstattungsmerkmale-Checkboxes
    private List<JCheckBox> featureCheckBoxes;

    // Zusätzliche Suchfelder
    private JTextField ratingField;
    private JTextField floorField;

    /**
     * Konstruktor für das UserSearchFrame
     *
     * @param user Der aktuell angemeldete Benutzer
     */
    public UserSearchFrame(User user) {
        this.currentUser = user;
        this.roomDao = new RoomDaoImpl();
        this.bookingDao = new BookingDaoImpl();

        setTitle("Räume suchen");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Suchbereich erstellen
        JPanel searchPanel = createSearchPanel();
        add(searchPanel, BorderLayout.NORTH);

        // Ergebnistabelle erstellen
        tableModel = new DefaultTableModel(new Object[][]{}, new String[]{"Raumname", "Kapazität", "Ausstattung", "Standort"});
        resultsTable = new JTable(tableModel);
        add(new JScrollPane(resultsTable), BorderLayout.CENTER);

        // Buchungsbereich erstellen
        JPanel bookPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bookButton = new JButton("Diesen Raum buchen");
        bookPanel.add(bookButton);
        add(bookPanel, BorderLayout.SOUTH);

        // ActionListener für Such- und Buchungsbuttons hinzufügen
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchRooms();
            }
        });

        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookRoom();
            }
        });
    }

    /**
     * Erstellt das JPanel für den Suchbereich mit allen Eingabefeldern und dem Suchbutton.
     *
     * @return Das erstellte Such-Panel
     */
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
        availableFromPicker = new DateTimePicker(); // Ensure this has the correct method for date-time
        searchPanel.add(availableFromPicker, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        searchPanel.add(new JLabel("Verfügbar bis:"), gbc);
        gbc.gridx = 1;
        availableToPicker = new DateTimePicker(); // Ensure this has the correct method for date-time
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

    /**
     * Sucht nach verfügbaren Räumen basierend auf den eingegebenen Kriterien und zeigt die Ergebnisse in der Tabelle an
     */
    private void searchRooms() {
        String capacityText = capacityField.getText();
        String location = locationField.getText();

        LocalDateTime availableFromDate = getDateTimeFromPicker(availableFromPicker);
        LocalDateTime availableToDate = getDateTimeFromPicker(availableToPicker);

        if (availableFromDate == null || availableToDate == null) {
            JOptionPane.showMessageDialog(this, "Bitte wählen Sie ein gültiges Datum und eine gültige Uhrzeit aus.", "Eingabefehler", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int capacity = -1;
        if (!capacityText.isEmpty()) {
            try {
                capacity = Integer.parseInt(capacityText);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Kapazität muss eine Zahl sein.", "Eingabefehler", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
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
                JOptionPane.showMessageDialog(this, "Bewertung muss eine Zahl sein.", "Eingabefehler", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
                return;
            }
        }

        int floor = -1;
        if (!floorField.getText().isEmpty()) {
            try {
                floor = Integer.parseInt(floorField.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Stockwerk muss eine Zahl sein.", "Eingabefehler", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
                return;
            }
        }

        List<Room> rooms = roomDao.searchRooms(capacity, location, selectedFeatures, rating, floor, availableFromDate, availableToDate);
        tableModel.setRowCount(0);
        for (Room room : rooms) {
            Object[] row = {
                    room.getRoomName(),  // Ensure this method exists or adjust accordingly
                    room.getCapacity(),
                    String.join(", ", room.getFeatures()),
                    room.getLocation()
            };
            tableModel.addRow(row);
        }
    }

    /**
     * Bucht den ausgewählten Raum für den aktuellen Benutzer.
     */
    private void bookRoom() {
        int selectedRow = resultsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Bitte wählen Sie einen Raum zum Buchen aus.", "Kein Raum ausgewählt", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String roomName = (String) tableModel.getValueAt(selectedRow, 0);
        Room room = roomDao.getRoomByName(roomName);
        if (room == null) {
            JOptionPane.showMessageDialog(this, "Ausgewählter Raum nicht gefunden.", "Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }

        LocalDateTime startTime = getDateTimeFromUser("Startzeit");
        LocalDateTime endTime = getDateTimeFromUser("Endzeit");

        if (startTime == null || endTime == null || endTime.isBefore(startTime)) {
            JOptionPane.showMessageDialog(this, "Ungültiger Zeitraum. Bitte wählen Sie gültige Start- und Endzeiten aus.", "Buchungsfehler", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Booking booking = new Booking(0, currentUser.getUserId(), room.getRoomId(), startTime, endTime, "Meeting", "Bestätigt");
        bookingDao.createBooking(booking.getUserId(), booking.getRoomId(), booking.getStartTime(), booking.getEndTime(), booking.getPurpose(), booking.getStatus());

        JOptionPane.showMessageDialog(this, "Raum " + room.getRoomName() + " erfolgreich gebucht!", "Buchungsbestätigung", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Fordert den Benutzer auf, ein Datum und eine Uhrzeit im Format "yyyy-MM-dd HH:mm" einzugeben und gibt das Ergebnis als LocalDateTime zurück.
     *
     * @param title Der Titel des Eingabedialogs
     * @return Das eingegebene Datum und die Uhrzeit als LocalDateTime, oder null, wenn die Eingabe ungültig oder leer ist
     */
    private LocalDateTime getDateTimeFromUser(String title) {
        String input = JOptionPane.showInputDialog(this, title + " (yyyy-MM-dd HH:mm):");
        if (input != null && !input.isEmpty()) {
            try {
                return LocalDateTime.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Ungültiges Datums-/Zeitformat. Bitte verwenden Sie yyyy-MM-dd HH:mm.", "Eingabefehler", JOptionPane.ERROR_MESSAGE);
            }
        }
        return null;
    }

    /**
     * Konvertiert den Wert aus dem DateTimePicker in LocalDateTime.
     *
     * @param dateTimePicker Der DateTimePicker
     * @return Das Datum und die Uhrzeit als LocalDateTime oder null, wenn das Datum leer ist
     */
    private LocalDateTime getDateTimeFromPicker(DateTimePicker dateTimePicker) {
        // Replace with the correct method to get date and time from the DateTimePicker
        java.util.Date date = dateTimePicker.getDate();  // Ensure getDate() is the correct method
        if (date != null) {
            return LocalDateTime.ofInstant(date.toInstant(), java.time.ZoneId.systemDefault());
        }
        return null;
    }
}
