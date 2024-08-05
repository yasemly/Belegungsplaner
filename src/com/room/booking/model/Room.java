package com.room.booking.model;

import java.util.List;

/**
 * Repräsentiert einen Raum im Raumbuchungssystem.
 */
public class Room {

    private int roomId;
    private String roomName;
    private int capacity;
    private List<String> features; // Liste zur Speicherung der Ausstattungsmerkmale
    private String location;
    private double rating;
    private int floor;

    /**
     * Konstruktor für die Room-Klasse.
     *
     * @param roomId     Die eindeutige ID des Raums.
     * @param roomName   Der Name des Raums.
     * @param capacity   Die maximale Kapazität des Raums.
     * @param features   Eine Liste der Ausstattungsmerkmale des Raums.
     * @param location   Der Standort des Raums.
     * @param rating     Die Bewertung des Raums.
     * @param floor      Das Stockwerk, auf dem sich der Raum befindet.
     */
    public Room(int roomId, String roomName, int capacity, List<String> features, String location, double rating, int floor) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.capacity = capacity;
        this.features = features;
        this.location = location;
        this.rating = rating;
        this.floor = floor;
    }

    // Getter und Setter
    /**
     * Gibt die ID des Raums zurück.
     *
     * @return Die Raum-ID.
     */
    public int getRoomId() {
        return roomId;
    }

    /**
     * Setzt die ID des Raums.
     *
     * @param roomId Die neue Raum-ID.
     */
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    /**
     * Gibt den Namen des Raums zurück.
     *
     * @return Der Raumname.
     */
    public String getRoomName() {
        return roomName;
    }

    /**
     * Setzt den Namen des Raums.
     *
     * @param roomName Der neue Raumname.
     */
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    /**
     * Gibt die maximale Kapazität des Raums zurück.
     *
     * @return Die Kapazität.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Setzt die maximale Kapazität des Raums.
     *
     * @param capacity Die neue Kapazität.
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Gibt eine Liste der Ausstattungsmerkmale des Raums zurück.
     *
     * @return Die Liste der Ausstattungsmerkmale.
     */
    public List<String> getFeatures() {
        return features;
    }

    /**
     * Setzt die Liste der Ausstattungsmerkmale des Raums.
     *
     * @param features Die neue Liste der Ausstattungsmerkmale.
     */
    public void setFeatures(List<String> features) {
        this.features = features;
    }

    /**
     * Gibt den Standort des Raums zurück.
     *
     * @return Der Standort.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Setzt den Standort des Raums.
     *
     * @param location Der neue Standort.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gibt die Bewertung des Raums zurück.
     *
     * @return Die Bewertung.
     */
    public double getRating() {
        return rating;
    }

    /**
     * Setzt die Bewertung des Raums.
     *
     * @param rating Die neue Bewertung.
     */
    public void setRating(double rating) {
        this.rating = rating;
    }

    /**
     * Gibt das Stockwerk zurück, auf dem sich der Raum befindet.
     *
     * @return Das Stockwerk.
     */
    public int getFloor() {
        return floor;
    }

    /**
     * Setzt das Stockwerk, auf dem sich der Raum befindet.
     *
     * @param floor Das neue Stockwerk.
     */
    public void setFloor(int floor) {
        this.floor = floor;
    }
}
