package com.room.booking.presentation;

import com.room.booking.dao.RoomDao;
import com.room.booking.dao.RoomDaoImpl;
import com.room.booking.model.Room;
import com.room.booking.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AddRoomFrame extends JFrame {

    private JTextField roomNameField;
    private JTextField roomCapacityField;
    private JTextField roomLocationField;
    private JCheckBox hasProjectorField;
    private JCheckBox hasWhiteboardField;
    private JCheckBox isAvailableField;

    public AddRoomFrame() {
        setTitle("Add Room");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 2, 10, 10));

        // Room Name
        add(new JLabel("Room Name:"));
        roomNameField = new JTextField();
        add(roomNameField);

        // Room Capacity
        add(new JLabel("Room Capacity:"));
        roomCapacityField = new JTextField();
        add(roomCapacityField);

        // Room Location
        add(new JLabel("Room Location:"));
        roomLocationField = new JTextField();
        add(roomLocationField);

        // Has Projector
        add(new JLabel("Has Projector:"));
        hasProjectorField = new JCheckBox();
        add(hasProjectorField);

        // Has Whiteboard
        add(new JLabel("Has Whiteboard:"));
        hasWhiteboardField = new JCheckBox();
        add(hasWhiteboardField);

        // Is Available
        add(new JLabel("Is Available:"));
        isAvailableField = new JCheckBox();
        add(isAvailableField);

        // Add Button
        JButton addButton = new JButton("Add Room");
        add(addButton);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addRoom();
            }
        });

        // Back Button
        JButton backButton = new JButton("Back to Employer Frame");
        add(backButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openEmployerFrame();
            }
        });
    }

    private void addRoom() {
        String roomName = roomNameField.getText().trim();
        int roomCapacity = Integer.parseInt(roomCapacityField.getText().trim());
        String roomLocation = roomLocationField.getText().trim();
        boolean hasProjector = hasProjectorField.isSelected();
        boolean hasWhiteboard = hasWhiteboardField.isSelected();
        boolean isAvailable = isAvailableField.isSelected();

        // Create a list of features based on the checkboxes
        List<String> features = new ArrayList<>();
        if (hasProjector) {
            features.add("Projector");
        }
        if (hasWhiteboard) {
            features.add("Whiteboard");
        }

        RoomDao roomDao = new RoomDaoImpl();
        Room room = new Room(0, roomName, roomCapacity, features, roomLocation, 0.0, 0);


        roomDao.addRoom(room);

        JOptionPane.showMessageDialog(this, "Room added successfully!");

        // Optionally, clear the fields after adding the room
        roomNameField.setText("");
        roomCapacityField.setText("");
        roomLocationField.setText("");
        hasProjectorField.setSelected(false);
        hasWhiteboardField.setSelected(false);
        isAvailableField.setSelected(false);
    }

    private void openEmployerFrame() {
        EmployerFrame employerFrame = new EmployerFrame(new User(1, "admin", "Admin User", "admin@example.com", "Admin", "password")); // Replace with the actual user
        employerFrame.setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AddRoomFrame addRoomFrame = new AddRoomFrame();
            addRoomFrame.setVisible(true);
        });
    }
}
