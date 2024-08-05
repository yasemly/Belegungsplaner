package com.room.booking.presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

/**
 * Frame zum Hinzufügen einer neuen Buchung.
 */
public class AddBookingFrame extends JFrame {

    private JTextField roomIdField;
    private JTextField userIdField;
    private JTextField startTimeField;
    private JTextField endTimeField;
    private JTextField purposeField;
    private JComboBox<String> statusComboBox;

    /**
     * Konstruktor für AddBookingFrame.
     * Initialisiert das Fenster und seine Komponenten.
     */
    public AddBookingFrame() {
        setTitle("Buchung hinzufügen");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2, 10, 10));

        panel.add(new JLabel("Raum-ID:"));
        roomIdField = new JTextField();
        panel.add(roomIdField);

        panel.add(new JLabel("Benutzer-ID:"));
        userIdField = new JTextField();
        panel.add(userIdField);

        panel.add(new JLabel("Startzeit (yyyy-MM-ddTHH:mm):"));
        startTimeField = new JTextField();
        panel.add(startTimeField);

        panel.add(new JLabel("Endzeit (yyyy-MM-ddTHH:mm):"));
        endTimeField = new JTextField();
        panel.add(endTimeField);

        panel.add(new JLabel("Zweck:"));
        purposeField = new JTextField();
        panel.add(purposeField);

        panel.add(new JLabel("Status:"));
        statusComboBox = new JComboBox<>(new String[]{"Pending", "Confirmed", "Cancelled"});
        panel.add(statusComboBox);

        JButton addButton = new JButton("Hinzufügen");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBooking();
            }
        });
        panel.add(addButton);

        add(panel);
    }

    /**
     * Methode zum Hinzufügen einer neuen Buchung.
     * Erfasst die Eingaben des Benutzers und führt die Buchung durch.
     */
    private void addBooking() {
        int roomId = Integer.parseInt(roomIdField.getText());
        int userId = Integer.parseInt(userIdField.getText());
        LocalDateTime startTime = LocalDateTime.parse(startTimeField.getText());
        LocalDateTime endTime = LocalDateTime.parse(endTimeField.getText());
        String purpose = purposeField.getText();
        String status = (String) statusComboBox.getSelectedItem();

        // Logik zum Hinzufügen der Buchung in die Datenbank oder andere Aktionen.
        // Hier könnten Sie einen Service-Aufruf hinzufügen, um die Buchung zu registrieren.

        JOptionPane.showMessageDialog(this, "Buchung hinzugefügt für Raum: " + roomId);
        clearFields();
    }

    /**
     * Methode zum Leeren der Eingabefelder nach erfolgreicher Buchung.
     */
    private void clearFields() {
        roomIdField.setText("");
        userIdField.setText("");
        startTimeField.setText("");
        endTimeField.setText("");
        purposeField.setText("");
        userIdField.setText("");
        statusComboBox.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AddBookingFrame frame = new AddBookingFrame();
            frame.setVisible(true);
        });
    }
}
