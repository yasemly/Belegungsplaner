package com.room.booking.service;

import com.room.booking.dao.BookingDao; // Use the interface instead of the implementation
import com.room.booking.dao.BookingDaoImpl;
import com.room.booking.util.DateUtil;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Service-Klasse zur Verwaltung von Buchungen.
 */
public class BookingServiceImpl {

    // Data Access Object für Buchungen
    private BookingDao bookingDao;

    /**
     * Konstruktor für den BookingServiceImpl.
     * Initialisiert das BookingDao.
     */
    public BookingServiceImpl() {
        this.bookingDao = new BookingDaoImpl();
    }

    /**
     * Erstellt eine neue Buchung.
     *
     * @param roomId       Die ID des gebuchten Raums
     * @param userId       Die ID des Benutzers, der die Buchung erstellt
     * @param selectedRoom Der Name des ausgewählten Raums (scheint derzeit nicht verwendet zu werden)
     * @param bookerName   Der Name des Benutzers, der die Buchung erstellt (scheint derzeit nicht verwendet zu werden)
     * @param startDate    Der Startzeitpunkt der Buchung
     * @param endDate      Der Endzeitpunkt der Buchung
     * @param purpose      Der Zweck der Buchung
     */
    public void createBooking(int roomId, int userId, String selectedRoom, String bookerName, Date startDate, Date endDate, String purpose) {
        String status = "IN_PROGRESS"; // Standardstatus für neue Buchungen

        LocalDateTime localDateTimeStart = DateUtil.convertDateToLocalDateTime(startDate);
        LocalDateTime localDateTimeEnd = DateUtil.convertDateToLocalDateTime(endDate);

        // Verwenden Sie die createBooking-Methode des BookingDao
        bookingDao.createBooking(userId, roomId, localDateTimeStart, localDateTimeEnd, purpose, status);
    }
}
