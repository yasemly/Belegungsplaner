package com.room.booking.presentation;

import javax.swing.*;
import java.awt.*;

/**
 * Simple stub for the "Benutzer hinzufügen" flow.
 * You can expand or replicate logic from RegistrationFrame, etc.
 */
public class AddUserFrame extends JFrame {

    public AddUserFrame() {
        setTitle("Benutzer hinzufügen");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Just a placeholder label
        add(new JLabel("AddUserFrame - implement me!", SwingConstants.CENTER), BorderLayout.CENTER);
    }
}
