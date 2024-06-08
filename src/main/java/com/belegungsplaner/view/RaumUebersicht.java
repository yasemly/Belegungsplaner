package main.java.com.belegungsplaner.view;

import javax.swing.*;
import java.awt.*;

public class RaumUebersicht extends JFrame {
    private JList<String> raumList;

    public RaumUebersicht() {
        setTitle("Raumübersicht");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] raeume = {"Raum A", "Raum B", "Raum C"};
        raumList = new JList<>(raeume);

        add(new JScrollPane(raumList), BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RaumUebersicht uebersicht = new RaumUebersicht();
            uebersicht.setVisible(true);
        });
    }
}
