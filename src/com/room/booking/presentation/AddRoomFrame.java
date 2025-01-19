package com.room.booking.presentation;

import com.room.booking.dao.RoomDao;
import com.room.booking.dao.RoomDaoImpl;
import com.room.booking.model.Employer;
import com.room.booking.model.Room;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * GUI-Frame zum Hinzufügen eines neuen Raums.
 */
public class AddRoomFrame extends JFrame {

    private JTextField roomNameField;
    private JTextField roomCapacityField;
    private JTextField roomLocationField;
    private JCheckBox hasProjectorField;
    private JCheckBox hasWhiteboardField;
    private JCheckBox isAvailableField; // example only, not used in DB logic

    public AddRoomFrame() {
        setTitle("Raum hinzufügen");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 2, 10, 10));

        add(new JLabel("Raumname:"));
        roomNameField = new JTextField();
        add(roomNameField);

        add(new JLabel("Raumkapazität:"));
        roomCapacityField = new JTextField();
        add(roomCapacityField);

        add(new JLabel("Raumstandort:"));
        roomLocationField = new JTextField();
        add(roomLocationField);

        add(new JLabel("Beamer vorhanden:"));
        hasProjectorField = new JCheckBox();
        add(hasProjectorField);

        add(new JLabel("Whiteboard vorhanden:"));
        hasWhiteboardField = new JCheckBox();
        add(hasWhiteboardField);

        add(new JLabel("Verfügbar:"));
        isAvailableField = new JCheckBox();
        add(isAvailableField);

        JButton addButton = new JButton("Raum hinzufügen");
        add(addButton);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addRoom();
            }
        });

        JButton backButton = new JButton("Zurück zum Arbeitgeber-Fenster");
        add(backButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openEmployerFrame();
            }
        });
    }

    private void addRoom() {
        try {
            String roomName = roomNameField.getText().trim();
            int roomCapacity = Integer.parseInt(roomCapacityField.getText().trim());
            String roomLocation = roomLocationField.getText().trim();
            boolean hasProjector = hasProjectorField.isSelected();
            boolean hasWhiteboard = hasWhiteboardField.isSelected();

            List<String> features = new ArrayList<>();
            if (hasProjector) features.add("Beamer");
            if (hasWhiteboard) features.add("Whiteboard");

            Room room = new Room(0, roomName, roomCapacity, features, roomLocation, 0.0, 0);
            RoomDao roomDao = new RoomDaoImpl();
            roomDao.addRoom(room);

            JOptionPane.showMessageDialog(this, "Raum erfolgreich hinzugefügt!");
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Fehler beim Hinzufügen des Raums: " + ex.getMessage());
        }
    }

    private void openEmployerFrame() {
        Employer sampleEmployer = new Employer(1, "admin", "Admin User",
                "admin@example.com", "password", "Admin");
        EmployerFrame employerFrame = new EmployerFrame(sampleEmployer);
        employerFrame.setVisible(true);
        dispose();
    }

    private void clearFields() {
        roomNameField.setText("");
        roomCapacityField.setText("");
        roomLocationField.setText("");
        hasProjectorField.setSelected(false);
        hasWhiteboardField.setSelected(false);
        isAvailableField.setSelected(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AddRoomFrame addRoomFrame = new AddRoomFrame();
            addRoomFrame.setVisible(true);
        });
    }
}
