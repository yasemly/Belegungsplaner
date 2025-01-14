package com.room.booking.model;

import java.util.List;

/**
 * Ein Datentransferobjekt (DTO), das grundlegende Rauminformationen repräsentiert.
 * Dieses DTO überträgt nun auch die Bewertung (rating) und das Stockwerk (floor).
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
        String location,
        /** Die Bewertung des Raums */
        double rating,
        /** Das Stockwerk, auf dem sich der Raum befindet */
        int floor
) {
}
