package com.room.booking.presentation;

import com.room.booking.dao.UserDao;
import com.room.booking.dao.UserDaoImpl;
import com.room.booking.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AccountManagementFrame extends JFrame {

    private User user;
    private UserDao userDao;

    private JTextField usernameField;
    private JTextField fullNameField;
    private JTextField emailField;
    private JPasswordField passwordField;

    public AccountManagementFrame(User user) {
        this.user = user;
        this.userDao = new UserDaoImpl();

        setTitle("Konto verwalten");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Felder für Kontoinformationen
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        usernameField = new JTextField(user.getUsername(), 20);
        add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Vollständiger Name:"), gbc);
        gbc.gridx = 1;
        fullNameField = new JTextField(user.getFullName(), 20);
        add(fullNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("E-Mail:"), gbc);
        gbc.gridx = 1;
        emailField = new JTextField(user.getEmail(), 20);
        add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Passwort:"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(user.getPassword(), 20);
        add(passwordField, gbc);

        // Buttons: Konto aktualisieren und Konto löschen
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton updateButton = new JButton("Konto aktualisieren");
        updateButton.addActionListener(this::handleUpdateAccount);
        JButton deleteButton = new JButton("Konto löschen");
        deleteButton.addActionListener(this::handleDeleteAccount);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        add(buttonPanel, gbc);
    }

    private void handleUpdateAccount(ActionEvent e) {
        try {
            String newUsername = usernameField.getText().trim();
            String newFullName = fullNameField.getText().trim();
            String newEmail = emailField.getText().trim();
            String newPassword = new String(passwordField.getPassword()).trim();

            // Aktualisieren der User-Daten – hier wird angenommen, dass updateUser() die Änderungen in der DB speichert
            User updatedUser = new User(user.getUserId(), newUsername, newFullName, newEmail, newPassword);
            userDao.updateUser(updatedUser);
            JOptionPane.showMessageDialog(this, "Konto aktualisiert.");
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Fehler beim Aktualisieren des Kontos: " + ex.getMessage());
        }
    }

    private void handleDeleteAccount(ActionEvent e) {
        int confirm = JOptionPane.showConfirmDialog(this, "Bist du sicher, dass du dein Konto löschen möchtest?", "Löschbestätigung", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            userDao.deleteUser(user.getUserId());
            JOptionPane.showMessageDialog(this, "Konto gelöscht.");
            // Zurück zur Login-Seite
            new LoginFrame().setVisible(true);
            dispose();
        }
    }
}
