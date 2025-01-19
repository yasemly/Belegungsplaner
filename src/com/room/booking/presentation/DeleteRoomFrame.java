package com.room.booking.presentation;

import com.room.booking.dao.RoomDao;
import com.room.booking.dao.RoomDaoImpl;
import com.room.booking.model.Employer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GUI-Frame zum Löschen eines Raums
 */
public class DeleteRoomFrame extends JFrame {

    private JTextField roomIdField;

    public DeleteRoomFrame() {
        setTitle("Raum löschen");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(2, 2, 10, 10));

        add(new JLabel("Raum-ID:"));
        roomIdField = new JTextField();
        add(roomIdField);

        JButton deleteButton = new JButton("Raum löschen");
        add(deleteButton);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteRoom();
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

    private void deleteRoom() {
        try {
            int roomId = Integer.parseInt(roomIdField.getText().trim());
            RoomDao roomDao = new RoomDaoImpl();
            roomDao.deleteRoom(roomId);

            JOptionPane.showMessageDialog(this, "Raum erfolgreich gelöscht!");
            roomIdField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Raum-ID muss eine Zahl sein.", "Eingabefehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openEmployerFrame() {
        EmployerFrame employerFrame = new EmployerFrame(new Employer(
                1, "admin", "Admin User", "admin@example.com", "password", "IT"
        ));
        employerFrame.setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DeleteRoomFrame deleteRoomFrame = new DeleteRoomFrame();
            deleteRoomFrame.setVisible(true);
        });
    }
}
