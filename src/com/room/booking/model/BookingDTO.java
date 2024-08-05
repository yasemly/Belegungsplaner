package com.room.booking.model;

import java.time.LocalDateTime;

/**
 * Ein Datentransferobjekt (DTO), das Buchungsinformationen repräsentiert.
 */
public record BookingDTO(
        int bookingId,
        int roomId,
        int userId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        String purpose,
        String status
) {
}
