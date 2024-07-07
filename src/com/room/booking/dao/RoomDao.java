package com.room.booking.dao;

import com.room.booking.model.Room;

import java.util.List;

public interface RoomDao {


    List<Room> getAllRooms();

    void saveRoom(String roomName, int capacity, String location, String features);
}
