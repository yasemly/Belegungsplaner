package main.java.com.belegungsplaner.view;

import main.java.com.belegungsplaner.model.Benutzer;
import main.java.com.belegungsplaner.model.BenutzerVerwaltung;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnmeldeView extends JFrame {
    private JTextField benutzernameField;
    private JPasswordField passwortField;
    private JButton anmeldenButton;
    private JButton registrierenButton;
    private BenutzerVerwaltung benutzerVerwaltung;

    public AnmeldeView(BenutzerVerwaltung benutzerVerwaltung) {
        this.benutzerVerwaltung = benutzerVerwaltung;

        setTitle("Anmeldung");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Benutzername:"));
        benutzernameField = new JTextField();
        add(benutzernameField);

        add(new JLabel("Passwort:"));
        passwortField = new JPasswordField();
        add(passwortField);

        anmeldenButton = new JButton("Anmelden");
        add(anmeldenButton);

        registrierenButton = new JButton("Registrieren");
        add(registrierenButton);

        anmeldenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String benutzername = benutzernameField.getText();
                String passwort = new String(passwortField.getPassword());

                Benutzer benutzer = benutzerVerwaltung.anmelden(benutzername, passwort);
                if (benutzer != null) {
                    JOptionPane.showMessageDialog(null, "Anmeldung erfolgreich!");
                    // Hier könntest du den Benutzer in die Hauptanwendung weiterleiten
                } else {
                    JOptionPane.showMessageDialog(null, "Anmeldedaten sind falsch!");
                }
            }
        });

        registrierenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new RegistrierungsView(benutzerVerwaltung).setVisible(true);
            }
        });
    }
}
