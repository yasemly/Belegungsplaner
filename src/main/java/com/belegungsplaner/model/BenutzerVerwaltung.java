package main.java.com.belegungsplaner.model;

import java.util.ArrayList;
import java.util.List;

public class BenutzerVerwaltung {
    private List<Benutzer> benutzerListe;

    public BenutzerVerwaltung() {
        benutzerListe = new ArrayList<>();
    }

    // Methode zur Registrierung
    public boolean registrieren(String benutzername, String passwort, String email) {
        for (Benutzer benutzer : benutzerListe) {
            if (benutzer.getBenutzername().equals(benutzername)) {
                return false; // Benutzername existiert bereits
            }
        }
        Benutzer neuerBenutzer = new Benutzer(benutzername, passwort, email);
        benutzerListe.add(neuerBenutzer);
        return true;
    }

    // Methode zur Anmeldung
    public Benutzer anmelden(String benutzername, String passwort) {
        for (Benutzer benutzer : benutzerListe) {
            if (benutzer.getBenutzername().equals(benutzername) && benutzer.getPasswort().equals(passwort)) {
                return benutzer;
            }
        }
        return null; // Anmeldedaten falsch
    }

    public List<Benutzer> getBenutzerListe() {
        return benutzerListe;
    }
}
