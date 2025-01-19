package com.room.booking.presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Frame zum Aktualisieren der Arbeitgeberinformationen.
 */
public class UpdateEmployerFrame extends JFrame {

    private JTextField employerIdField;
    private JTextField usernameField;
    private JTextField fullNameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField departmentField;

    public UpdateEmployerFrame() {
        setTitle("Arbeitgeber aktualisieren");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2, 10, 10));

        panel.add(new JLabel("Arbeitgeber-ID:"));
        employerIdField = new JTextField();
        panel.add(employerIdField);

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

        panel.add(new JLabel("Abteilung:"));
        departmentField = new JTextField();
        panel.add(departmentField);

        JButton updateButton = new JButton("Aktualisieren");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateEmployer();
            }
        });
        panel.add(updateButton);

        add(panel);
    }

    private void updateEmployer() {
        try {
            int employerId = Integer.parseInt(employerIdField.getText());
            String username = usernameField.getText().trim();
            String fullName = fullNameField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword());
            String department = departmentField.getText().trim();

            // DB or service call
            JOptionPane.showMessageDialog(this, "Arbeitgeber mit ID " + employerId + " aktualisiert.");
            clearFields();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Bitte geben Sie eine gültige Arbeitgeber-ID ein.");
        }
    }

    private void clearFields() {
        employerIdField.setText("");
        usernameField.setText("");
        fullNameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        departmentField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UpdateEmployerFrame frame = new UpdateEmployerFrame();
            frame.setVisible(true);
        });
    }
}
