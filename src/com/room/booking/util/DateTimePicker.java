package com.room.booking.util;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

/**
 * Ein benutzerdefiniertes Swing-Panel, das einen JDatePicker zur Datumsauswahl und einen JSpinner zur Zeitauswahl kombiniert.
 */
public class DateTimePicker extends JPanel {

    private JDatePickerImpl datePicker;
    private JSpinner timeSpinner;

    /**
     * Konstruktor für den DateTimePicker.
     * Initialisiert das Layout und die Komponenten.
     */
    public DateTimePicker() {
        setLayout(new BorderLayout());

        // Datumsauswahl (DatePicker)
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Heute");
        p.put("text.month", "Monat");
        p.put("text.year", "Jahr");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        add(datePicker, BorderLayout.CENTER);

        // Zeitauswahl (TimeSpinner)
        SpinnerModel timeModel = new SpinnerDateModel();
        timeSpinner = new JSpinner(timeModel);
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
        timeSpinner.setEditor(timeEditor);
        add(timeSpinner, BorderLayout.EAST);
    }

    /**
     * Gibt das ausgewählte Datum und die ausgewählte Uhrzeit als Date-Objekt zurück.
     *
     * @return Das ausgewählte Datum und die Uhrzeit.
     */
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
