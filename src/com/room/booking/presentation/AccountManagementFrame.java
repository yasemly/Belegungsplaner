package com.room.booking.presentation;

import com.room.booking.dao.EmployerDao;
import com.room.booking.dao.EmployerDaoImpl;
import com.room.booking.model.Employer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AccountManagementFrame extends JFrame {

    private Employer employer;
    private EmployerDao employerDao;

    private JTextField usernameField;
    private JTextField fullNameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField departmentField;

    public AccountManagementFrame(Employer employer) {
        this.employer = employer;
        this.employerDao = new EmployerDaoImpl();

        setTitle("Konto verwalten");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        setContentPane(contentPanel);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        contentPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        usernameField = new JTextField(employer.getUsername(), 20);
        contentPanel.add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        contentPanel.add(new JLabel("Vollständiger Name:"), gbc);
        gbc.gridx = 1;
        fullNameField = new JTextField(employer.getFullName(), 20);
        contentPanel.add(fullNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        contentPanel.add(new JLabel("E-Mail:"), gbc);
        gbc.gridx = 1;
        emailField = new JTextField(employer.getEmail(), 20);
        contentPanel.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        contentPanel.add(new JLabel("Passwort:"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(employer.getPassword(), 20);
        contentPanel.add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        contentPanel.add(new JLabel("Abteilung:"), gbc);
        gbc.gridx = 1;
        departmentField = new JTextField(employer.getDepartment(), 20);
        contentPanel.add(departmentField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton updateButton = new JButton("Konto aktualisieren");
        updateButton.addActionListener(this::handleUpdate);
        JButton deleteButton = new JButton("Konto löschen");
        deleteButton.addActionListener(this::handleDelete);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        contentPanel.add(buttonPanel, gbc);
    }

    private void handleUpdate(ActionEvent e) {
        try {
            String newUsername = usernameField.getText().trim();
            String newFullName = fullNameField.getText().trim();
            String newEmail = emailField.getText().trim();
            String newPassword = new String(passwordField.getPassword()).trim();
            String newDepartment = departmentField.getText().trim();

            Employer updatedEmployer = new Employer(employer.getEmployerId(), newUsername, newFullName, newEmail, newPassword, newDepartment);
            employerDao.updateEmployer(updatedEmployer);
            JOptionPane.showMessageDialog(this, "Konto aktualisiert.");
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Fehler beim Aktualisieren des Kontos: " + ex.getMessage());
        }
    }

    private void handleDelete(ActionEvent e) {
        int confirm = JOptionPane.showConfirmDialog(this, "Möchten Sie das Konto wirklich löschen?", "Konto löschen", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            employerDao.deleteEmployer(employer.getEmployerId());
            JOptionPane.showMessageDialog(this, "Konto gelöscht.");
            new LoginFrame().setVisible(true);
            dispose();
        }
    }
}
