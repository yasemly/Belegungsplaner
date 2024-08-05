package com.room.booking.presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Frame zum Aktualisieren der Rauminformationen.
 */
public class UpdateRoomFrame extends JFrame {

    private JTextField roomIdField;
    private JTextField roomNameField;
    private JTextField capacityField;
    private JTextField featuresField;
    private JTextField locationField;
    private JTextField floorField;

    /**
     * Konstruktor für UpdateRoomFrame.
     * Initialisiert das Fenster und seine Komponenten.
     */
    public UpdateRoomFrame() {
        setTitle("Raum aktualisieren");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2, 10, 10));

        panel.add(new JLabel("Raum-ID:"));
        roomIdField = new JTextField();
        panel.add(roomIdField);

        panel.add(new JLabel("Raumname:"));
        roomNameField = new JTextField();
        panel.add(roomNameField);

        panel.add(new JLabel("Kapazität:"));
        capacityField = new JTextField();
        panel.add(capacityField);

        panel.add(new JLabel("Ausstattung:"));
        featuresField = new JTextField();
        panel.add(featuresField);

        panel.add(new JLabel("Standort:"));
        locationField = new JTextField();
        panel.add(locationField);

        panel.add(new JLabel("Stockwerk:"));
        floorField = new JTextField();
        panel.add(floorField);

        JButton updateButton = new JButton("Aktualisieren");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateRoom();
            }
        });
        panel.add(updateButton);

        add(panel);
    }

    /**
     * Methode zum Aktualisieren der Rauminformationen.
     */
    private void updateRoom() {
        try {
            int roomId = Integer.parseInt(roomIdField.getText());
            String roomName = roomNameField.getText().trim();
            int capacity = Integer.parseInt(capacityField.getText().trim());
            String features = featuresField.getText().trim();
            String location = locationField.getText().trim();
            String floor = floorField.getText().trim();

            // Hier die Logik zum Aktualisieren des Raums in der Datenbank implementieren
            // Beispielsweise einen Service-Aufruf zur Aktualisierung des Raums

            JOptionPane.showMessageDialog(this, "Raum mit ID " + roomId + " aktualisiert.");
            clearFields();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Bitte geben Sie gültige Werte ein.");
        }
    }

    /**
     * Methode zum Leeren der Eingabefelder nach erfolgreicher Aktualisierung.
     */
    private void clearFields() {
        roomIdField.setText("");
        roomNameField.setText("");
        capacityField.setText("");
        featuresField.setText("");
        locationField.setText("");
        floorField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UpdateRoomFrame frame = new UpdateRoomFrame();
            frame.setVisible(true);
        });
    }
}
