package com.room.booking.model;

import java.util.List; // Import for List

/**
 * Ein Datentransferobjekt (DTO), das grundlegende Rauminformationen repräsentiert.
 */
public record RoomDTO(
        /** Die eindeutige ID des Raums */
        int roomId,
        /** Der Name des Raums */
        String roomName,
        /** Die maximale Kapazität des Raums */
        int capacity,
        /** Eine Liste der Ausstattungsmerkmale des Raums */
        List<String> features,
        /** Der Standort des Raums */
        String location
) {
}
