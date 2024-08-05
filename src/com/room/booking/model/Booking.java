package com.room.booking.model;

import java.time.LocalDateTime;

/**
 * Repräsentiert eine Buchung im Raumbuchungssystem.
 */
public class Booking {

    private int bookingId;
    private int userId;
    private int roomId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String purpose;
    private String status;

    /**
     * Konstruktor für die Booking-Klasse.
     *
     * @param bookingId Die eindeutige ID der Buchung.
     * @param userId    Die ID des Benutzers, der die Buchung erstellt hat.
     * @param roomId    Die ID des gebuchten Raums.
     * @param startTime Der Beginn der Buchung.
     * @param endTime   Das Ende der Buchung.
     * @param purpose   Der Zweck der Buchung.
     * @param status    Der Status der Buchung (z.B. "bestätigt", "storniert").
     */
    public Booking(int bookingId, int userId, int roomId, LocalDateTime startTime, LocalDateTime endTime, String purpose, String status) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.roomId = roomId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.purpose = purpose;
        this.status = status;
    }

    // Getter und Setter

    /**
     * Gibt die ID der Buchung zurück.
     *
     * @return Die Buchungs-ID.
     */
    public int getBookingId() {
        return bookingId;
    }

    /**
     * Setzt die ID der Buchung.
     *
     * @param bookingId Die neue Buchungs-ID.
     */
    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    /**
     * Gibt die ID des Benutzers zurück, der die Buchung erstellt hat.
     *
     * @return Die Benutzer-ID.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Setzt die ID des Benutzers, der die Buchung erstellt hat.
     *
     * @param userId Die neue Benutzer-ID.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gibt die ID des gebuchten Raums zurück.
     *
     * @return Die Raum-ID.
     */
    public int getRoomId() {
        return roomId;
    }

    /**
     * Setzt die ID des gebuchten Raums.
     *
     * @param roomId Die neue Raum-ID.
     */
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    /**
     * Gibt den Beginn der Buchung zurück.
     *
     * @return Der Buchungsbeginn.
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * Setzt den Beginn der Buchung.
     *
     * @param startTime Der neue Buchungsbeginn.
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Gibt das Ende der Buchung zurück.
     *
     * @return Das Buchungsende.
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * Setzt das Ende der Buchung.
     *
     * @param endTime Das neue Buchungsende.
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    /**
     * Gibt den Zweck der Buchung zurück.
     *
     * @return Der Zweck der Buchung.
     */
    public String getPurpose() {
        return purpose;
    }

    /**
     * Setzt den Zweck der Buchung.
     *
     * @param purpose Der neue Zweck der Buchung.
     */
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    /**
     * Gibt den Status der Buchung zurück.
     *
     * @return Der Buchungsstatus.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Setzt den Status der Buchung.
     *
     * @param status Der neue Buchungsstatus.
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
