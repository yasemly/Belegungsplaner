package com.room.booking.presentation;

import com.room.booking.model.IBookable;
import javax.swing.*;
import java.awt.*;

public class BookingManagementDialog extends JDialog {

    private int bookingId;
    private IBookable bookable;  // Das kann entweder ein User oder ein Employer sein

    public BookingManagementDialog(Frame owner, int bookingId, IBookable bookable) {
        super(owner, "Buchung bearbeiten / löschen", true);
        this.bookingId = bookingId;
        this.bookable = bookable;
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel infoLabel = new JLabel("Buchung " + bookingId + " bearbeiten für: " + bookable.getFullName());
        infoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(infoLabel, BorderLayout.NORTH);
        // Weitere Komponenten und Bearbeitungslogik hier einfügen...
        add(panel);
        pack();
        setLocationRelativeTo(getOwner());
    }
}
