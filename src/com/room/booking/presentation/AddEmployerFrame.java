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

    /**
     * Konstruktor für AddEmployerFrame.
     * Initialisiert das Fenster und seine Komponenten.
     */
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

    /**
     * Methode zum Hinzufügen eines neuen Arbeitgebers.
     * Erfasst die Eingaben des Benutzers und führt die Registrierung durch.
     */
    private void addEmployer() {
        String username = usernameField.getText();
        String fullName = fullNameField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        String department = departmentField.getText();

        // Logik zum Hinzufügen des Arbeitgebers in die Datenbank oder andere Aktionen.
        // Hier könnten Sie einen Service-Aufruf hinzufügen, um den Arbeitgeber zu registrieren.

        JOptionPane.showMessageDialog(this, "Arbeitgeber hinzugefügt: " + fullName);
        clearFields();
    }

    /**
     * Methode zum Leeren der Eingabefelder nach erfolgreicher Registrierung.
     */
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
