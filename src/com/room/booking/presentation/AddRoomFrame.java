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

/**
 * GUI-Frame zum Hinzufügen eines neuen Raums.
 */
public class AddRoomFrame extends JFrame {

    // Swing-Komponenten
    private JTextField roomNameField;
    private JTextField roomCapacityField;
    private JTextField roomLocationField;
    private JCheckBox hasProjectorField;
    private JCheckBox hasWhiteboardField;
    private JCheckBox isAvailableField; // Diese Checkbox scheint derzeit nicht verwendet zu werden

    /**
     * Konstruktor für das AddRoomFrame.
     * Initialisiert die Komponenten und das Layout des Frames.
     */
    public AddRoomFrame() {
        setTitle("Raum hinzufügen");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 2, 10, 10));

        // Raumname
        add(new JLabel("Raumname:"));
        roomNameField = new JTextField();
        add(roomNameField);

        // Raumkapazität
        add(new JLabel("Raumkapazität:"));
        roomCapacityField = new JTextField();
        add(roomCapacityField);

        // Raumstandort
        add(new JLabel("Raumstandort:"));
        roomLocationField = new JTextField();
        add(roomLocationField);

        // Beamer vorhanden
        add(new JLabel("Beamer vorhanden:"));
        hasProjectorField = new JCheckBox();
        add(hasProjectorField);

        // Whiteboard vorhanden
        add(new JLabel("Whiteboard vorhanden:"));
        hasWhiteboardField = new JCheckBox();
        add(hasWhiteboardField);

        // Verfügbar (derzeit nicht verwendet)
        add(new JLabel("Verfügbar:"));
        isAvailableField = new JCheckBox();
        add(isAvailableField);

        // Hinzufügen-Button
        JButton addButton = new JButton("Raum hinzufügen");
        add(addButton);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addRoom();
            }
        });

        // Zurück-Button
        JButton backButton = new JButton("Zurück zum Arbeitgeber-Fenster");
        add(backButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openEmployerFrame();
            }
        });
    }

    /**
     * Fügt einen neuen Raum zur Datenbank hinzu.
     * Liest die Daten aus den Eingabefeldern, erstellt ein Room-Objekt und speichert es über das RoomDao.
     */
    private void addRoom() {
        String roomName = roomNameField.getText().trim();
        int roomCapacity;
        try {
            roomCapacity = Integer.parseInt(roomCapacityField.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Raumkapazität muss eine Zahl sein.", "Eingabefehler", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String roomLocation = roomLocationField.getText().trim();
        boolean hasProjector = hasProjectorField.isSelected();
        boolean hasWhiteboard = hasWhiteboardField.isSelected();

        // Liste der Ausstattungsmerkmale basierend auf den Checkboxes erstellen
        List<String> features = new ArrayList<>();
        if (hasProjector) {
            features.add("Beamer");
        }
        if (hasWhiteboard) {
            features.add("Whiteboard");
        }

        RoomDao roomDao = new RoomDaoImpl();
        Room room = new Room(0, roomName, roomCapacity, features, roomLocation, 0.0, 0); // Standardwerte für Bewertung und Stockwerk

        roomDao.addRoom(room);
        JOptionPane.showMessageDialog(this, "Raum erfolgreich hinzugefügt!");

        // Felder nach dem Hinzufügen leeren
        clearFields();
    }

    /**
     * Öffnet das EmployerFrame und schließt das aktuelle Fenster
     */
    private void openEmployerFrame() {
        // Hier sollten Sie den tatsächlichen Benutzer übergeben, nicht einen neuen erstellen
        EmployerFrame employerFrame = new EmployerFrame(new User(1, "admin", "Admin User", "admin@example.com", "password", "Admin")); // Add "role" parameter
        employerFrame.setVisible(true);
        dispose();
    }

    /**
     * Leert alle Eingabefelder und setzt die Checkboxes zurück
     */
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
