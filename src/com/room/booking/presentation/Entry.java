package com.room.booking.presentation;

import javax.swing.*;

/**
 * Entry point für die Anwendung.
 */
public class Entry {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }
}
