package com.room.booking.dao;

import com.room.booking.model.Booking;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface für den Datenzugriff auf Buchungen.
 */
public interface BookingDao {

    /**
     * Liefert alle Buchungen eines bestimmten Raumes an einem bestimmten Datum.
     */
    List<Booking> getBookingsForRoomOnDate(int roomId, LocalDate date);

    /**
     * Liefert alle Buchungen eines Benutzers anhand der Benutzer-ID.
     */
    List<Booking> getBookingsByUserId(int userId);

    /**
     * Liefert alle Buchungen eines Benutzers anhand des Benutzernamens.
     */
    List<Booking> getBookingsByUsername(String username);

    /**
     * Fügt eine neue Buchung in die Datenbank ein.
     */
    void createBooking(int userId, int roomId, LocalDateTime bookingStart, LocalDateTime bookingEnd,
                       String purpose, String status);

    /**
     * Aktualisiert eine bestehende Buchung.
     */
    void updateBooking(int bookingId, LocalDateTime bookingStart, LocalDateTime bookingEnd,
                       String purpose, String status);

    /**
     * Löscht eine Buchung anhand der Buchungs-ID.
     */
    void deleteBooking(int bookingId);

    /**
     * Liefert eine einzelne Buchung anhand der Buchungs-ID.
     */
    Booking getBookingById(int bookingId);
}
