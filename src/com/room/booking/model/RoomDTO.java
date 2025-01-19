package com.room.booking.model;

import java.util.List;

/**
 * Ein Datentransferobjekt (DTO), das grundlegende Rauminformationen repräsentiert.
 */
public record RoomDTO(
        int roomId,
        String roomName,
        int capacity,
        List<String> features,
        String location,
        double rating,
        int floor
) {
}
