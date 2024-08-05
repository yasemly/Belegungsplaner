package com.room.booking.presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Frame zum Löschen eines bestehenden Benutzers.
 */
public class DeleteUserFrame extends JFrame {

    private JTextField usernameField;

    /**
     * Konstruktor für DeleteUserFrame.
     * Initialisiert das Fenster und seine Komponenten.
     */
    public DeleteUserFrame() {
        setTitle("Benutzer löschen");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2, 10, 10));

        panel.add(new JLabel("Benutzername:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        JButton deleteButton = new JButton("Löschen");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteUser();
            }
        });
        panel.add(deleteButton);

        add(panel);
    }

    /**
     * Methode zum Löschen eines Benutzers anhand des eingegebenen Benutzernamens.
     */
    private void deleteUser() {
        String username = usernameField.getText().trim();

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bitte geben Sie einen gültigen Benutzernamen ein.");
            return;
        }

        // Hier die Logik zum Löschen des Benutzers in der Datenbank implementieren
        // Beispielsweise einen Service-Aufruf zur Löschung des Benutzers

        JOptionPane.showMessageDialog(this, "Benutzer mit dem Namen " + username + " gelöscht.");
        clearFields();
    }

    /**
     * Methode zum Leeren des Eingabefelds nach erfolgreicher Löschung.
     */
    private void clearFields() {
        usernameField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DeleteUserFrame frame = new DeleteUserFrame();
            frame.setVisible(true);
        });
    }
}
