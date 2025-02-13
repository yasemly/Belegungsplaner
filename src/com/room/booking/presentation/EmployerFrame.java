package com.room.booking.presentation;

import com.room.booking.dao.RoomDao;
import com.room.booking.dao.RoomDaoImpl;
import com.room.booking.dao.BookingDao;
import com.room.booking.dao.BookingDaoImpl;
import com.room.booking.dao.EmployerDao;
import com.room.booking.dao.EmployerDaoImpl;
import com.room.booking.model.Employer;
import com.room.booking.model.Room;
import com.room.booking.model.Booking;
import com.room.booking.model.User;  // Nur falls benötigt (z. B. für Account-Management)

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EmployerFrame extends JFrame {

    private Employer employer;  // Hier arbeiten wir ausschließlich mit Employer
    private RoomDao roomDao;
    private BookingDao bookingDao;
    private EmployerDao employerDao;

    private JTable roomTable;
    private DefaultTableModel roomTableModel;

    private JTable bookingTable;
    private DefaultTableModel bookingTableModel;

    public EmployerFrame(Employer employer) {
        // Hier übergeben wir auch unbedingt einen Employer!
        this.employer = employer;
        this.roomDao = new RoomDaoImpl();
        this.bookingDao = new BookingDaoImpl();
        this.employerDao = new EmployerDaoImpl();

        setTitle("Arbeitgeber-Dashboard – Willkommen " + employer.getFullName());
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Hauptpanel mit BorderLayout und Rändern
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        setContentPane(contentPanel);

        // Kopfzeile (Header)
        JLabel headerLabel = new JLabel("Arbeitgeber-Dashboard");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 28));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(headerLabel, BorderLayout.NORTH);

        // TabbedPane für zwei Bereiche: Räume und Buchungen verwalten
        JTabbedPane tabbedPane = new JTabbedPane();

        // Tab "Räume verwalten"
        JPanel roomPanel = new JPanel(new BorderLayout(10, 10));
        roomPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        roomTableModel = new DefaultTableModel(new Object[]{"Raum-ID", "Raumname", "Kapazität", "Standort", "Rating", "Stockwerk", "Ausstattung"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        roomTable = new JTable(roomTableModel);
        roomTable.setRowHeight(25);
        roomTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        roomTable.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane roomScroll = new JScrollPane(roomTable);
        roomPanel.add(roomScroll, BorderLayout.CENTER);

        JPanel roomButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        JButton addRoomButton = new JButton("Neuen Raum hinzufügen");
        addRoomButton.setFont(new Font("Arial", Font.BOLD, 14));
        addRoomButton.addActionListener(e -> {
            new AddRoomFrame().setVisible(true);
            refreshRoomsTable();
        });
        JButton modifyRoomButton = new JButton("Raum bearbeiten/löschen");
        modifyRoomButton.setFont(new Font("Arial", Font.BOLD, 14));
        modifyRoomButton.addActionListener(e -> {
            int selectedRow = roomTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Bitte wählen Sie einen Raum aus.");
            } else {
                int roomId = (Integer) roomTableModel.getValueAt(selectedRow, 0);
                new RoomManagementDialog(this, roomId).setVisible(true);
                refreshRoomsTable();
            }
        });
        roomButtonPanel.add(addRoomButton);
        roomButtonPanel.add(modifyRoomButton);
        roomPanel.add(roomButtonPanel, BorderLayout.SOUTH);
        tabbedPane.addTab("Räume verwalten", roomPanel);

        // Tab "Buchungen verwalten"
        JPanel bookingPanel = new JPanel(new BorderLayout(10, 10));
        bookingPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        bookingTableModel = new DefaultTableModel(new Object[]{"Buchungs-ID", "Raum", "Start", "Ende", "Zweck", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        bookingTable = new JTable(bookingTableModel);
        bookingTable.setRowHeight(25);
        bookingTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        bookingTable.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane bookingScroll = new JScrollPane(bookingTable);
        bookingPanel.add(bookingScroll, BorderLayout.CENTER);

        JPanel bookingButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        JButton manageBookingButton = new JButton("Buchung bearbeiten/löschen");
        manageBookingButton.setFont(new Font("Arial", Font.BOLD, 14));
        manageBookingButton.addActionListener(e -> {
            int selectedRow = bookingTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Bitte wählen Sie eine Buchung aus.");
            } else {
                int bookingId = (Integer) bookingTableModel.getValueAt(selectedRow, 0);
                new BookingManagementDialog(this, bookingId, employer).setVisible(true);
                refreshBookingsTable();
            }
        });
        bookingButtonPanel.add(manageBookingButton);
        bookingPanel.add(bookingButtonPanel, BorderLayout.SOUTH);
        tabbedPane.addTab("Buchungen verwalten", bookingPanel);

        contentPanel.add(tabbedPane, BorderLayout.CENTER);

        // Fußzeile: Konto verwalten und Logout
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        JButton accountButton = new JButton("Konto verwalten");
        accountButton.setFont(new Font("Arial", Font.BOLD, 14));
        accountButton.addActionListener(e -> new AccountManagementFrame(employer).setVisible(true));
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
        logoutButton.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
        bottomPanel.add(accountButton);
        bottomPanel.add(logoutButton);
        contentPanel.add(bottomPanel, BorderLayout.SOUTH);

        refreshRoomsTable();
        refreshBookingsTable();
    }

    private void refreshRoomsTable() {
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

    private void refreshBookingsTable() {
        bookingTableModel.setRowCount(0);
        // Hier rufen wir die Buchungen anhand der Employer-ID ab,
        // sofern in BookingDao die Methode getBookingsByUserId(int userId) existiert.
        // Falls in deiner Implementation Buchungen anhand eines User-Objekts abgerufen wurden,
        // musst du diese Methode so anpassen, dass sie einen int als Parameter erhält.
        List<Booking> bookings = bookingDao.getBookingsByUserId(employer.getEmployerId());
        for (Booking b : bookings) {
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
            bookingTableModel.addRow(row);
        }
    }

    public static void main(String[] args) {
        // Test mit Dummy-Arbeitgeberdaten
        Employer dummyEmployer = new Employer(1, "admin", "Admin User", "admin@example.com", "password", "IT");
        SwingUtilities.invokeLater(() -> new EmployerFrame(dummyEmployer).setVisible(true));
    }
}
