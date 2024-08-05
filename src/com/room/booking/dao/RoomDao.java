package com.room.booking.dao;

import com.room.booking.model.Room;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Schnittstelle für den Zugriff auf Raumdaten in der Datenbank
 */
public interface RoomDao {

    /**
     * Bucht einen Raum für einen bestimmten Benutzer
     *
     * @param userId    Die ID des Benutzers, der den Raum bucht
     * @param roomId    Die ID des zu buchenden Raums
     * @param startTime Der Beginn der Buchung
     * @param endTime   Das Ende der Buchung
     */
    void bookRoom(int userId, int roomId, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * Fügt einen neuen Raum zur Datenbank hinzu
     *
     * @param room Der hinzuzufügende Raum
     */
    void addRoom(Room room);

    /**
     * Löscht einen Raum aus der Datenbank
     *
     * @param roomId Die ID des zu löschenden Raums
     */
    void deleteRoom(int roomId);

    /**
     * Sucht nach verfügbaren Räumen basierend auf bestimmten Kriterien
     *
     * @param capacity      Die Mindestkapazität des Raums
     * @param location      Der Standort des Raums
     * @param features      Eine Liste der gewünschten Ausstattungsmerkmale
     * @param rating        Die Mindestbewertung des Raums
     * @param floor         Die Etage, auf der sich der Raum befindet
     * @param availableFrom Der früheste Zeitpunkt, ab dem der Raum verfügbar sein soll
     * @param availableTo   Der späteste Zeitpunkt, bis zu dem der Raum verfügbar sein soll
     * @return Eine Liste der gefundenen Räume
     */
    List<Room> searchRooms(int capacity, String location, List<String> features, double rating, int floor, LocalDateTime availableFrom, LocalDateTime availableTo);

    /**
     * Holt alle Räume aus der Datenbank
     *
     * @return Eine Liste aller Räume
     */
    List<Room> getAllRooms();

    /**
     * Holt die ID eines Raums anhand seines Namens
     *
     * @param roomName Der Name des Raums
     * @return Die ID des Raums, falls gefunden, sonst -1
     */
    int getRoomId(String roomName);

    /**
     * Holt einen Raum anhand seines Namens
     *
     * @param name Der Name des Raums
     * @return Der Raum, falls gefunden, sonst null
     */
    Room getRoomByName(String name);
}
