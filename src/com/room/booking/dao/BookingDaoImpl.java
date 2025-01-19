package com.room.booking.dao;

import com.room.booking.model.Booking;
import com.room.booking.util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementierung des BookingDao-Interfaces für den Datenbankzugriff auf Buchungen.
 */
public class BookingDaoImpl implements BookingDao {

    // SQL-Abfragen für die verschiedenen Operationen:
    private static final String SELECT_BOOKINGS_BY_USER_ID_SQL =
            "SELECT * FROM bookings WHERE user_id = ?";

    private static final String SELECT_BOOKINGS_BY_USERNAME_SQL =
            "SELECT b.* FROM bookings b JOIN users u ON b.user_id = u.user_id WHERE u.username = ?";

    private static final String INSERT_BOOKING_SQL =
            "INSERT INTO bookings (user_id, room_id, booking_start, booking_end, purpose, status) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_BOOKING_SQL =
            "UPDATE bookings SET booking_start = ?, booking_end = ?, purpose = ?, status = ? WHERE booking_id = ?";

    private static final String DELETE_BOOKING_SQL =
            "DELETE FROM bookings WHERE booking_id = ?";

    private static final String SELECT_BOOKING_BY_ID_SQL =
            "SELECT * FROM bookings WHERE booking_id = ?";

    private static final String SELECT_BOOKINGS_FOR_ROOM_ON_DATE_SQL =
            "SELECT * FROM bookings WHERE room_id = ? AND DATE(booking_start) = ?";

    @Override
    public List<Booking> getBookingsForRoomOnDate(int roomId, LocalDate date) {
        List<Booking> result = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BOOKINGS_FOR_ROOM_ON_DATE_SQL)) {

            stmt.setInt(1, roomId);
            stmt.setDate(2, java.sql.Date.valueOf(date));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    result.add(mapResultSetToBooking(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting bookings for room on date", e);
        }
        return result;
    }

    @Override
    public List<Booking> getBookingsByUserId(int userId) {
        List<Booking> bookings = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_BOOKINGS_BY_USER_ID_SQL)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    bookings.add(mapResultSetToBooking(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Abrufen von Buchungen nach Benutzer-ID", e);
        }
        return bookings;
    }

    @Override
    public List<Booking> getBookingsByUsername(String username) {
        List<Booking> bookings = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_BOOKINGS_BY_USERNAME_SQL)) {

            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    bookings.add(mapResultSetToBooking(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Abrufen von Buchungen nach Benutzernamen", e);
        }
        return bookings;
    }

    @Override
    public void createBooking(int userId, int roomId, LocalDateTime bookingStart, LocalDateTime bookingEnd,
                              String purpose, String status) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_BOOKING_SQL, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, userId);
            ps.setInt(2, roomId);
            ps.setTimestamp(3, Timestamp.valueOf(bookingStart));
            ps.setTimestamp(4, Timestamp.valueOf(bookingEnd));
            ps.setString(5, purpose);
            ps.setString(6, status);

            ps.executeUpdate();
            // Optional: abrufen der generierten Buchungs-ID
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int bookingId = generatedKeys.getInt(1);
                    // Hier kannst du bookingId verarbeiten oder einfach ignorieren
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Erstellen der Buchung", e);
        }
    }

    @Override
    public void updateBooking(int bookingId, LocalDateTime bookingStart, LocalDateTime bookingEnd,
                              String purpose, String status) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_BOOKING_SQL)) {

            ps.setTimestamp(1, Timestamp.valueOf(bookingStart));
            ps.setTimestamp(2, Timestamp.valueOf(bookingEnd));
            ps.setString(3, purpose);
            ps.setString(4, status);
            ps.setInt(5, bookingId);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Aktualisieren der Buchung", e);
        }
    }

    @Override
    public void deleteBooking(int bookingId) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_BOOKING_SQL)) {

            ps.setInt(1, bookingId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Löschen der Buchung", e);
        }
    }

    @Override
    public Booking getBookingById(int bookingId) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_BOOKING_BY_ID_SQL)) {

            ps.setInt(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToBooking(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Abrufen der Buchung nach ID", e);
        }
        return null;
    }

    private Booking mapResultSetToBooking(ResultSet rs) throws SQLException {
        return new Booking(
                rs.getInt("booking_id"),
                rs.getInt("user_id"),
                rs.getInt("room_id"),
                rs.getTimestamp("booking_start").toLocalDateTime(),
                rs.getTimestamp("booking_end").toLocalDateTime(),
                rs.getString("purpose"),
                rs.getString("status")
        );
    }
}
