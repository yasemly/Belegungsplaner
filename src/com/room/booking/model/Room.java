package com.room.booking.model;

import java.util.List;

/**
 * Repräsentiert einen Raum im Raumbuchungssystem.
 */
public class Room {

    private int roomId;
    private String roomName;
    private int capacity;
    private List<String> features;
    private String location;
    private double rating;
    private int floor;

    public Room(int roomId, String roomName, int capacity,
                List<String> features, String location,
                double rating, int floor) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.capacity = capacity;
        this.features = features;
        this.location = location;
        this.rating = rating;
        this.floor = floor;
    }

    // Getters and Setters
    public int getRoomId() {
        return roomId;
    }
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getCapacity() {
        return capacity;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<String> getFeatures() {
        return features;
    }
    public void setFeatures(List<String> features) {
        this.features = features;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public double getRating() {
        return rating;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getFloor() {
        return floor;
    }
    public void setFloor(int floor) {
        this.floor = floor;
    }
}
