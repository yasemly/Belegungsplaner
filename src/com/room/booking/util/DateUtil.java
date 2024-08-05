package com.room.booking.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Hilfsklasse für die Handhabung von Datumsformaten.
 */
public class DateUtil {

    /**
     * Konvertiert ein Date-Objekt in ein LocalDateTime-Objekt.
     *
     * @param date Das zu konvertierende Date-Objekt
     * @return Das entsprechende LocalDateTime-Objekt
     */
    public static LocalDateTime convertDateToLocalDateTime(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
