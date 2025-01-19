package com.room.booking.presentation;

import com.room.booking.dao.EmployerDao;
import com.room.booking.dao.EmployerDaoImpl;
import com.room.booking.model.Employer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterEmployerFrame extends JFrame {

    private JTextField usernameField;
    private JTextField fullNameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField departmentField;

    private EmployerDao employerDao;

    public RegisterEmployerFrame() {
        employerDao = new EmployerDaoImpl();

        setTitle("Employer Registration");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2, 10, 10));

        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Full Name:"));
        fullNameField = new JTextField();
        add(fullNameField);

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        add(new JLabel("Department:"));
        departmentField = new JTextField();
        add(departmentField);

        JButton registerButton = new JButton("Register");
        add(registerButton);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerEmployer();
            }
        });

        JButton backButton = new JButton("Back to Login");
        add(backButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginFrame().setVisible(true);
                dispose();
            }
        });
    }

    private void registerEmployer() {
        try {
            String username = usernameField.getText().trim();
            String fullName = fullNameField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword());
            String department = departmentField.getText().trim();

            Employer emp = new Employer(0, username, fullName, email, password, department);
            employerDao.createEmployer(emp);

            JOptionPane.showMessageDialog(this, "Employer registration successful!");
            new LoginFrame().setVisible(true);
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error registering employer: " + ex.getMessage());
        }
    }
}
