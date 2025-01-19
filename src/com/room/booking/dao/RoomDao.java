package com.room.booking.dao;

import com.room.booking.model.Room;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Schnittstelle für den Zugriff auf Raumdaten in der Datenbank.
 */
public interface RoomDao {

    /**
     * Liefert alle Räume.
     * @return Liste aller Räume
     */
    List<Room> getAllRooms();

    /**
     * Liefert einen Raum anhand seiner ID.
     * @param roomId Die ID des Raumes.
     * @return Der Raum, falls vorhanden, sonst null.
     */
    Room getRoomById(int roomId);

    /**
     * Liefert einen Raum anhand seines Namens.
     * @param roomName Der Name des Raumes.
     * @return Der Raum, falls vorhanden, sonst null.
     */
    Room getRoomByName(String roomName);

    /**
     * Fügt einen neuen Raum der Datenbank hinzu.
     * @param room Der einzufügende Raum.
     */
    void addRoom(Room room);

    /**
     * Löscht einen Raum anhand der Raum-ID.
     * @param roomId Die ID des zu löschenden Raumes.
     */
    void deleteRoom(int roomId);

    /**
     * Sucht nach Räumen, die den angegebenen Kriterien entsprechen.
     *
     * @param capacity      Mindestkapazität des Raumes (0 = keine Einschränkung)
     * @param location      Standort ("" = keine Einschränkung)
     * @param features      Liste der gewünschten Features (leere Liste = keine Einschränkung)
     * @param rating        Mindestrating (0.0 = keine Einschränkung)
     * @param floor         Gewünschtes Stockwerk (0 = keine Einschränkung)
     * @param availableFrom Frühester Zeitpunkt der Verfügbarkeit
     * @param availableTo   Spätester Zeitpunkt der Verfügbarkeit
     * @return Liste der Räume, die den Kriterien entsprechen.
     */
    List<Room> searchRooms(int capacity, String location, List<String> features,
                           double rating, int floor, LocalDateTime availableFrom, LocalDateTime availableTo);
}
