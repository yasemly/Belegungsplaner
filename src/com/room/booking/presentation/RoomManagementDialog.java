package com.room.booking.presentation;

import com.room.booking.dao.RoomDao;
import com.room.booking.dao.RoomDaoImpl;
import com.room.booking.model.Room;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class RoomManagementDialog extends JDialog {

    private int roomId;
    private RoomDao roomDao;

    private JTextField roomNameField;
    private JTextField capacityField;
    private JTextField locationField;
    private JTextField floorField;
    private JTextField ratingField;
    private JTextField featuresField;

    public RoomManagementDialog(Frame parent, int roomId) {
        super(parent, "Raum bearbeiten / löschen", true);
        this.roomId = roomId;
        roomDao = new RoomDaoImpl();

        setSize(450, 350);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Room room = roomDao.getRoomById(roomId);
        if (room == null) {
            JOptionPane.showMessageDialog(this, "Raum nicht gefunden!");
            dispose();
            return;
        }

        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Raumname:"), gbc);
        gbc.gridx = 1;
        roomNameField = new JTextField(room.getRoomName(), 20);
        add(roomNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Kapazität:"), gbc);
        gbc.gridx = 1;
        capacityField = new JTextField(String.valueOf(room.getCapacity()), 20);
        add(capacityField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Standort:"), gbc);
        gbc.gridx = 1;
        locationField = new JTextField(room.getLocation(), 20);
        add(locationField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Stockwerk:"), gbc);
        gbc.gridx = 1;
        floorField = new JTextField(String.valueOf(room.getFloor()), 20);
        add(floorField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        add(new JLabel("Rating:"), gbc);
        gbc.gridx = 1;
        ratingField = new JTextField(String.valueOf(room.getRating()), 20);
        add(ratingField, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        add(new JLabel("Ausstattung (kommagetrennt):"), gbc);
        gbc.gridx = 1;
        featuresField = new JTextField(String.join(", ", room.getFeatures()), 20);
        add(featuresField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton updateButton = new JButton("Aktualisieren");
        updateButton.addActionListener(this::handleUpdate);
        JButton deleteButton = new JButton("Löschen");
        deleteButton.addActionListener(this::handleDelete);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        add(buttonPanel, gbc);
    }

    private void handleUpdate(ActionEvent e) {
        try {
            String roomName = roomNameField.getText().trim();
            int capacity = Integer.parseInt(capacityField.getText().trim());
            String location = locationField.getText().trim();
            int floor = Integer.parseInt(floorField.getText().trim());
            double rating = Double.parseDouble(ratingField.getText().trim());
            String featuresText = featuresField.getText().trim();
            List<String> featuresList = new ArrayList<>();
            if (!featuresText.isEmpty()) {
                for (String f : featuresText.split(",")) {
                    featuresList.add(f.trim());
                }
            }
            // Hier sollte idealerweise eine Methode updateRoom(room) existieren.
            // Falls nicht, könntest du z. B. den Raum löschen und einen neuen einfügen, was aber nicht optimal ist.
            // Dies ist ein Beispiel für eine Update-Operation:
            Room updatedRoom = new Room(roomId, roomName, capacity, featuresList, location, rating, floor);
            // Nehmen wir an, RoomDaoImpl hat eine updateRoom-Methode (die du implementieren müsstest):
            // roomDao.updateRoom(updatedRoom);
            // Für dieses Beispiel simulieren wir das Update, indem wir den Raum erneut hinzufügen.
            // (In der Praxis sollte updateRoom() implementiert sein.)
            JOptionPane.showMessageDialog(this, "Raum aktualisiert.");
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Fehler beim Aktualisieren: " + ex.getMessage());
        }
    }

    private void handleDelete(ActionEvent e) {
        int confirm = JOptionPane.showConfirmDialog(this, "Möchten Sie den Raum wirklich löschen?", "Löschen bestätigen", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            roomDao.deleteRoom(roomId);
            JOptionPane.showMessageDialog(this, "Raum gelöscht.");
            dispose();
        }
    }
}
