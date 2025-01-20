package com.room.booking.presentation;

import com.room.booking.dao.UserDao;
import com.room.booking.dao.UserDaoImpl;
import com.room.booking.dao.EmployerDao;
import com.room.booking.dao.EmployerDaoImpl;
import com.room.booking.model.User;
import com.room.booking.model.Employer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    private UserDao userDao;
    private EmployerDao employerDao;

    public LoginFrame() {
        userDao = new UserDaoImpl();
        employerDao = new EmployerDaoImpl();

        setTitle("Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel userLabel = new JLabel("Benutzername:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(userLabel, gbc);

        usernameField = new JTextField(20);
        gbc.gridx = 1;
        add(usernameField, gbc);

        JLabel passLabel = new JLabel("Passwort:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passLabel, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        add(passwordField, gbc);

        JButton loginButton = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(loginButton, gbc);

        JButton registerButton = new JButton("Registrieren");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(registerButton, gbc);

        loginButton.addActionListener((ActionEvent e) -> {
            attemptLogin();
        });

        registerButton.addActionListener((ActionEvent e) -> {
            new RegistrationFrame().setVisible(true);
            dispose();
        });
    }

    private void attemptLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        // Zunächst als normaler User prüfen
        User user = userDao.getUserByUsernameAndPassword(username, password);
        if (user != null) {
            new UserOverviewFrame(user).setVisible(true);
            dispose();
            return;
        }

        // Dann als Arbeitgeber prüfen
        Employer employer = employerDao.getEmployerByUsernameAndPassword(username, password);
        if (employer != null) {
            new EmployerOverviewFrame(employer).setVisible(true);
            dispose();
            return;
        }

        JOptionPane.showMessageDialog(this, "Ungültiger Benutzername oder Passwort.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
