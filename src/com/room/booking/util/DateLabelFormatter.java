package com.room.booking.util;

import javax.swing.text.DateFormatter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Formatierer für Datumsangaben in JDatePicker-Komponenten.
 * Verwendet das Format "yyyy-MM-dd".
 */
public class DateLabelFormatter extends DateFormatter {

    private static final long serialVersionUID = 1L;
    private static final String DATE_PATTERN = "yyyy-MM-dd";

    /**
     * Konstruktor für den DateLabelFormatter.
     * Initialisiert den Formatierer mit dem angegebenen Datumsmuster.
     */
    public DateLabelFormatter() {
        super(new SimpleDateFormat(DATE_PATTERN));
    }

    /**
     * Konvertiert einen String in ein Date-Objekt.
     *
     * @param text Der zu konvertierende String.
     * @return Das Date-Objekt, das den String repräsentiert.
     * @throws ParseException Wenn der String nicht im erwarteten Format ist.
     */
    @Override
    public Object stringToValue(String text) throws ParseException {
        return new SimpleDateFormat(DATE_PATTERN).parse(text);
    }

    /**
     * Konvertiert ein Date-Objekt in einen String.
     *
     * @param value Das zu konvertierende Date-Objekt.
     * @return Der String, der das Date-Objekt repräsentiert.
     * @throws ParseException Wenn das Date-Objekt nicht konvertiert werden kann.
     */
    @Override
    public String valueToString(Object value) throws ParseException {
        if (value instanceof Date) {
            return new SimpleDateFormat(DATE_PATTERN).format((Date) value);
        } else {
            throw new ParseException("Das Objekt ist kein gültiges Datum.", 0);
        }
    }
}
