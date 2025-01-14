package com.room.booking.presentation;

import com.room.booking.model.BaseUser;
import com.room.booking.model.Employer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployerFrame extends JFrame {

    // Swing Components for Employer Options
    private JButton addEmployerButton;
    private JButton deleteEmployerButton;
    private JButton updateEmployerButton;

    // Swing Components for User Options
    private JButton addUserButton;
    private JButton deleteUserButton;
    private JButton updateUserButton;

    // Swing Components for Room Options
    private JButton addRoomButton;
    private JButton deleteRoomButton;
    private JButton updateRoomButton;

    // Swing Components for Booking Options
    private JButton addBookingButton;
    private JButton deleteBookingButton;
    private JButton updateBookingButton;

    // The currently logged-in user
    private BaseUser user;

    /**
     * Constructor for EmployerFrame
     *
     * @param user The currently logged-in user (should be an employer)
     */
    public EmployerFrame(BaseUser user) {
        this.user = user;
        setTitle("Arbeitgeber-Seite");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 3)); // Layout adjusted to show all buttons

        // Initialize the buttons for different options
        initializeEmployerButtons();
        initializeUserButtons();
        initializeRoomButtons();
        initializeBookingButtons();
    }

    /**
     * Initializes the buttons for employer options and their action listeners.
     */
    private void initializeEmployerButtons() {
        addEmployerButton = new JButton("Arbeitgeber hinzufügen");
        add(addEmployerButton);
        addEmployerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegisterEmployerFrame().setVisible(true);
                dispose();
            }
        });

        deleteEmployerButton = new JButton("Arbeitgeber löschen");
        add(deleteEmployerButton);
        deleteEmployerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DeleteEmployerFrame().setVisible(true);
                dispose();
            }
        });

        updateEmployerButton = new JButton("Arbeitgeber aktualisieren");
        add(updateEmployerButton);
        updateEmployerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleEmployerOption("Update Employer");
            }
        });
    }

    /**
     * Initializes the buttons for user options and their action listeners.
     */
    private void initializeUserButtons() {
        addUserButton = new JButton("Benutzer hinzufügen");
        add(addUserButton);
        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddUserFrame().setVisible(true);
            }
        });

        deleteUserButton = new JButton("Benutzer löschen");
        add(deleteUserButton);
        deleteUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUserOption("Delete User");
            }
        });

        updateUserButton = new JButton("Benutzer aktualisieren");
        add(updateUserButton);
        updateUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUserOption("Update User");
            }
        });
    }

    /**
     * Initializes the buttons for room options and their action listeners.
     */
    private void initializeRoomButtons() {
        addRoomButton = new JButton("Raum hinzufügen");
        add(addRoomButton);
        addRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddRoomFrame().setVisible(true);
            }
        });

        deleteRoomButton = new JButton("Raum löschen");
        add(deleteRoomButton);
        deleteRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DeleteRoomFrame().setVisible(true);
            }
        });

        updateRoomButton = new JButton("Raum aktualisieren");
        add(updateRoomButton);
        updateRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRoomOption("Update Room");
            }
        });
    }

    /**
     * Initializes the buttons for booking options and their action listeners.
     */
    private void initializeBookingButtons() {
        addBookingButton = new JButton("Buchung hinzufügen");
        add(addBookingButton);
        addBookingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBookingOption("Add Booking");
            }
        });

        deleteBookingButton = new JButton("Buchung löschen");
        add(deleteBookingButton);
        deleteBookingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBookingOption("Delete Booking");
            }
        });

        updateBookingButton = new JButton("Buchung aktualisieren");
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

    // Stub frames to allow compilation. Replace these with your actual frame implementations.
    static class RegisterEmployerFrame extends JFrame {
        public RegisterEmployerFrame() {
            setTitle("Register Employer");
            setSize(300, 200);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            add(new JLabel("Register Employer Frame", SwingConstants.CENTER), BorderLayout.CENTER);
        }
    }

    static class DeleteEmployerFrame extends JFrame {
        public DeleteEmployerFrame() {
            setTitle("Delete Employer");
            setSize(300, 200);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            add(new JLabel("Delete Employer Frame", SwingConstants.CENTER), BorderLayout.CENTER);
        }
    }

    static class AddUserFrame extends JFrame {
        public AddUserFrame() {
            setTitle("Add User");
            setSize(300, 200);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            add(new JLabel("Add User Frame", SwingConstants.CENTER), BorderLayout.CENTER);
        }
    }

    static class AddRoomFrame extends JFrame {
        public AddRoomFrame() {
            setTitle("Add Room");
            setSize(300, 200);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            add(new JLabel("Add Room Frame", SwingConstants.CENTER), BorderLayout.CENTER);
        }
    }

    static class DeleteRoomFrame extends JFrame {
        public DeleteRoomFrame() {
            setTitle("Delete Room");
            setSize(300, 200);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            add(new JLabel("Delete Room Frame", SwingConstants.CENTER), BorderLayout.CENTER);
        }
    }

    // Uncomment the following main method to test the frame:
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BaseUser sampleUser = new Employer(1, "john.doe@example.com", "John Doe", "john.doe@example.com", "password", "IT");
            EmployerFrame frame = new EmployerFrame(sampleUser);
            frame.setVisible(true);
        });
    }
}
