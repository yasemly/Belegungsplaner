package com.room.booking.dao;

import com.room.booking.model.Booking;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BookingDao {
    List<Booking> getBookingsForRoomOnDate(int roomId, LocalDate date);
    List<Booking> getBookingsByUserId(int userId);
    List<Booking> getBookingsByUsername(String username);
    List<Booking> getAllBookings();  // Neue Methode: Alle Buchungen
    void createBooking(int userId, int roomId, LocalDateTime bookingStart, LocalDateTime bookingEnd,
                       String purpose, String status);
    void updateBooking(int bookingId, LocalDateTime bookingStart, LocalDateTime bookingEnd,
                       String purpose, String status);
    void deleteBooking(int bookingId);
    Booking getBookingById(int bookingId);
}
