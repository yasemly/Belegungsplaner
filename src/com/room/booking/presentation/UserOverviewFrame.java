package com.room.booking.presentation;

import com.room.booking.dao.BookingDao;
import com.room.booking.dao.BookingDaoImpl;
import com.room.booking.model.Booking;
import com.room.booking.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * GUI-Frame zur Anzeige der bestehenden Buchungen eines Benutzers und zum Erstellen neuer Buchungen
 */
public class UserOverviewFrame extends JFrame {

    // Der aktuell angemeldete Benutzer
    private User user;
    // Data Access Object für Buchungen
    private BookingDao bookingDao;

    /**
     * Konstruktor für das UserOverviewFrame
     *
     * @param user Der aktuell angemeldete Benutzer
     */
    public UserOverviewFrame(User user) {
        this.user = user;
        this.bookingDao = new BookingDaoImpl();

        setTitle("Benutzerübersicht - Bestehende Buchungen");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Textbereich für Buchungen erstellen
        JTextArea bookingsTextArea = new JTextArea();
        bookingsTextArea.setEditable(false);
        bookingsTextArea.append("Bestehende Buchungen:\n");

        // Buchungen für den Benutzer abrufen
        try {
            List<Booking> bookings = bookingDao.getBookingsByUserId(user.getUserId());
            for (Booking booking : bookings) {
                bookingsTextArea.append("Buchungs-ID: " + booking.getBookingId() + "\n");
                bookingsTextArea.append("Raum-ID: " + booking.getRoomId() + "\n");
                bookingsTextArea.append("Start: " + booking.getStartTime() + "\n");
                bookingsTextArea.append("Ende: " + booking.getEndTime() + "\n");
                bookingsTextArea.append("Zweck: " + booking.getPurpose() + "\n");
                bookingsTextArea.append("Status: " + booking.getStatus() + "\n\n");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Bessere Fehlerbehandlung in einer realen Anwendung wäre angebracht
            bookingsTextArea.append("Fehler beim Abrufen der Buchungen.\n");
        }

        // Scrollbare Ansicht für den Textbereich erstellen
        JScrollPane scrollPane = new JScrollPane(bookingsTextArea);
        add(scrollPane, BorderLayout.CENTER);

        // Button zum Erstellen einer neuen Buchung
        JButton createBookingButton = new JButton("Neue Buchung erstellen");
        createBookingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openCreateBookingFrame();
            }
        });
        add(createBookingButton, BorderLayout.SOUTH);
    }

    /**
     * Öffnet das UserSearchFrame, um eine neue Buchung zu erstellen, und schließt das aktuelle Fenster
     */
    private void openCreateBookingFrame() {
        UserSearchFrame createBookingFrame = new UserSearchFrame(user);
        createBookingFrame.setVisible(true);
        this.dispose();
    }
}
