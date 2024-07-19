package com.room.booking.dao;

import com.room.booking.model.Booking;
import com.room.booking.util.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookingDaoImpl implements BookingDao {

    private static final String SELECT_BOOKINGS_BY_USER_ID_SQL = "SELECT * FROM bookings WHERE user_id = ?";

    private static final String INSERT_BOOKING_SQL =
            "INSERT INTO bookings (user_id, room_id, booking_start, booking_end, purpose, status) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_BOOKING_SQL =
            "UPDATE bookings SET booking_start = ?, booking_end = ?, purpose = ?, status = ? WHERE booking_id = ?";

    private static final String DELETE_BOOKING_SQL =
            "DELETE FROM bookings WHERE booking_id = ?";

    private static final String sql = "INSERT INTO bookings (user_id, room_id, start_time, end_time) VALUES (?, ?, ?, ?)";


    @Override
    public void bookRoom(int userId, int roomId, LocalDateTime startTime, LocalDateTime endTime) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);
            statement.setInt(2, roomId);
            statement.setObject(3, startTime);
            statement.setObject(4, endTime);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<Booking> getBookingsByUserId(int userId) {
        List<Booking> bookings = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_BOOKINGS_BY_USER_ID_SQL)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Booking booking = new Booking(
                            rs.getInt("booking_id"),
                            rs.getInt("user_id"),
                            rs.getInt("room_id"),
                            rs.getTimestamp("booking_start").toLocalDateTime(),
                            rs.getTimestamp("booking_end").toLocalDateTime(),
                            rs.getString("purpose"),
                            rs.getString("status")
                    );
                    bookings.add(booking);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving bookings by user ID", e);
        }

        return bookings;
    }

    @Override
    public void createBooking(int userId, int roomId, LocalDateTime bookingStart,
                              LocalDateTime bookingEnd, String purpose, String status) {
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
                    // Optionally set the generated booking ID on the booking object or use it as needed
                } else {
                    throw new SQLException("Creating booking failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error creating booking", e);
        }
    }

    @Override
    public void updateBooking(int bookingId, LocalDateTime bookingStart,
                              LocalDateTime bookingEnd, String purpose, String status) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_BOOKING_SQL)) {

            ps.setTimestamp(1, Timestamp.valueOf(bookingStart));
            ps.setTimestamp(2, Timestamp.valueOf(bookingEnd));
            ps.setString(3, purpose);
            ps.setString(4, status);
            ps.setInt(5, bookingId);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error updating booking", e);
        }
    }

    @Override
    public void deleteBooking(int bookingId) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_BOOKING_SQL)) {

            ps.setInt(1, bookingId);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting booking", e);
        }
    }

}
