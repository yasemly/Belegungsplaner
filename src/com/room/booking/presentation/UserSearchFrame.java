package com.room.booking.presentation;

import com.room.booking.dao.BookingDao;
import com.room.booking.dao.BookingDaoImpl;
import com.room.booking.dao.RoomDao;
import com.room.booking.dao.RoomDaoImpl;
import com.room.booking.model.Room;
import com.room.booking.model.User;
import com.room.booking.model.Booking;
import org.jdatepicker.JDatePicker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Calendar;
import java.util.TimeZone;

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
    private User currentUser;
    private List<JCheckBox> featureCheckBoxes;
    private JTextField ratingField;
    private JTextField floorField;

    public UserSearchFrame(User user) {
        this.currentUser = user;
        this.roomDao = new RoomDaoImpl();

        setTitle("Search Rooms");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel(new GridBagLayout());
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        searchPanel.add(new JLabel("Capacity:"), gbc);
        gbc.gridx = 1;
        capacityField = new JTextField(10);
        searchPanel.add(capacityField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        searchPanel.add(new JLabel("Location:"), gbc);
        gbc.gridx = 1;
        locationField = new JTextField(20);
        searchPanel.add(locationField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        searchPanel.add(new JLabel("Available From:"), gbc);
        gbc.gridx = 1;
        availableFromPicker = new DateTimePicker();
        searchPanel.add(availableFromPicker, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        searchPanel.add(new JLabel("Available To:"), gbc);
        gbc.gridx = 1;
        availableToPicker = new DateTimePicker();
        searchPanel.add(availableToPicker, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        searchPanel.add(new JLabel("Features:"), gbc);
        gbc.gridx = 1;
        JPanel featuresPanel = new JPanel(new GridLayout(0, 1));
        featureCheckBoxes = new ArrayList<>();
        String[] features = {"Beamer", "Blackboard", "Whiteboard", "Wi-Fi", "Air Conditioning"};
        for (String feature : features) {
            JCheckBox checkBox = new JCheckBox(feature);
            featureCheckBoxes.add(checkBox);
            featuresPanel.add(checkBox);
        }
        searchPanel.add(featuresPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        searchPanel.add(new JLabel("Rating:"), gbc);
        gbc.gridx = 1;
        ratingField = new JTextField(10);
        searchPanel.add(ratingField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        searchPanel.add(new JLabel("Floor:"), gbc);
        gbc.gridx = 1;
        floorField = new JTextField(10);
        searchPanel.add(floorField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        searchButton = new JButton("Search");
        searchPanel.add(searchButton, gbc);

        add(searchPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Room Name", "Capacity", "Features", "Location"}
        );
        resultsTable = new JTable(tableModel);
        add(new JScrollPane(resultsTable), BorderLayout.CENTER);

        JPanel bookPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bookButton = new JButton("Book This Room");
        bookPanel.add(bookButton);
        add(bookPanel, BorderLayout.SOUTH);

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

    private void searchRooms() {
        String capacityText = capacityField.getText();
        String location = locationField.getText();

        LocalDateTime availableFromDate = availableFromPicker.getDate();
        Date availableToDate = availableToPicker.getDate();

        if (availableFromDate == null || availableToDate == null) {
            JOptionPane.showMessageDialog(this, "Please select valid date and time.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int capacity = -1;
        if (!capacityText.isEmpty()) {
            try {
                capacity = Integer.parseInt(capacityText);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Capacity must be a number.", "Input Error", JOptionPane.ERROR_MESSAGE);
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
                JOptionPane.showMessageDialog(this, "Rating must be a number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
                return;
            }
        }

        int floor = -1;
        if (!floorField.getText().isEmpty()) {
            try {
                floor = Integer.parseInt(floorField.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Floor must be a number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
                return;
            }
        }

        List<Room> rooms = roomDao.searchRooms(capacity, location, selectedFeatures, rating, floor, availableFromDate, availableToDate);

        tableModel.setRowCount(0);

        for (Room room : rooms) {
            Object[] row = {room.getRoomName(), room.getCapacity(), room.getFeatures(), room.getLocation()};
            tableModel.addRow(row);
        }
    }

    private void bookRoom() {
        int selectedRow = resultsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a room to book.", "No Room Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String roomName = (String) tableModel.getValueAt(selectedRow, 0);
        Room room = roomDao.getRoomByName(roomName);

        if (room == null) {
            JOptionPane.showMessageDialog(this, "Selected room not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        LocalDateTime startTime = getDateTimeFromUser("Start Time");
        LocalDateTime endTime = getDateTimeFromUser("End Time");

        if (startTime == null || endTime == null || endTime.isBefore(startTime)) {
            JOptionPane.showMessageDialog(this, "Invalid time range. Please select valid start and end times.", "Booking Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Booking booking = new Booking(0, currentUser.getUserId(), room.getRoomId(), startTime, endTime, "Meeting", "Confirmed");

        // Assuming you have a BookingDao for handling booking data
        // Replace this with your actual booking method
        BookingDao bookingDao = new BookingDaoImpl();
        bookingDao.bookRoom(booking);

        JOptionPane.showMessageDialog(this, "Room " + room.getRoomName() + " booked successfully!", "Booking Confirmation", JOptionPane.INFORMATION_MESSAGE);
    }

    private LocalDateTime getDateTimeFromUser(String title) {
        String input = JOptionPane.showInputDialog(this, title + " (yyyy-MM-dd HH:mm):");
        if (input != null && !input.isEmpty()) {
            try {
                return LocalDateTime.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid date/time format. Please use yyyy-MM-dd HH:mm.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return null;
    }
}

// Custom DateTimePicker Class
class DateTimePicker extends JPanel {
    private JDatePicker datePicker;
    private JSpinner timeSpinner;

    public DateTimePicker() {
        setLayout(new BorderLayout());

        // Date Picker
        datePicker = new JDatePicker();
        add(datePicker, BorderLayout.NORTH);

        // Time Spinner
        SpinnerModel timeModel = new SpinnerDateModel();
        timeSpinner = new JSpinner(timeModel);
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
        timeSpinner.setEditor(timeEditor);
        add(timeSpinner, BorderLayout.SOUTH);
    }

    public Date getDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime((Date) datePicker.getModel().getValue());
        cal.set(Calendar.HOUR_OF_DAY, ((Date) timeSpinner.getValue()).getHours());
        cal.set(Calendar.MINUTE, ((Date) timeSpinner.getValue()).getMinutes());
        return cal.getTime();
    }
}
