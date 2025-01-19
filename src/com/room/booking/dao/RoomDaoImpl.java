package com.room.booking.dao;

import com.room.booking.model.Room;
import com.room.booking.util.DBConnection;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementierung des RoomDao-Interfaces für den Zugriff auf Raumdaten.
 */
public class RoomDaoImpl implements RoomDao {

    private static final Logger logger = Logger.getLogger(RoomDaoImpl.class.getName());

    // SQL-Abfragen
    private static final String SELECT_ALL_ROOMS_SQL = "SELECT * FROM rooms";
    private static final String SELECT_ROOM_BY_ID_SQL = "SELECT * FROM rooms WHERE room_id = ?";
    private static final String SELECT_ROOM_BY_NAME_SQL = "SELECT * FROM rooms WHERE room_name = ?";
    private static final String INSERT_ROOM_SQL =
            "INSERT INTO rooms (room_name, capacity, location, rating, floor, room_features) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String DELETE_ROOM_SQL = "DELETE FROM rooms WHERE room_id = ?";

    /**
     * Für die Suche nutzen wir eine Basis-Query, der weitere Kriterien dynamisch hinzugefügt werden.
     */
    private static final String BASE_SEARCH_QUERY = "SELECT * FROM rooms WHERE 1=1";

    @Override
    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_ALL_ROOMS_SQL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                rooms.add(mapResultSetToRoom(rs));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching all rooms", e);
        }
        return rooms;
    }

    @Override
    public Room getRoomById(int roomId) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_ROOM_BY_ID_SQL)) {

            stmt.setInt(1, roomId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToRoom(rs);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching room by ID: " + roomId, e);
        }
        return null;
    }

    @Override
    public Room getRoomByName(String roomName) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_ROOM_BY_NAME_SQL)) {

            stmt.setString(1, roomName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToRoom(rs);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching room by name: " + roomName, e);
        }
        return null;
    }

    @Override
    public void addRoom(Room room) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(INSERT_ROOM_SQL)) {

            stmt.setString(1, room.getRoomName());
            stmt.setInt(2, room.getCapacity());
            stmt.setString(3, room.getLocation());
            stmt.setDouble(4, room.getRating());
            stmt.setInt(5, room.getFloor());
            stmt.setString(6, String.join(",", room.getFeatures()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error adding room: " + room.getRoomName(), e);
        }
    }

    @Override
    public void deleteRoom(int roomId) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(DELETE_ROOM_SQL)) {

            stmt.setInt(1, roomId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error deleting room with ID: " + roomId, e);
        }
    }

    /**
     * Implementiert die Suche nach Räumen anhand mehrerer Kriterien.
     */
    @Override
    public List<Room> searchRooms(int capacity, String location, List<String> features,
                                  double rating, int floor, LocalDateTime availableFrom, LocalDateTime availableTo) {
        List<Room> rooms = new ArrayList<>();
        // Baue die Query dynamisch auf
        StringBuilder query = new StringBuilder(BASE_SEARCH_QUERY);
        List<Object> params = new ArrayList<>();

        if (capacity > 0) {
            query.append(" AND capacity >= ?");
            params.add(capacity);
        }
        if (location != null && !location.isEmpty()) {
            query.append(" AND location = ?");
            params.add(location);
        }
        if (rating > 0) {
            query.append(" AND rating >= ?");
            params.add(rating);
        }
        if (floor != 0) { // Falls 0 als "keine Einschränkung" verwendet wird
            query.append(" AND floor = ?");
            params.add(floor);
        }
        // Für Features und Verfügbarkeitszeitraum könntest du komplexere Logik einbauen;
        // hier wird nur das Grundgerüst gezeigt.
        // Beispiel: Für jedes gewünschte Feature eine LIKE-Bedingung
        if (features != null && !features.isEmpty()) {
            for (String feature : features) {
                query.append(" AND room_features ILIKE ?");
                params.add("%" + feature + "%");
            }
        }
        // Optional: Hier könnte man zusätzlich prüfen, ob der Raum im gewünschten Zeitraum verfügbar ist.
        // Diese Prüfung erfordert einen Vergleich mit der "bookings"-Tabelle, was aber den Rahmen
        // dieses Beispiels sprengt. Stattdessen nehmen wir an, dass die Verfügbarkeit extern geprüft wird.
        // Falls notwendig, kann hier auch eine Subquery ergänzt werden.

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query.toString())) {
            // Parameter einfügen
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    rooms.add(mapResultSetToRoom(rs));
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error searching rooms with given criteria", e);
        }
        return rooms;
    }

    /**
     * Hilfsmethode zum Mappen eines ResultSet in ein Room-Objekt.
     */
    private Room mapResultSetToRoom(ResultSet rs) throws SQLException {
        int roomId = rs.getInt("room_id");
        String roomName = rs.getString("room_name");
        int capacity = rs.getInt("capacity");
        String location = rs.getString("location");
        double rating = rs.getDouble("rating");
        int floor = rs.getInt("floor");
        String roomFeatures = rs.getString("room_features");

        List<String> featuresList = new ArrayList<>();
        if (roomFeatures != null && !roomFeatures.isEmpty()) {
            String[] feats = roomFeatures.split(",");
            for (String f : feats) {
                featuresList.add(f.trim());
            }
        }
        return new Room(roomId, roomName, capacity, featuresList, location, rating, floor);
    }
}
