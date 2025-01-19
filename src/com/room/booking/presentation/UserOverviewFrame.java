package com.room.booking.presentation;

import com.room.booking.dao.BookingDao;
import com.room.booking.dao.BookingDaoImpl;
import com.room.booking.dao.RoomDao;
import com.room.booking.dao.RoomDaoImpl;
import com.room.booking.dao.UserDao;
import com.room.booking.dao.UserDaoImpl;
import com.room.booking.model.Booking;
import com.room.booking.model.Room;
import com.room.booking.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UserOverviewFrame extends JFrame {

    private User user;
    private BookingDao bookingDao;
    private RoomDao roomDao;
    private UserDao userDao;

    private JTable bookingTable;
    private DefaultTableModel tableModel;

    public UserOverviewFrame(User user) {
        this.user = user;
        this.bookingDao = new BookingDaoImpl();
        this.roomDao = new RoomDaoImpl();
        this.userDao = new UserDaoImpl();

        setTitle("Benutzerübersicht - Buchungen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Größeres Fenster für besseren Überblick
        setSize(950, 650);
        setLocationRelativeTo(null);

        // Hauptpanel mit BorderLayout und Rand für Frische
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        setContentPane(contentPanel);

        // Kopfzeile: Begrüßung und Logo (optional)
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel headerLabel = new JLabel("Willkommen, " + user.getFullName());
        headerLabel.setFont(new Font("Arial", Font.BOLD, 28));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(headerLabel, BorderLayout.CENTER);
        contentPanel.add(headerPanel, BorderLayout.NORTH);

        // Tabelle, um alle Buchungen anzuzeigen
        tableModel = new DefaultTableModel(new Object[]{"Buchungs-ID", "Raum", "Start", "Ende", "Zweck", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Keine direkte Bearbeitung in der Tabelle
            }
        };
        bookingTable = new JTable(tableModel);
        bookingTable.setFont(new Font("Arial", Font.PLAIN, 14));
        bookingTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        bookingTable.setRowHeight(25);
        JScrollPane tableScroll = new JScrollPane(bookingTable);
        contentPanel.add(tableScroll, BorderLayout.CENTER);

        // Laden der Buchungen in die Tabelle
        loadBookingsIntoTable();

        // Fußzeile mit Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        // "Neue Buchung erstellen"
        JButton createBookingButton = new JButton("Neue Buchung erstellen");
        createBookingButton.setFont(new Font("Arial", Font.BOLD, 14));
        createBookingButton.addActionListener(e -> {
            new AddBookingFrame(user).setVisible(true);
            dispose();
        });
        // "Buchung bearbeiten/löschen"
        JButton modifyBookingButton = new JButton("Buchung bearbeiten/löschen");
        modifyBookingButton.setFont(new Font("Arial", Font.BOLD, 14));
        modifyBookingButton.addActionListener(e -> {
            int selectedRow = bookingTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Bitte wählen Sie eine Buchung zum Bearbeiten oder Löschen aus.");
            } else {
                int bookingId = (Integer) tableModel.getValueAt(selectedRow, 0);
                new BookingManagementDialog(this, bookingId, user).setVisible(true);
                loadBookingsIntoTable(); // Tabelle nach Änderungen aktualisieren
            }
        });
        // "Konto verwalten" (Aktualisieren/Löschen des Benutzerkontos)
        JButton accountButton = new JButton("Konto verwalten");
        accountButton.setFont(new Font("Arial", Font.BOLD, 14));
        accountButton.addActionListener(e -> {
            new AccountManagementFrame(user).setVisible(true);
            // Optional: dispose() falls das Konto gelöscht wird.
        });
        // "Logout" Button – zurück zum Login
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
        logoutButton.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });

        buttonPanel.add(createBookingButton);
        buttonPanel.add(modifyBookingButton);
        buttonPanel.add(accountButton);
        buttonPanel.add(logoutButton);

        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadBookingsIntoTable() {
        tableModel.setRowCount(0);
        List<Booking> bookings = bookingDao.getBookingsByUserId(user.getUserId());
        for (Booking b : bookings) {
            // Hole den Raumnamen über RoomDao, falls möglich
            Room room = roomDao.getRoomById(b.getRoomId());
            String roomName = (room != null) ? room.getRoomName() : "Unbekannt";
            Object[] row = {
                    b.getBookingId(),
                    roomName,
                    b.getStartTime(),
                    b.getEndTime(),
                    b.getPurpose(),
                    b.getStatus()
            };
            tableModel.addRow(row);
        }
    }

    public static void main(String[] args) {
        // Testweise mit Dummy-Daten
        User dummyUser = new User(1, "testuser", "Test User", "test@example.com", "password");
        SwingUtilities.invokeLater(() -> new UserOverviewFrame(dummyUser).setVisible(true));
    }
}
