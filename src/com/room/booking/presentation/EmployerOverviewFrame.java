package com.room.booking.presentation;

import com.room.booking.dao.RoomDao;
import com.room.booking.dao.RoomDaoImpl;
import com.room.booking.dao.BookingDao;
import com.room.booking.dao.BookingDaoImpl;
import com.room.booking.dao.EmployerDao;
import com.room.booking.dao.EmployerDaoImpl;
import com.room.booking.dao.UserDao;
import com.room.booking.dao.UserDaoImpl;
import com.room.booking.model.Booking;
import com.room.booking.model.Employer;
import com.room.booking.model.Room;
import com.room.booking.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EmployerOverviewFrame extends JFrame {

    private Employer employer;
    private RoomDao roomDao;
    private BookingDao bookingDao;
    private EmployerDao employerDao;
    private UserDao userDao;

    private JTable roomTable;
    private DefaultTableModel roomTableModel;

    private JTable bookingTable;
    private DefaultTableModel bookingTableModel;

    public EmployerOverviewFrame(Employer employer) {
        this.employer = employer;
        roomDao = new RoomDaoImpl();
        bookingDao = new BookingDaoImpl();
        employerDao = new EmployerDaoImpl();
        userDao = new UserDaoImpl();

        setTitle("Arbeitgeber-Dashboard – Willkommen " + employer.getFullName());
        setSize(1100, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel contentPanel = new JPanel(new BorderLayout(10,10));
        contentPanel.setBorder(new EmptyBorder(15,15,15,15));
        setContentPane(contentPanel);

        JLabel headerLabel = new JLabel("Arbeitgeber-Dashboard");
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(headerLabel, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Tab: Räume verwalten (wie zuvor)
        JPanel roomPanel = new JPanel(new BorderLayout(10,10));
        roomPanel.setBorder(new EmptyBorder(10,10,10,10));
        roomTableModel = new DefaultTableModel(new Object[]{"Raum-ID", "Raumname", "Kapazität", "Standort", "Rating", "Stockwerk", "Ausstattung"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        roomTable = new JTable(roomTableModel);
        roomTable.setRowHeight(25);
        roomTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        roomTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JScrollPane roomScroll = new JScrollPane(roomTable);
        roomPanel.add(roomScroll, BorderLayout.CENTER);

        JPanel roomButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        JButton addRoomButton = new JButton("Neuen Raum hinzufügen");
        addRoomButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        addRoomButton.addActionListener(e -> {
            new AddRoomFrame().setVisible(true);
            refreshRooms();
        });
        JButton modifyRoomButton = new JButton("Raum bearbeiten/löschen");
        modifyRoomButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        modifyRoomButton.addActionListener(e -> {
            int selectedRow = roomTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Bitte wählen Sie einen Raum aus.");
            } else {
                int roomId = (Integer) roomTableModel.getValueAt(selectedRow, 0);
                new RoomManagementDialog(this, roomId).setVisible(true);
                refreshRooms();
            }
        });
        roomButtonPanel.add(addRoomButton);
        roomButtonPanel.add(modifyRoomButton);
        roomPanel.add(roomButtonPanel, BorderLayout.SOUTH);
        tabbedPane.addTab("Räume verwalten", roomPanel);

        // Tab: Buchungen verwalten – Alle Buchungen anzeigen
        JPanel bookingPanel = new JPanel(new BorderLayout(10,10));
        bookingPanel.setBorder(new EmptyBorder(10,10,10,10));
        bookingTableModel = new DefaultTableModel(new Object[]{"Buchungs-ID", "Raumname", "Buchender", "Zeitraum", "Zweck", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        bookingTable = new JTable(bookingTableModel);
        bookingTable.setRowHeight(25);
        bookingTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        bookingTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JScrollPane bookingScroll = new JScrollPane(bookingTable);
        bookingPanel.add(bookingScroll, BorderLayout.CENTER);

        JPanel bookingButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        JButton editBookingButton = new JButton("Buchung bearbeiten");
        editBookingButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        editBookingButton.addActionListener(e -> {
            int selectedRow = bookingTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Bitte wählen Sie eine Buchung aus.");
            } else {
                List<Booking> bookings = bookingDao.getAllBookings();
                Booking selectedBooking = bookings.get(selectedRow);
                int bookingId = selectedBooking.getBookingId();
                new BookingEditDialog(this, bookingId, employer).setVisible(true);
                refreshBookingsEmployer();
            }
        });
        JButton deleteBookingButton = new JButton("Buchung löschen");
        deleteBookingButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        deleteBookingButton.addActionListener(e -> {
            int selectedRow = bookingTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Bitte wählen Sie eine Buchung aus.");
            } else {
                List<Booking> bookings = bookingDao.getAllBookings();
                Booking selectedBooking = bookings.get(selectedRow);
                int bookingId = selectedBooking.getBookingId();
                int confirm = JOptionPane.showConfirmDialog(this, "Buchung " + bookingId + " wirklich löschen?", "Bestätigung", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    bookingDao.deleteBooking(bookingId);
                    JOptionPane.showMessageDialog(this, "Buchung gelöscht.");
                    refreshBookingsEmployer();
                }
            }
        });
        bookingButtonPanel.add(editBookingButton);
        bookingButtonPanel.add(deleteBookingButton);
        bookingPanel.add(bookingButtonPanel, BorderLayout.SOUTH);
        tabbedPane.addTab("Buchungen verwalten", bookingPanel);

        contentPanel.add(tabbedPane, BorderLayout.CENTER);

        // Footer: Konto verwalten und Logout
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        JButton accountButton = new JButton("Konto verwalten");
        accountButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        accountButton.addActionListener(e -> new AccountManagementFrame(employer).setVisible(true));
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        logoutButton.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
        bottomPanel.add(accountButton);
        bottomPanel.add(logoutButton);
        contentPanel.add(bottomPanel, BorderLayout.SOUTH);

        refreshRooms();
        refreshBookingsEmployer();
    }

    private void refreshRooms() {
        roomTableModel.setRowCount(0);
        List<Room> rooms = roomDao.getAllRooms();
        for (Room r : rooms) {
            Object[] row = {
                    r.getRoomId(),
                    r.getRoomName(),
                    r.getCapacity(),
                    r.getLocation(),
                    r.getRating(),
                    r.getFloor(),
                    String.join(", ", r.getFeatures())
            };
            roomTableModel.addRow(row);
        }
    }

    private void refreshBookingsEmployer() {
        bookingTableModel.setRowCount(0);
        List<Booking> bookings = bookingDao.getAllBookings();
        for (Booking b : bookings) {
            Room r = roomDao.getRoomById(b.getRoomId());
            String roomName = (r != null) ? r.getRoomName() : "Unbekannt";
            // Ermittle den Namen des Buchenden über UserDao
            User booker = new UserDaoImpl().getUserById(b.getUserId());
            String bookerName = (booker != null) ? booker.getFullName() : "Unbekannt";
            // Formatierung des Zeitraums
            String period = b.getStartTime().format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
                    + " - " + b.getEndTime().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"));
            Object[] row = {
                    b.getBookingId(),
                    roomName,
                    bookerName,
                    period,
                    b.getPurpose(),
                    b.getStatus()
            };
            bookingTableModel.addRow(row);
        }
    }

    public static void main(String[] args) {
        com.room.booking.model.Employer dummyEmployer = new com.room.booking.model.Employer(1, "admin", "Admin User", "admin@example.com", "password", "IT");
        SwingUtilities.invokeLater(() -> new EmployerOverviewFrame(dummyEmployer).setVisible(true));
    }
}
