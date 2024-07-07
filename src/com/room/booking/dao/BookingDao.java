package com.room.booking.dao;

import com.room.booking.model.Booking;

import java.time.LocalDateTime;

public interface BookingDao {


    void createBooking(int userId, int roomId, LocalDateTime bookingStart, LocalDateTime bookingEnd, String purpose, String status);

    void changeBooking(LocalDateTime startDate, int bookingId);





}
