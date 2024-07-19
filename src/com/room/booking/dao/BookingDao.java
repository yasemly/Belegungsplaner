package com.room.booking.dao;

import com.room.booking.model.Booking;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingDao {

    void bookRoom(int userId, int roomId, LocalDateTime startTime, LocalDateTime endTime);

    List<Booking> getBookingsByUserId(int userId);

    void createBooking(int userId, int roomId, LocalDateTime bookingStart,
                       LocalDateTime bookingEnd, String purpose, String status);

    void updateBooking(int bookingId, LocalDateTime bookingStart,
                       LocalDateTime bookingEnd, String purpose, String status);

    void deleteBooking(int bookingId);

}
