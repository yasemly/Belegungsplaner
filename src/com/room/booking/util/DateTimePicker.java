package com.room.booking.util;

import com.room.booking.util.DateLabelFormatter;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.jdatepicker.impl.JDatePanelImpl;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.Calendar;
import java.util.Properties;

public class DateTimePicker extends JPanel {
    private JDatePickerImpl datePicker;
    private JSpinner timeSpinner;

    public DateTimePicker() {
        setLayout(new BorderLayout());

        // Date Picker
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        add(datePicker, BorderLayout.NORTH);

        // Time Spinner
        SpinnerModel timeModel = new SpinnerDateModel();
        timeSpinner = new JSpinner(timeModel);
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
        timeSpinner.setEditor(timeEditor);
        add(timeSpinner, BorderLayout.SOUTH);
    }

    public Date getDate() {
        Calendar cal = Calendar.getInstance();
        Date date = (Date) datePicker.getModel().getValue();
        cal.setTime(date);
        Date time = (Date) timeSpinner.getValue();
        cal.set(Calendar.HOUR_OF_DAY, time.getHours());
        cal.set(Calendar.MINUTE, time.getMinutes());
        return cal.getTime();
    }
}
