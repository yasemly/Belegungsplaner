package com.room.booking.presentation;

import com.room.booking.model.BaseUser;
import com.room.booking.model.Employer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GUI-Frame für die Arbeitgeber-Ansicht.
 * Bietet verschiedene Optionen für die Verwaltung von Arbeitgebern, Benutzern, Räumen und Buchungen.
 */
public class EmployerFrame extends JFrame {

    // Swing-Komponenten für Arbeitgeber-Optionen
    private JButton addEmployerButton;
    private JButton deleteEmployerButton;
    private JButton updateEmployerButton;

    // Swing-Komponenten für Benutzer-Optionen
    private JButton addUserButton;
    private JButton deleteUserButton;
    private JButton updateUserButton;

    // Swing-Komponenten für Raum-Optionen
    private JButton addRoomButton;
    private JButton deleteRoomButton;
    private JButton updateRoomButton;

    // Swing-Komponenten für Buchungs-Optionen
    private JButton addBookingButton;
    private JButton deleteBookingButton;
    private JButton updateBookingButton;

    // Der aktuell angemeldete Benutzer
    private BaseUser user;

    /**
     * Konstruktor für das EmployerFrame
     *
     * @param user Der aktuell angemeldete Benutzer (sollte ein Arbeitgeber sein)
     */
    public EmployerFrame(BaseUser user) {
        this.user = user;

        setTitle("Arbeitgeber-Seite");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 3)); // Layout angepasst, um alle Buttons anzuzeigen

        // Initialisierung der Buttons für Arbeitgeber-Optionen
        initializeEmployerButtons();

        // Initialisierung der Buttons für Benutzer-Optionen
        initializeUserButtons();

        // Initialisierung der Buttons für Raum-Optionen
        initializeRoomButtons();

        // Initialisierung der Buttons für Buchungs-Optionen
        initializeBookingButtons();
    }

    /**
     * Initialisiert die Buttons für Arbeitgeber-Optionen und deren ActionListener
     */
    private void initializeEmployerButtons() {
        addEmployerButton = new JButton("Arbeitgeber hinzufügen");
        add(addEmployerButton);
        addEmployerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // handleEmployerOption("Add Employer"); // Diese Zeile scheint nicht benötigt zu werden
                new RegisterEmployerFrame().setVisible(true);
                dispose();
            }
        });

        deleteEmployerButton = new JButton("Arbeitgeber löschen");
        add(deleteEmployerButton);
        deleteEmployerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // handleEmployerOption("Delete Employer"); // Diese Zeile scheint nicht benötigt zu werden
                new DeleteEmployerFrame().setVisible(true);
                dispose();
            }
        });

        updateEmployerButton = new JButton("Arbeitgeber aktualisieren");
        add(updateEmployerButton);
        updateEmployerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleEmployerOption("Update Employer"); // Implementieren Sie die Aktualisierungslogik hier
            }
        });
    }

    /**
     * Initialisiert die Buttons für Benutzer-Optionen und deren ActionListener
     */
    private void initializeUserButtons() {
        addUserButton = new JButton("Benutzer hinzufügen");
        add(addUserButton);
        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddUserFrame().setVisible(true); // Öffnen Sie das AddUserFrame
                // dispose(); // Schließen Sie das aktuelle Fenster, falls gewünscht
            }
        });

        deleteUserButton = new JButton("Benutzer löschen");
        add(deleteUserButton);
        deleteUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUserOption("Delete User"); // Implementieren Sie die Löschlogik hier
            }
        });

        updateUserButton = new JButton("Benutzer aktualisieren");
        add(updateUserButton);
        updateUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUserOption("Update User"); // Implementieren Sie die Aktualisierungslogik hier
            }
        });
    }

    /**
     * Initialisiert die Buttons für Raum-Optionen und deren ActionListener
     */
    private void initializeRoomButtons() {
        addRoomButton = new JButton("Raum hinzufügen");
        add(addRoomButton);
        addRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddRoomFrame().setVisible(true); // Öffnen Sie das AddRoomFrame
                // dispose(); // Schließen Sie das aktuelle Fenster, falls gewünscht
            }
        });

        deleteRoomButton = new JButton("Raum löschen");
        add(deleteRoomButton);
        deleteRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DeleteRoomFrame().setVisible(true); // Öffnen Sie das DeleteRoomFrame
                // dispose(); // Schließen Sie das aktuelle Fenster, falls gewünscht
            }
        });

        updateRoomButton = new JButton("Raum aktualisieren");
        add(updateRoomButton);
        updateRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRoomOption("Update Room"); // Implementieren Sie die Aktualisierungslogik hier
            }
        });
    }

    /**
     * Initialisiert die Buttons für Buchungs-Optionen und deren ActionListener
     */
    private void initializeBookingButtons() {
        addBookingButton = new JButton("Buchung hinzufügen");
        add(addBookingButton);
        addBookingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBookingOption("Add Booking"); // Implementieren Sie die Hinzufügen-Logik hier
            }
        });

        deleteBookingButton = new JButton("Buchung löschen");
        add(deleteBookingButton);
        deleteBookingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBookingOption("Delete Booking"); // Implementieren Sie die Löschlogik hier
            }
        });

        updateBookingButton = new JButton("Buchung aktualisieren");
        add(updateBookingButton);
        updateBookingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBookingOption("Update Booking"); // Implementieren Sie die Aktualisierungslogik hier
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

    // Uncomment this method if you want to test the frame
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            BaseUser user = new Employer(1, "john.doe@example.com", "John Doe", "john.doe@example.com", "password");
//            EmployerFrame employerFrame = new EmployerFrame(user);
//            employerFrame.setVisible(true);
//        });
//    }
}
