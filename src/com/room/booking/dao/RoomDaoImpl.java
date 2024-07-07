package com.room.booking.dao;

import com.room.booking.model.Room;
import com.room.booking.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDaoImpl implements RoomDao{

    private static final String GET_ALL_ROOMS = "SELECT * FROM ROOMS";

    private static final String GET_ROOM_BY_NAME = "SELECT * FROM ROOMS WHERE ROOM_NAME=?";
    private static final String INSERT_ROOM_SQL = "INSERT INTO rooms (room_name, capacity, location, features) VALUES (?,?,?,?)";

    @Override
    public List<Room> getAllRooms() {

        List<Room> rooms = new ArrayList<>();
        final Connection connection = DBConnection.getConnection();
        Statement st = null;
        ResultSet rs = null;

        try {
            st = connection.createStatement();
            rs = st.executeQuery(GET_ALL_ROOMS);
            while (rs.next()) {

                Room room = new Room();
                room.setRoom_id(rs.getInt("room_id"));
                room.setRoom_name(rs.getString("room_name"));
                room.setCapacity(rs.getInt("capacity"));
                room.setFeatures(rs.getString("features"));
                room.setLocation(rs.getString("location"));
                rooms.add(room);
            }
            rs.close();
            st.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return rooms;
    }

    @Override
    public void saveRoom(String roomName, int capacity, String location, String features) {

        final Connection connection = DBConnection.getConnection();

        try {
            final PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ROOM_SQL);
            preparedStatement.setString(1, roomName);
            preparedStatement.setInt(2, capacity);
            preparedStatement.setString(3, location);
            preparedStatement.setString(4, features);
            preparedStatement.executeQuery();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public Room getRoomByName(String selectedRoom) {
        final Connection connection = DBConnection.getConnection();

        try {
            final PreparedStatement preparedStatement = connection.prepareStatement(GET_ROOM_BY_NAME);
            preparedStatement.setString(1, selectedRoom);
            try (ResultSet resultSet = preparedStatement.executeQuery()) { // try with resources
                if (resultSet.next()) {
                    int roomId = resultSet.getInt("room_id");
                    String roomNameFromDB = resultSet.getString("room_name");
                    int capacity = resultSet.getInt("capacity");
                    String features = resultSet.getString("features");
                    String location = resultSet.getString("location");
                    return new Room(roomId, roomNameFromDB, capacity, features, location);
                } else {
                    return null; // or throw an exception if the room is not found
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
