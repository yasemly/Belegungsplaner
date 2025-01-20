package com.room.booking.presentation;

import com.room.booking.dao.UserDao;
import com.room.booking.dao.UserDaoImpl;
import com.room.booking.model.User;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ProfileManagementFrame extends JFrame {

    private User user;
    private UserDao userDao;

    public ProfileManagementFrame(User user) {
        this.user = user;
        userDao = new UserDaoImpl();

        setTitle("Profil bearbeiten – " + user.getFullName());
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10,10));
        panel.setBorder(new EmptyBorder(20,20,20,20));

        JLabel header = new JLabel("Profil bearbeiten");
        header.setFont(new Font("SansSerif", Font.BOLD, 28));
        header.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(header, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.add(new JLabel("Vollständiger Name:"));
        JTextField fullNameField = new JTextField(user.getFullName());
        formPanel.add(fullNameField);

        formPanel.add(new JLabel("E-Mail:"));
        JTextField emailField = new JTextField(user.getEmail());
        formPanel.add(emailField);

        formPanel.add(new JLabel("Benutzername:"));
        JTextField usernameField = new JTextField(user.getUsername());
        formPanel.add(usernameField);

        formPanel.add(new JLabel("Passwort:"));
        JPasswordField passwordField = new JPasswordField(user.getPassword());
        formPanel.add(passwordField);

        panel.add(formPanel, BorderLayout.CENTER);

        JButton saveButton = new JButton("Änderungen speichern");
        saveButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        saveButton.addActionListener(e -> {
            user.setFullName(fullNameField.getText().trim());
            user.setEmail(emailField.getText().trim());
            user.setUsername(usernameField.getText().trim());
            user.setPassword(new String(passwordField.getPassword()));
            userDao.updateUser(user);
            JOptionPane.showMessageDialog(this, "Profil wurde aktualisiert.");
            dispose();
        });
        panel.add(saveButton, BorderLayout.SOUTH);

        add(panel);
    }

    public static void main(String[] args) {
        User dummyUser = new User(1, "alice", "Alice Doe", "alice@example.com", "passw0rd");
        SwingUtilities.invokeLater(() -> new ProfileManagementFrame(dummyUser).setVisible(true));
    }
}
