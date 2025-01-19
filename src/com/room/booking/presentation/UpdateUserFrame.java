package com.room.booking.presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Frame zum Aktualisieren der Benutzerinformationen.
 */
public class UpdateUserFrame extends JFrame {

    private JTextField userIdField;
    private JTextField usernameField;
    private JTextField fullNameField;
    private JTextField emailField;
    private JPasswordField passwordField;

    public UpdateUserFrame() {
        setTitle("Benutzer aktualisieren");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 10, 10));

        panel.add(new JLabel("Benutzer-ID:"));
        userIdField = new JTextField();
        panel.add(userIdField);

        panel.add(new JLabel("Benutzername:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Vollständiger Name:"));
        fullNameField = new JTextField();
        panel.add(fullNameField);

        panel.add(new JLabel("E-Mail:"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Passwort:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton updateButton = new JButton("Aktualisieren");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateUser();
            }
        });
        panel.add(updateButton);

        add(panel);
    }

    private void updateUser() {
        try {
            int userId = Integer.parseInt(userIdField.getText());
            String username = usernameField.getText().trim();
            String fullName = fullNameField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword());

            // DB or service call
            JOptionPane.showMessageDialog(this, "Benutzer mit ID " + userId + " aktualisiert.");
            clearFields();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Bitte geben Sie eine gültige Benutzer-ID ein.");
        }
    }

    private void clearFields() {
        userIdField.setText("");
        usernameField.setText("");
        fullNameField.setText("");
        emailField.setText("");
        passwordField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UpdateUserFrame frame = new UpdateUserFrame();
            frame.setVisible(true);
        });
    }
}
