package com.room.booking.presentation;

import com.room.booking.dao.UserDao;
import com.room.booking.dao.UserDaoImpl;
import com.room.booking.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrationFrame extends JFrame {

    private JTextField usernameField;
    private JTextField fullNameField;
    private JTextField emailField;
    private JPasswordField passwordField;

    public RegistrationFrame() {
        setTitle("User Registration");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 10, 10));

        // Username
        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        // Full Name
        add(new JLabel("Full Name:"));
        fullNameField = new JTextField();
        add(fullNameField);

        // Email
        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        // Password
        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        // Register Button
        JButton registerButton = new JButton("Register");
        add(registerButton);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });

        // Back to Login Button
        JButton loginButton = new JButton("Back to Login");
        add(loginButton);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openLoginFrame();
            }
        });
    }

    private void registerUser() {
        String username = usernameField.getText().trim();
        String fullName = fullNameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        UserDao userDao = new UserDaoImpl();
        userDao.registerUser(username, fullName, email, password);

        JOptionPane.showMessageDialog(this, "Registration successful!");

        // Redirect to login page after registration
        openLoginFrame();

        // Optionally, close the registration frame after registration
        dispose();
    }

    private void openLoginFrame() {
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setVisible(true);
        dispose(); // Close registration frame after going back to login
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RegistrationFrame registrationFrame = new RegistrationFrame();
            registrationFrame.setVisible(true);
        });
    }
}
