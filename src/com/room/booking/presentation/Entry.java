package com.room.booking.presentation;

import javax.swing.*;

/**
 * Entry point für die Anwendung.
 * Erstellt und zeigt den Login-Frame an.
 */
public class Entry {

    /**
     * Die main-Methode, die beim Start der Anwendung ausgeführt wird.
     *
     * @param args Kommandozeilenargumente (werden derzeit nicht verwendet).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }
}
