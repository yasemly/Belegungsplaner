package com.room.booking.dao;

import com.mysql.cj.xdevapi.PreparableStatement;
import com.room.booking.util.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;

public class BookingDaoImpl implements BookingDao {

    private static final String INSERT_BOOKING_SQL =
            "INSERT INTO bookings (user_id, room_id, booking_start, booking_end, purpose, status) VALUES (?, ?, ?, ?, ?, ?)";


    private static final String UPDATE_BOOKING_SQL = "UPDATE bookings SET booking_start = ? WHERE booking_id = ?";

    @Override
    public void createBooking(int userId, int roomId, LocalDateTime bookingStart, LocalDateTime bookingEnd, String purpose, String status) {

        final Connection connection = DBConnection.getConnection();
        status = "BOOKED";

        try {
            final PreparedStatement ps = connection.prepareStatement(INSERT_BOOKING_SQL);
            ps.setInt(1, userId);
            ps.setInt(2, roomId);
            ps.setTimestamp(3, Timestamp.valueOf(bookingStart));
            ps.setTimestamp(4, Timestamp.valueOf(bookingEnd));
            ps.setString(5, purpose);
            ps.setString(6, status);
            ps.executeUpdate(); //executeUpdate() is used for insert, update or delete statements
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void changeBooking(LocalDateTime startDate, int bookingId) {

        final Connection connection = DBConnection.getConnection();

        try {
            final PreparedStatement ps = connection.prepareStatement(UPDATE_BOOKING_SQL);
            ps.setTimestamp(1, Timestamp.valueOf(startDate));
            ps.setInt(2, bookingId);
            ps.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

}



