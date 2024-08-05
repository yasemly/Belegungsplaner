package com.room.booking.presentation;

import com.room.booking.dao.RoomDao;
import com.room.booking.dao.RoomDaoImpl;
import com.room.booking.model.BaseUser;
import com.room.booking.model.Employer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GUI-Frame zum Löschen eines Raums
 */
public class DeleteRoomFrame extends JFrame {

    // Swing-Komponenten
    private JTextField roomIdField;

    /**
     * Konstruktor für das DeleteRoomFrame
     * Initialisiert die Komponenten und das Layout des Frames
     */
    public DeleteRoomFrame() {
        setTitle("Raum löschen");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(2, 2, 10, 10));

        // Raum-ID
        add(new JLabel("Raum-ID:"));
        roomIdField = new JTextField();
        add(roomIdField);

        // Löschen-Button
        JButton deleteButton = new JButton("Raum löschen");
        add(deleteButton);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteRoom();
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
     * Löscht einen Raum aus der Datenbank anhand der Raum-ID
     */
    private void deleteRoom() {
        int roomId;
        try {
            roomId = Integer.parseInt(roomIdField.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Raum-ID muss eine Zahl sein.", "Eingabefehler", JOptionPane.ERROR_MESSAGE);
            return;
        }

        RoomDao roomDao = new RoomDaoImpl();
        roomDao.deleteRoom(roomId);

        JOptionPane.showMessageDialog(this, "Raum erfolgreich gelöscht!");

        // Feld nach dem Löschen leeren
        roomIdField.setText("");
    }

    /**
     * Öffnet das EmployerFrame und schließt das aktuelle Fenster
     */
    private void openEmployerFrame() {
        // Hier sollten Sie den tatsächlichen Benutzer übergeben, nicht einen neuen erstellen
        // Verwenden Sie Employer, wenn Sie spezifische Arbeitgeberfunktionen benötigen
        EmployerFrame employerFrame = new EmployerFrame(new Employer(1, "admin", "Admin User", "admin@example.com", "password", "IT"));
        employerFrame.setVisible(true);
        dispose();
    }

    // ... (main-Methode bleibt unverändert)


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DeleteRoomFrame deleteRoomFrame = new DeleteRoomFrame();
            deleteRoomFrame.setVisible(true);
        });
    }
}
