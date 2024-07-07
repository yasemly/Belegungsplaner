package com.room.booking.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
/**
 * Utility class for Date format manipulation
 */
public class DateUtil {

    public static LocalDateTime convertDateToLocalDateTime(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
