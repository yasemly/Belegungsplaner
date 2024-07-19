import com.room.booking.dao.RoomDao;
import com.room.booking.dao.RoomDaoImpl;
import com.room.booking.model.Room;
import com.room.booking.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;

public class UserSearchFrame extends JFrame {
    private JTextField capacityField;
    private JTextField locationField;
    private JDatePickerImpl availableFromPicker;
    private JDatePickerImpl availableToPicker;
    private JComboBox<String> availableFromTimeComboBox;
    private JComboBox<String> availableToTimeComboBox;
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
        searchPanel.add(new JLabel("Available From (Date):"), gbc);
        gbc.gridx = 1;
        availableFromPicker = createDatePicker();
        searchPanel.add(availableFromPicker, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        searchPanel.add(new JLabel("Available From (Time):"), gbc);
        gbc.gridx = 1;
        availableFromTimeComboBox = createTimeComboBox();
        searchPanel.add(availableFromTimeComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        searchPanel.add(new JLabel("Available To (Date):"), gbc);
        gbc.gridx = 1;
        availableToPicker = createDatePicker();
        searchPanel.add(availableToPicker, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        searchPanel.add(new JLabel("Available To (Time):"), gbc);
        gbc.gridx = 1;
        availableToTimeComboBox = createTimeComboBox();
        searchPanel.add(availableToTimeComboBox, gbc);

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

        // Add rating field
        gbc.gridx = 0;
        gbc.gridy++;
        searchPanel.add(new JLabel("Rating:"), gbc);
        gbc.gridx = 1;
        ratingField = new JTextField(10);
        searchPanel.add(ratingField, gbc);

        // Add floor field
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

        // Table setup
        tableModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Room Name", "Capacity", "Features", "Location"}
        );
        resultsTable = new JTable(tableModel);
        add(new JScrollPane(resultsTable), BorderLayout.CENTER);

        // Book Button
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

    private JComboBox<String> createTimeComboBox() {
        String[] times = {"09:00 AM", "10:00 AM", "11:00 AM", "12:00 PM", "01:00 PM", "02:00 PM", "03:00 PM",
                "04:00 PM", "05:00 PM", "06:00 PM", "07:00 PM", "08:00 PM", "09:00 PM", "10:00 PM", "11:00 PM"};
        return new JComboBox<>(times);
    }

    private JDatePickerImpl createDatePicker() {
        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model);
        return new JDatePickerImpl(datePanel);
    }

    private void searchRooms() {
        String capacityText = capacityField.getText();
        String location = locationField.getText();

        Date availableFromDate = (Date) availableFromPicker.getModel().getValue();
        Date availableToDate = (Date) availableToPicker.getModel().getValue();

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");

        String availableFromTime = (String) availableFromTimeComboBox.getSelectedItem();
        String availableToTime = (String) availableToTimeComboBox.getSelectedItem();

        LocalDateTime availableFrom = null;
        LocalDateTime availableTo = null;

        try {
            if (availableFromDate != null && availableFromTime != null) {
                LocalDateTime date = availableFromDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                LocalTime time = LocalTime.parse(availableFromTime, timeFormatter);
                availableFrom = date.with(time);
            }

            if (availableToDate != null && availableToTime != null) {
                LocalDateTime date = availableToDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                LocalTime time = LocalTime.parse(availableToTime, timeFormatter);
                availableTo = date.with(time);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error parsing date/time.", "Date Parsing Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
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

        // Update the searchRooms call to include availableFrom and availableTo
        List<Room> rooms = roomDao.searchRooms(capacity, location, selectedFeatures, rating, floor, availableFrom, availableTo);

        // Clear previous table data
        tableModel.setRowCount(0);

        // Populate table with new data
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

        // Get booking times from user input
        LocalDateTime startTime = getDateTimeFromUser("Start Time");
        LocalDateTime endTime = getDateTimeFromUser("End Time");

        if (startTime == null || endTime == null || endTime.isBefore(startTime)) {
            JOptionPane.showMessageDialog(this, "Invalid time range. Please select valid start and end times.", "Booking Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Proceed with booking the room
        // Replace this with your actual booking method
        // bookingDao.bookRoom(currentUser.getUserId(), room.getRoomId(), startTime, endTime);
        JOptionPane.showMessageDialog(this, "Room " + room.getRoomName() + " booked successfully!", "Booking Confirmation", JOptionPane.INFORMATION_MESSAGE);
    }

    private LocalDateTime getDateTimeFromUser(String title) {
        // For simplicity, using JOptionPane for date/time input
        // Replace with your own implementation for better user experience
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
