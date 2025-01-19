package com.room.booking.dao;

import com.room.booking.model.Booking;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface für den Datenzugriff auf Buchungen
 */
public interface BookingDao {
    // In BookingDao.java
    List<Booking> getBookingsForRoomOnDate(int roomId, LocalDate date);


    List<Booking> getBookingsByUserId(int userId);

    List<Booking> getBookingsByUsername(String username);

    void createBooking(int userId, int roomId, LocalDateTime bookingStart, LocalDateTime bookingEnd,
                       String purpose, String status);

    void updateBooking(int bookingId, LocalDateTime bookingStart, LocalDateTime bookingEnd,
                       String purpose, String status);

    void deleteBooking(int bookingId);
}
