package com.room.booking.model;

public class Room {


    private int room_id;
    private String room_name;

    private int capacity;

    private String location;

    private String features;

    public Room(int room_id, String room_name, int capacity, String location, String features) {
        this.room_id = room_id;
        this.room_name = room_name;
        this.capacity = capacity;
        this.location = location;
        this.features = features;
    }

    public Room() {

    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }


}
