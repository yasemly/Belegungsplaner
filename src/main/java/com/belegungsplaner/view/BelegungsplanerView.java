package main.java.com.belegungsplaner.view;

import javax.swing.*;
import java.awt.*;

public class BelegungsplanerView extends JFrame {
    private JPanel mainPanel;
    private JButton raumBuchenButton;
    private JButton buchungenAnzeigenButton;
    private JLabel welcomeLabel;

    public BelegungsplanerView() {
        setTitle("Belegungsplaner");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Fenster in der Mitte des Bildschirms anzeigen

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        welcomeLabel = new JLabel("Willkommen zum Belegungsplaner", JLabel.CENTER);
        mainPanel.add(welcomeLabel, BorderLayout.NORTH);

        raumBuchenButton = new JButton("Raum buchen");
        buchungenAnzeigenButton = new JButton("Buchungen anzeigen");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(raumBuchenButton);
        buttonPanel.add(buchungenAnzeigenButton);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    public JButton getRaumBuchenButton() {
        return raumBuchenButton;
    }

    public JButton getBuchungenAnzeigenButton() {
        return buchungenAnzeigenButton;
    }

    // Hier die main-Methode hinzufügen
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BelegungsplanerView view = new BelegungsplanerView();
            view.setVisible(true);
        });
    }
}
