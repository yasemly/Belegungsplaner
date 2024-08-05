package com.room.booking.dao;

import com.room.booking.model.Booking;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface für den Datenzugriff auf Buchungen
 */
public interface BookingDao {

    /**
     * Holt alle Buchungen eines bestimmten Benutzers anhand seiner Benutzer-ID.
     *
     * @param userId Die ID des Benutzers.
     * @return Eine Liste aller Buchungen des Benutzers.
     */
    List<Booking> getBookingsByUserId(int userId);

    /**
     * Holt alle Buchungen eines bestimmten Benutzers anhand seines Benutzernamens.
     *
     * @param username Der Benutzername des Benutzers.
     * @return Eine Liste aller Buchungen des Benutzers.
     */
    List<Booking> getBookingsByUsername(String username);

    /**
     * Erstellt eine neue Buchung in der Datenbank.
     *
     * @param userId       Die ID des Benutzers, der die Buchung erstellt.
     * @param roomId       Die ID des gebuchten Raums.
     * @param bookingStart Der Beginn der Buchung.
     * @param bookingEnd   Das Ende der Buchung.
     * @param purpose      Der Zweck der Buchung.
     * @param status       Der Status der Buchung (z.B. "bestätigt", "storniert").
     */
    void createBooking(int userId, int roomId, LocalDateTime bookingStart, LocalDateTime bookingEnd, String purpose, String status);

    /**
     * Aktualisiert eine bestehende Buchung in der Datenbank.
     *
     * @param bookingId    Die ID der zu aktualisierenden Buchung.
     * @param bookingStart Der neue Beginn der Buchung.
     * @param bookingEnd   Das neue Ende der Buchung.
     * @param purpose      Der neue Zweck der Buchung.
     * @param status       Der neue Status der Buchung.
     */
    void updateBooking(int bookingId, LocalDateTime bookingStart, LocalDateTime bookingEnd, String purpose, String status);

    /**
     * Löscht eine Buchung aus der Datenbank.
     *
     * @param bookingId Die ID der zu löschenden Buchung.
     */
    void deleteBooking(int bookingId);
}
