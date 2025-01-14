package com.room.booking.presentation;

import com.room.booking.dao.BaseUserDao;
import com.room.booking.dao.BaseUserDaoImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GUI-Frame zur Registrierung eines neuen Benutzers
 */
public class RegistrationFrame extends JFrame {

    // Swing-Komponenten
    private JTextField usernameField;
    private JTextField fullNameField;
    private JTextField emailField;
    private JPasswordField passwordField;

    /**
     * Konstruktor für das RegistrationFrame.
     * Initialisiert die Komponenten und das Layout des Frames
     */
    public RegistrationFrame() {
        setTitle("Benutzerregistrierung");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 10, 10));

        // Benutzername
        add(new JLabel("Benutzername:"));
        usernameField = new JTextField();
        add(usernameField);

        // Vollständiger Name
        add(new JLabel("Vollständiger Name:"));
        fullNameField = new JTextField();
        add(fullNameField);

        // E-Mail
        add(new JLabel("E-Mail:"));
        emailField = new JTextField();
        add(emailField);

        // Passwort
        add(new JLabel("Passwort:"));
        passwordField = new JPasswordField();
        add(passwordField);

        // Registrieren-Button
        JButton registerButton = new JButton("Registrieren");
        add(registerButton);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });

        // Zurück zum Anmelde-Fenster Button
        JButton loginButton = new JButton("Zurück zur Anmeldung");
        add(loginButton);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openLoginFrame();
            }
        });
    }

    /**
     * Registriert einen neuen Benutzer in der Datenbank
     */
    private void registerUser() {
        String username = usernameField.getText().trim();
        String fullName = fullNameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        BaseUserDao userDao = new BaseUserDaoImpl(); // Use BaseUserDao for registration
        String role = "user"; // Set default role or get from user input if needed
        userDao.registerUser(username, fullName, email, password);
        JOptionPane.showMessageDialog(this, "Registrierung erfolgreich!");

        // Weiterleitung zur Anmeldeseite nach der Registrierung
        openLoginFrame();

        // Schließen des Registrierungs-Frames nach der Registrierung (optional)
        dispose();
    }

    /**
     * Öffnet das LoginFrame und schließt das aktuelle Fenster
     */
    private void openLoginFrame() {
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setVisible(true);
        dispose(); // Schließen des Registrierungs-Frames nach Rückkehr zur Anmeldung
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RegistrationFrame registrationFrame = new RegistrationFrame();
            registrationFrame.setVisible(true);
        });
    }
}
