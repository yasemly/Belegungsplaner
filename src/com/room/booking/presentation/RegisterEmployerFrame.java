package com.room.booking.presentation;

import com.room.booking.dao.BaseUserDao;
import com.room.booking.dao.BaseUserDaoImpl;
import com.room.booking.model.Employer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GUI-Frame zur Registrierung eines neuen Arbeitgebers
 */
public class RegisterEmployerFrame extends JFrame {

    // Swing-Komponenten
    private JTextField usernameField;
    private JTextField fullNameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField departmentField; // Added for department input

    // Data Access Object für Benutzer
    private BaseUserDao userDao;

    /**
     * Konstruktor für das RegisterEmployerFrame.
     * Initialisiert die Komponenten und das Layout des Frames
     */
    public RegisterEmployerFrame() {
        userDao = new BaseUserDaoImpl();

        setTitle("Arbeitgeber-Registrierung");
        setSize(400, 350); // Increased height to accommodate department field
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2, 10, 10));

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

        // Abteilung (neu hinzugefügt)
        add(new JLabel("Abteilung:"));
        departmentField = new JTextField();
        add(departmentField);

        // Registrieren-Button
        JButton registerButton = new JButton("Registrieren");
        add(registerButton);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerEmployer();
            }
        });

        // Zurück zum Arbeitgeber-Fenster Button
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
     * Registriert einen neuen Arbeitgeber in der Datenbank
     */
    private void registerEmployer() {
        String username = usernameField.getText().trim();
        String fullName = fullNameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        String department = departmentField.getText().trim(); // Get department from input

        // Register the employer using BaseUserDaoImpl (korrigiert)
        Employer employer = new Employer(0, username, fullName, email, password, department);
        userDao.createBaseUser(employer);


        JOptionPane.showMessageDialog(this, "Arbeitgeber-Registrierung erfolgreich!");

        // Redirect to employer frame after registration
        openEmployerFrame();

        // Optionally, close the registration frame after registration
        dispose();
    }

    /**
     * Öffnet das EmployerFrame und schließt das aktuelle Fenster
     */
    private void openEmployerFrame() {
        // Hier sollten Sie den tatsächlichen Benutzer übergeben, nicht einen neuen erstellen
        // Verwenden Sie Employer, da Sie zum EmployerFrame wechseln
        EmployerFrame employerFrame = new EmployerFrame(new Employer(1, "john.doe@example.com", "John Doe", "john.doe@example.com", "password", "IT"));
        employerFrame.setVisible(true);
        dispose();
    }

    // ... (main-Methode bleibt unverändert)
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RegisterEmployerFrame registrationFrame = new RegisterEmployerFrame();
            registrationFrame.setVisible(true);
        });
    }
}
