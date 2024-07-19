package com.room.booking.presentation;

import com.room.booking.dao.UserDao;
import com.room.booking.dao.UserDaoImpl;
import com.room.booking.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterEmployerFrame extends JFrame {

    private JTextField usernameField;
    private JTextField fullNameField;
    private JTextField emailField;
    private JPasswordField passwordField;

    private UserDao userDao;

    public RegisterEmployerFrame() {
        userDao = new UserDaoImpl(); // Initialize UserDaoImpl

        setTitle("Employer Registration");
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
                registerEmployer();
            }
        });

        // Back to Employer Frame Button
        JButton backButton = new JButton("Back to Employer Frame");
        add(backButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openEmployerFrame();
            }
        });
    }

    private void registerEmployer() {
        String username = usernameField.getText().trim();
        String fullName = fullNameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        // Register the employer using UserDaoImpl
        userDao.registerEmployer(username, fullName, email, password);

        JOptionPane.showMessageDialog(this, "Employer registration successful!");

        // Redirect to employer frame after registration
        openEmployerFrame();

        // Optionally, close the registration frame after registration
        dispose();
    }

    private void openEmployerFrame() {
        User user = new User(1, "john.doe@example.com", "John Doe", "john.doe@example.com", "Admin", "password");
        EmployerFrame employerFrame = new EmployerFrame(user);
        employerFrame.setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RegisterEmployerFrame registrationFrame = new RegisterEmployerFrame();
            registrationFrame.setVisible(true);
        });
    }
}
