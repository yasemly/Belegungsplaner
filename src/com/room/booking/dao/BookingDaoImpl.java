package com.room.booking.dao;

import com.room.booking.model.Booking;
import com.room.booking.util.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementierung des BookingDao-Interfaces für den Datenbankzugriff auf Buchungen
 */
public class BookingDaoImpl implements BookingDao {

    // SQL-Abfragen
    private static final String SELECT_BOOKINGS_BY_USER_ID_SQL = "SELECT * FROM bookings WHERE user_id = ?";
    private static final String SELECT_BOOKINGS_BY_USERNAME_SQL = "SELECT b.* FROM bookings b JOIN users u ON b.user_id = u.user_id WHERE u.username = ?";
    private static final String INSERT_BOOKING_SQL = "INSERT INTO bookings (user_id, room_id, booking_start, booking_end, purpose, status) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_BOOKING_SQL = "UPDATE bookings SET booking_start = ?, booking_end = ?, purpose = ?, status = ? WHERE booking_id = ?";
    private static final String DELETE_BOOKING_SQL = "DELETE FROM bookings WHERE booking_id = ?";

    /**
     * Holt alle Buchungen eines bestimmten Benutzers anhand seiner Benutzer-ID.
     *
     * @param userId Die ID des Benutzers.
     * @return Eine Liste aller Buchungen des Benutzers.
     */
    @Override
    public List<Booking> getBookingsByUserId(int userId) {
        List<Booking> bookings = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_BOOKINGS_BY_USER_ID_SQL)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    bookings.add(mapResultSetToBooking(rs)); // Use helper method to map ResultSet
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Abrufen von Buchungen nach Benutzer-ID", e);
        }

        return bookings;
    }

    /**
     * Holt alle Buchungen eines bestimmten Benutzers anhand seines Benutzernamens.
     *
     * @param username Der Benutzername des Benutzers.
     * @return Eine Liste aller Buchungen des Benutzers.
     */
    @Override
    public List<Booking> getBookingsByUsername(String username) {
        List<Booking> bookings = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_BOOKINGS_BY_USERNAME_SQL)) {

            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    bookings.add(mapResultSetToBooking(rs)); // Use helper method to map ResultSet
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Abrufen von Buchungen nach Benutzernamen", e);
        }

        return bookings;
    }

    /**
     * Erstellt eine neue Buchung in der Datenbank.
     *
     * @param userId       Die ID des Benutzers, der die Buchung erstellt.
     * @param roomId       Die ID des gebuchten Raums.
     * @param bookingStart Der Beginn der Buchung.
     * @param bookingEnd   Das Ende der Buchung.
     * @param purpose      Der Zweck der Buchung.
     * @param status       Der Status der Buchung (z.B. "bestätigt", "storniert").
     */
    @Override
    public void createBooking(int userId, int roomId, LocalDateTime bookingStart, LocalDateTime bookingEnd, String purpose, String status) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_BOOKING_SQL, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, userId);
            ps.setInt(2, roomId);
            ps.setTimestamp(3, Timestamp.valueOf(bookingStart));
            ps.setTimestamp(4, Timestamp.valueOf(bookingEnd));
            ps.setString(5, purpose);
            ps.setString(6, status);

            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int bookingId = generatedKeys.getInt(1);
                    // Optional: Setzen Sie die generierte bookingId auf das Booking-Objekt oder verwenden Sie sie nach Bedarf
                } else {
                    throw new SQLException("Erstellen der Buchung fehlgeschlagen, keine ID erhalten.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Erstellen der Buchung", e);
        }
    }

    /**
     * Aktualisiert eine bestehende Buchung in der Datenbank.
     *
     * @param bookingId    Die ID der zu aktualisierenden Buchung.
     * @param bookingStart Der neue Beginn der Buchung.
     * @param bookingEnd   Das neue Ende der Buchung.
     * @param purpose      Der neue Zweck der Buchung.
     * @param status       Der neue Status der Buchung.
     */
    @Override
    public void updateBooking(int bookingId, LocalDateTime bookingStart, LocalDateTime bookingEnd, String purpose, String status) {
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

    /**
     * Löscht eine Buchung aus der Datenbank.
     *
     * @param bookingId Die ID der zu löschenden Buchung.
     */
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

    // Hilfsmethode zum Mappen eines ResultSet auf ein Booking-Objekt
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
