package com.room.booking.presentation;

import com.room.booking.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployerFrame extends JFrame {
    private JButton addEmployerButton;
    private JButton deleteEmployerButton;
    private JButton updateEmployerButton;
    private JButton addUserButton;
    private JButton deleteUserButton;
    private JButton updateUserButton;
    private JButton addRoomButton;
    private JButton deleteRoomButton;
    private JButton updateRoomButton;
    private JButton addBookingButton;
    private JButton deleteBookingButton;
    private JButton updateBookingButton;
    private User user;

    public EmployerFrame(User user) {
        this.user = user;

        setTitle("Employer Page");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2)); // Adjust layout

        // Initialize buttons for employer options
        addEmployerButton = new JButton("Add Employer");
        add(addEmployerButton);
        addEmployerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleEmployerOption("Add Employer");
                new RegisterEmployerFrame().setVisible(true);
                dispose();
            }
        });

        deleteEmployerButton = new JButton("Delete Employer");
        add(deleteEmployerButton);
        deleteEmployerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleEmployerOption("Delete Employer");
                new DeleteEmployerFrame().setVisible(true);
                dispose();
            }
        });

        updateEmployerButton = new JButton("Update Employer");
        add(updateEmployerButton);
        updateEmployerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleEmployerOption("Update Employer");
            }
        });

        // Initialize buttons for user options
        addUserButton = new JButton("Add User");
        add(addUserButton);
        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUserOption("Add User");
            }
        });

        deleteUserButton = new JButton("Delete User");
        add(deleteUserButton);
        deleteUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUserOption("Delete User");
            }
        });

        updateUserButton = new JButton("Update User");
        add(updateUserButton);
        updateUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUserOption("Update User");
            }
        });

        // Initialize buttons for room options
        addRoomButton = new JButton("Add Room");
        add(addRoomButton);
        addRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRoomOption("Add Room");
            }
        });

        deleteRoomButton = new JButton("Delete Room");
        add(deleteRoomButton);
        deleteRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRoomOption("Delete Room");
            }
        });

        updateRoomButton = new JButton("Update Room");
        add(updateRoomButton);
        updateRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRoomOption("Update Room");
            }
        });

        // Initialize buttons for booking options
        addBookingButton = new JButton("Add Booking");
        add(addBookingButton);
        addBookingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBookingOption("Add Booking");
            }
        });

        deleteBookingButton = new JButton("Delete Booking");
        add(deleteBookingButton);
        deleteBookingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBookingOption("Delete Booking");
            }
        });

        updateBookingButton = new JButton("Update Booking");
        add(updateBookingButton);
        updateBookingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBookingOption("Update Booking");
            }
        });
    }

    private void handleEmployerOption(String option) {
        JOptionPane.showMessageDialog(this, "Handling employer option: " + option);
    }

    private void handleUserOption(String option) {
        JOptionPane.showMessageDialog(this, "Handling user option: " + option);
    }

    private void handleRoomOption(String option) {
        JOptionPane.showMessageDialog(this, "Handling room option: " + option);
    }

    private void handleBookingOption(String option) {
        JOptionPane.showMessageDialog(this, "Handling booking option: " + option);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            User user = new User(1, "john.doe@example.com", "John Doe", "john.doe@example.com", "Admin", "password");
            EmployerFrame employerFrame = new EmployerFrame(user);
            employerFrame.setVisible(true);
        });
    }
}
