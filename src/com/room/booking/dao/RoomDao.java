package com.room.booking.dao;

import com.room.booking.model.Room;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface RoomDao {
    void bookRoom(int userId, int roomId, LocalDateTime startTime, LocalDateTime endTime);
    void addRoom(Room room);
    void deleteRoom(int roomId);
    List<Room> searchRooms(int capacity, String location, List<String> features,
                           double rating, int floor, LocalDateTime availableFrom, LocalDateTime availableTo);

    List<Room> getAllRooms();
    int getRoomId(String roomName);
    Room getRoomByName(String name);
}
