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
 * Implementierung des RoomDao-Interfaces für den Datenbankzugriff auf Räume
 */
public class RoomDaoImpl implements RoomDao {

    private static final Logger logger = Logger.getLogger(RoomDaoImpl.class.getName());

    // SQL-Abfragen
    private static final String INSERT_ROOM_SQL = "INSERT INTO rooms (room_name, capacity, room_features, location, rating, floor) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String DELETE_ROOM_SQL = "DELETE FROM rooms WHERE room_id = ?";
    private static final String SELECT_ALL_ROOMS_SQL = "SELECT * FROM rooms";
    private static final String SELECT_ROOMS_BY_CRITERIA_SQL = "SELECT * FROM rooms WHERE 1=1"; // Basis für dynamische Suche
    private static final String INSERT_BOOKING_SQL = "INSERT INTO bookings (user_id, room_id, booking_start, booking_end) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ROOM_BY_NAME_SQL = "SELECT * FROM rooms WHERE room_name = ?";
    private static final String SELECT_ROOM_ID_BY_NAME_SQL = "SELECT room_id FROM rooms WHERE room_name = ?";

    /**
     * Holt einen Raum anhand seines Namens.
     *
     * @param name Der Name des Raums
     * @return Der Raum, falls gefunden, sonst null
     */
    @Override
    public Room getRoomByName(String name) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ROOM_BY_NAME_SQL)) {

            preparedStatement.setString(1, name);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToRoom(rs); // Hilfsmethode zur Abbildung des ResultSets
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching room by name: " + name, e);
        }
        return null;
    }

    /**
     * Bucht einen Raum für einen bestimmten Benutzer.
     *
     * @param userId    Die ID des Benutzers, der den Raum bucht
     * @param roomId    Die ID des zu buchenden Raums
     * @param startTime Der Beginn der Buchung
     * @param endTime   Das Ende der Buchung
     */
    @Override
    public void bookRoom(int userId, int roomId, LocalDateTime startTime, LocalDateTime endTime) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_BOOKING_SQL)) {

            statement.setInt(1, userId);
            statement.setInt(2, roomId);
            statement.setObject(3, Timestamp.valueOf(startTime));
            statement.setObject(4, Timestamp.valueOf(endTime));

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error booking room: " + roomId + " for user: " + userId, e);
        }
    }

    /**
     * Fügt einen neuen Raum zur Datenbank hinzu
     *
     * @param room Der hinzuzufügende Raum
     */
    @Override
    public void addRoom(Room room) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ROOM_SQL)) {

            preparedStatement.setString(1, room.getRoomName());
            preparedStatement.setInt(2, room.getCapacity());
            preparedStatement.setString(3, String.join(",", room.getFeatures()));
            preparedStatement.setString(4, room.getLocation());
            preparedStatement.setDouble(5, room.getRating());
            preparedStatement.setInt(6, room.getFloor());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error adding room: " + room.getRoomName(), e);
        }
    }

    /**
     * Löscht einen Raum aus der Datenbank
     *
     * @param roomId Die ID des zu löschenden Raums
     */
    @Override
    public void deleteRoom(int roomId) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ROOM_SQL)) {

            preparedStatement.setInt(1, roomId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error deleting room with ID: " + roomId, e);
        }
    }

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
    @Override
    public List<Room> searchRooms(int capacity, String location, List<String> features,
                                  double rating, int floor, LocalDateTime availableFrom, LocalDateTime availableTo) {

        List<Room> rooms = new ArrayList<>();
        StringBuilder query = new StringBuilder(SELECT_ROOMS_BY_CRITERIA_SQL);
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

        if (floor > 0) {
            query.append(" AND floor = ?");
            params.add(floor);
        }

        // Berücksichtigen Sie Buchungen bei der Verfügbarkeitsprüfung
        if (availableFrom != null && availableTo != null) {
            query.append(" AND NOT EXISTS (");
            query.append(" SELECT 1 FROM bookings b ");
            query.append(" WHERE b.room_id = rooms.room_id ");
            query.append(" AND ((b.booking_start <= ? AND b.booking_end >= ?)"); // Überlappungsprüfung
            query.append(" OR (b.booking_start <= ? AND b.booking_end >= ?))");
            query.append(")");
            params.add(Timestamp.valueOf(availableTo));
            params.add(Timestamp.valueOf(availableFrom));
            params.add(Timestamp.valueOf(availableFrom));
            params.add(Timestamp.valueOf(availableTo));
        }

        for (String feature : features) {
            query.append(" AND room_features LIKE ?");
            params.add("%" + feature + "%");
        }

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query.toString())) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    rooms.add(mapResultSetToRoom(rs)); // Hilfsmethode zur Abbildung des ResultSets
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error searching rooms with given criteria", e);
        }

        return rooms;
    }

    /**
     * Holt alle Räume aus der Datenbank
     *
     * @return Eine Liste aller Räume
     */
    @Override
    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_ALL_ROOMS_SQL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                rooms.add(mapResultSetToRoom(rs)); // Hilfsmethode zur Abbildung des ResultSets
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching all rooms", e);
        }
        return rooms;
    }

    /**
     * Holt die ID eines Raums anhand seines Namens
     *
     * @param roomName Der Name des Raums
     * @return Die ID des Raums, falls gefunden, sonst -1
     */
    @Override
    public int getRoomId(String roomName) {
        int roomId = -1;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(SELECT_ROOM_ID_BY_NAME_SQL)) {
            stmt.setString(1, roomName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    roomId = rs.getInt("room_id");
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching room ID for room name: " + roomName, e);
        }
        return roomId;
    }

    // Hilfsmethode zum Mappen eines ResultSet auf ein Room-Objekt
    private Room mapResultSetToRoom(ResultSet rs) throws SQLException {
        int roomId = rs.getInt("room_id");
        String roomName = rs.getString("room_name");
        int capacity = rs.getInt("capacity");
        String roomFeatures = rs.getString("room_features");
        String location = rs.getString("location");
        double rating = rs.getDouble("rating");
        int floor = rs.getInt("floor");

        List<String> roomFeatureList = new ArrayList<>();
        if (roomFeatures != null && !roomFeatures.isEmpty()) {
            roomFeatureList = new ArrayList<>(List.of(roomFeatures.split(",")));
        }

        return new Room(roomId, roomName, capacity, roomFeatureList, location, rating, floor);
    }
}
