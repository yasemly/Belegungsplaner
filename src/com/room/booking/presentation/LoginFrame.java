package com.room.booking.presentation;

import com.room.booking.dao.BaseUserDao;
import com.room.booking.dao.BaseUserDaoImpl;
import com.room.booking.model.BaseUser;
import com.room.booking.model.Employer;
import com.room.booking.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GUI-Frame für die Anmeldung von Benutzern.
 */
public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    // Data Access Object für Benutzer
    private BaseUserDao userDao;

    /**
     * Konstruktor für das LoginFrame.
     * Initialisiert die Komponenten und das Layout des Frames.
     */
    public LoginFrame() {
        userDao = new BaseUserDaoImpl();

        setTitle("Loginpage");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Benutzername-Label
        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(usernameLabel, gbc);

        // Benutzername-Eingabefeld
        usernameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(usernameField, gbc);

        // Passwort-Label
        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passwordLabel, gbc);

        // Passwort-Eingabefeld
        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(passwordField, gbc);

        // Anmelden-Button
        JButton loginButton = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(loginButton, gbc);

        // Registrieren-Button
        JButton backButton = new JButton("Register");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(backButton, gbc);

        // ActionListener für den Login-Button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginUser();
            }
        });

        // ActionListener für den Register-Button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegistrationFrame().setVisible(true);
                dispose();
            }
        });
    }

    /**
     * Prüft die eingegebenen Anmeldedaten und öffnet das entsprechende Fenster für User/Employer.
     */
    private void loginUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        BaseUser user = userDao.getUserByUsernameAndPassword(username, password);
        if (user != null) {
            if (user instanceof Employer) {
                new EmployerFrame((Employer) user).setVisible(true);
            } else if (user instanceof User) {
                new UserOverviewFrame((User) user).setVisible(true);
            }
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }

}
