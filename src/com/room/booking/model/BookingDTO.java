package com.room.booking.model;

import java.time.LocalDateTime;

public record BookingDTO(int bookingId,
                         int roomId,
                         int userId,
                         LocalDateTime startTime,
                         LocalDateTime endTime,
                         String purpose,
                         String status) {

}
