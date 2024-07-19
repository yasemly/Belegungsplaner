package com.room.booking.dao;

import com.room.booking.model.Room;
import com.room.booking.util.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RoomDaoImpl implements RoomDao {

    private static final String INSERT_ROOM_SQL = "INSERT INTO rooms (room_name, capacity, room_features, location, rating, floor) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String DELETE_ROOM_SQL = "DELETE FROM rooms WHERE room_id = ?";
    private static final String SELECT_ALL_ROOMS_SQL = "SELECT * FROM rooms";
    private static final String SELECT_ROOMS_BY_CRITERIA_SQL = "SELECT * FROM rooms WHERE 1=1";
    private static final String INSERT_BOOKING_SQL = "INSERT INTO bookings (user_id, room_id, start_time, end_time) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ROOM_BY_NAME_SQL = "SELECT * FROM rooms WHERE room_name = ?";

    @Override
    public Room getRoomByName(String name) {
        Room room = null;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ROOM_BY_NAME_SQL)) {

            preparedStatement.setString(1, name);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
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

                    room = new Room(roomId, roomName, capacity, roomFeatureList, location, rating, floor);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle better in a real application
        }

        return room;
    }


    @Override
    public void bookRoom(int userId, int roomId, LocalDateTime startTime, LocalDateTime endTime) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_BOOKING_SQL)) {

            statement.setInt(1, userId);
            statement.setInt(2, roomId);
            statement.setObject(3, startTime);
            statement.setObject(4, endTime);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle better in a real application
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
            e.printStackTrace();
        }
    }

    @Override
    public void deleteRoom(int roomId) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ROOM_SQL)) {

            preparedStatement.setInt(1, roomId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Room> searchRooms(int capacity, String location, List<String> features, double rating, int floor, LocalDateTime availableFrom, LocalDateTime availableTo) {
        List<Room> rooms = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM rooms WHERE 1=1");

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

        if (availableFrom != null) {
            query.append(" AND available_from >= ?");
            params.add(Timestamp.valueOf(availableFrom));
        }

        if (availableTo != null) {
            query.append(" AND available_to <= ?");
            params.add(Timestamp.valueOf(availableTo));
        }

        for (String feature : features) {
            query.append(" AND features LIKE ?");
            params.add("%" + feature + "%");
        }

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query.toString())) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int roomId = rs.getInt("room_id");
                String roomName = rs.getString("room_name");
                int capacityValue = rs.getInt("capacity");
                String roomFeatures = rs.getString("room_features");
                String locationValue = rs.getString("location");
                double roomRating = rs.getDouble("rating");
                int roomFloor = rs.getInt("floor");

                List<String> roomFeatureList = new ArrayList<>();
                if (roomFeatures != null && !roomFeatures.isEmpty()) {
                    roomFeatureList = new ArrayList<>(List.of(roomFeatures.split(",")));
                }

                Room room = new Room(roomId, roomName, capacityValue, roomFeatureList, locationValue, roomRating, roomFloor);
                rooms.add(room);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle better in a real application
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

                Room room = new Room(roomId, roomName, capacity, roomFeatureList, location, rating, floor);

                rooms.add(room);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rooms;
    }

    @Override
    public int getRoomId(String roomName) {
        int roomId = -1;
        String query = "SELECT room_id FROM rooms WHERE room_name = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, roomName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    roomId = rs.getInt("room_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roomId;
    }
}
