package com.room.booking.presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

// Import the JXDatePicker class for date selection
import org.jdesktop.swingx.JXDatePicker;

// Your custom Room and RoomDao classes
import com.room.booking.model.Room;
import com.room.booking.dao.RoomDao;
import com.room.booking.dao.RoomDaoImpl;
import com.room.booking.model.User;


public class DeleteRoomFrame extends JFrame {

    private JTextField roomIdField;

    public DeleteRoomFrame() {
        setTitle("Delete Room");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(2, 2, 10, 10));

        // Room ID
        add(new JLabel("Room ID:"));
        roomIdField = new JTextField();
        add(roomIdField);

        // Delete Button
        JButton deleteButton = new JButton("Delete Room");
        add(deleteButton);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteRoom();
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

    private void deleteRoom() {
        int roomId = Integer.parseInt(roomIdField.getText().trim());

        RoomDao roomDao = new RoomDaoImpl();
        roomDao.deleteRoom(roomId);

        JOptionPane.showMessageDialog(this, "Room deleted successfully!");

        // Optionally, clear the field after deleting the room
        roomIdField.setText("");
    }

    private void openEmployerFrame() {
        EmployerFrame employerFrame = new EmployerFrame(new User(1, "admin", "Admin User", "admin@example.com", "Admin", "password")); // Replace with the actual user
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
