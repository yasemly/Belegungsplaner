package com.room.booking.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.text.DateFormatter;

public class DateLabelFormatter extends DateFormatter {
    private static final long serialVersionUID = 1L;
    private static final String DATE_PATTERN = "yyyy-MM-dd";

    public DateLabelFormatter() {
        super(new SimpleDateFormat(DATE_PATTERN));
    }

    @Override
    public Object stringToValue(String text) throws ParseException {
        return new SimpleDateFormat(DATE_PATTERN).parse(text);
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        return new SimpleDateFormat(DATE_PATTERN).format((Date) value);
    }
}
