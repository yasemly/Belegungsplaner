package com.room.booking.presentation;

import com.room.booking.dao.BookingDao;
import com.room.booking.dao.BookingDaoImpl;
import com.room.booking.dao.UserDao;
import com.room.booking.dao.UserDaoImpl;
import com.room.booking.dao.RoomDao;
import com.room.booking.dao.RoomDaoImpl;
import com.room.booking.model.Booking;
import com.room.booking.model.Room;
import com.room.booking.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class UserOverviewFrame extends JFrame {

    private User user;
    private BookingDao bookingDao;
    private UserDao userDao; // Für Profilverwaltung
    private RoomDao roomDao;
    private JTable bookingTable;
    private DefaultTableModel tableModel;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public UserOverviewFrame(User user) {
        this.user = user;
        bookingDao = new BookingDaoImpl();
        userDao = new UserDaoImpl();
        roomDao = new RoomDaoImpl();

        setTitle("Benutzerübersicht – Willkommen " + user.getFullName());
        setSize(1100, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Hauptpanel: BorderLayout mit großzügigen Rändern
        JPanel contentPanel = new JPanel(new BorderLayout(10,10));
        contentPanel.setBorder(new EmptyBorder(20,20,20,20));
        setContentPane(contentPanel);

        // Header
        JLabel headerLabel = new JLabel("Ihr Buchungsdashboard");
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(headerLabel, BorderLayout.NORTH);

        // Tabelle zur Anzeige der Buchungen – Spalten: Raumname, Zeitraum, Zweck, Status
        tableModel = new DefaultTableModel(new Object[]{"Raumname", "Zeitraum", "Zweck", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        bookingTable = new JTable(tableModel);
        bookingTable.setRowHeight(30);
        bookingTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 18));
        bookingTable.setFont(new Font("SansSerif", Font.PLAIN, 16));
        JScrollPane tableScrollPane = new JScrollPane(bookingTable);
        contentPanel.add(tableScrollPane, BorderLayout.CENTER);

        // Button-Panel im Footer
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 15));

        JButton editBookingButton = new JButton("Buchung bearbeiten");
        editBookingButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        editBookingButton.addActionListener((ActionEvent e) -> {
            int selectedRow = bookingTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Bitte wählen Sie eine Buchung zum Bearbeiten aus.");
            } else {
                // Annahme: Die Reihenfolge in der Tabelle entspricht der Liste der Buchungen
                List<Booking> bookings = bookingDao.getBookingsByUserId(user.getUserId());
                Booking selectedBooking = bookings.get(selectedRow);
                int bookingId = selectedBooking.getBookingId();
                new BookingEditDialog(this, bookingId, user).setVisible(true);
                refreshBookings();
            }
        });
        buttonPanel.add(editBookingButton);

        JButton deleteBookingButton = new JButton("Buchung löschen");
        deleteBookingButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        deleteBookingButton.addActionListener((ActionEvent e) -> {
            int selectedRow = bookingTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Bitte wählen Sie eine Buchung zum Löschen aus.");
            } else {
                List<Booking> bookings = bookingDao.getBookingsByUserId(user.getUserId());
                Booking selectedBooking = bookings.get(selectedRow);
                int bookingId = selectedBooking.getBookingId();
                int confirm = JOptionPane.showConfirmDialog(this, "Buchung " + bookingId + " wirklich löschen?", "Bestätigung", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    bookingDao.deleteBooking(bookingId);
                    JOptionPane.showMessageDialog(this, "Buchung gelöscht.");
                    refreshBookings();
                }
            }
        });
        buttonPanel.add(deleteBookingButton);

        JButton newBookingButton = new JButton("Neue Buchung erstellen");
        newBookingButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        newBookingButton.addActionListener((ActionEvent e) -> {
            new AddBookingFrame(user).setVisible(true);
            dispose();
        });
        buttonPanel.add(newBookingButton);

        JButton editProfileButton = new JButton("Profil bearbeiten");
        editProfileButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        editProfileButton.addActionListener((ActionEvent e) -> {
            new ProfileManagementFrame(user).setVisible(true);
        });
        buttonPanel.add(editProfileButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        logoutButton.addActionListener((ActionEvent e) -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
        buttonPanel.add(logoutButton);

        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        refreshBookings();
    }

    private void refreshBookings() {
        tableModel.setRowCount(0);
        List<Booking> bookings = bookingDao.getBookingsByUserId(user.getUserId());
        for (Booking b : bookings) {
            Room r = roomDao.getRoomById(b.getRoomId());
            String roomName = (r != null) ? r.getRoomName() : "Unbekannt";
            // Formatierung des Zeitraums: TT.MM.JJJJ HH:mm - HH:mm
            String period = b.getStartTime().format(dtf) + " - " + b.getEndTime().format(DateTimeFormatter.ofPattern("HH:mm"));
            Object[] row = {
                    roomName,
                    period,
                    b.getPurpose(),
                    b.getStatus()
            };
            tableModel.addRow(row);
        }
    }

    public static void main(String[] args) {
        User dummyUser = new User(1, "alice", "Alice Doe", "alice@example.com", "passw0rd");
        SwingUtilities.invokeLater(() -> new UserOverviewFrame(dummyUser).setVisible(true));
    }
}
