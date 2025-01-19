package com.room.booking.presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Frame zum Hinzufügen eines neuen Arbeitgebers.
 */
public class AddEmployerFrame extends JFrame {

    private JTextField usernameField;
    private JTextField fullNameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField departmentField;

    public AddEmployerFrame() {
        setTitle("Arbeitgeber hinzufügen");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 10, 10));

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

        JButton addButton = new JButton("Hinzufügen");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEmployer();
            }
        });
        panel.add(addButton);

        add(panel);
    }

    private void addEmployer() {
        try {
            String username = usernameField.getText();
            String fullName = fullNameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String department = departmentField.getText();

            // DB logic or service call
            JOptionPane.showMessageDialog(this, "Arbeitgeber hinzugefügt: " + fullName);
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Fehler beim Hinzufügen des Arbeitgebers: " + ex.getMessage());
        }
    }

    private void clearFields() {
        usernameField.setText("");
        fullNameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        departmentField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AddEmployerFrame frame = new AddEmployerFrame();
            frame.setVisible(true);
        });
    }
}
