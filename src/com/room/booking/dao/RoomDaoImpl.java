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

    private static final String INSERT_ROOM_SQL =
            "INSERT INTO rooms (room_name, capacity, room_features, location, rating, floor) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String DELETE_ROOM_SQL =
            "DELETE FROM rooms WHERE room_id = ?";
    private static final String SELECT_ALL_ROOMS_SQL =
            "SELECT * FROM rooms";
    private static final String SELECT_ROOMS_BY_CRITERIA_SQL =
            "SELECT * FROM rooms WHERE 1=1";
    private static final String INSERT_BOOKING_SQL =
            "INSERT INTO bookings (user_id, room_id, booking_start, booking_end) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ROOM_BY_NAME_SQL =
            "SELECT * FROM rooms WHERE room_name = ?";
    private static final String SELECT_ROOM_ID_BY_NAME_SQL =
            "SELECT room_id FROM rooms WHERE room_name = ?";

    @Override
    public Room getRoomByName(String name) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ROOM_BY_NAME_SQL)) {

            preparedStatement.setString(1, name);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToRoom(rs);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching room by name: " + name, e);
        }
        return null;
    }

    @Override
    public void bookRoom(int userId, int roomId, LocalDateTime startTime, LocalDateTime endTime) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_BOOKING_SQL)) {

            statement.setInt(1, userId);
            statement.setInt(2, roomId);
            statement.setTimestamp(3, Timestamp.valueOf(startTime));
            statement.setTimestamp(4, Timestamp.valueOf(endTime));

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error booking room: " + roomId + " for user: " + userId, e);
        }
    }

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

        // Check for availability (bookings table). This is a naive approach:
        if (availableFrom != null && availableTo != null) {
            query.append(" AND NOT EXISTS (")
                    .append(" SELECT 1 FROM bookings b ")
                    .append(" WHERE b.room_id = rooms.room_id ")
                    .append(" AND ( (b.booking_start <= ? AND b.booking_end >= ?) ")
                    .append(" OR (b.booking_start <= ? AND b.booking_end >= ?))")
                    .append(")");
            // Overlap check
            params.add(Timestamp.valueOf(availableTo));
            params.add(Timestamp.valueOf(availableFrom));
            params.add(Timestamp.valueOf(availableFrom));
            params.add(Timestamp.valueOf(availableTo));
        }

        // Check each requested feature
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
                    rooms.add(mapResultSetToRoom(rs));
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error searching rooms with given criteria", e);
        }

        return rooms;
    }

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
            // split on comma
            String[] feats = roomFeatures.split(",");
            for (String f : feats) {
                roomFeatureList.add(f.trim());
            }
        }

        return new Room(roomId, roomName, capacity, roomFeatureList, location, rating, floor);
    }
}
