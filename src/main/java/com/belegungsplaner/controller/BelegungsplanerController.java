package main.java.com.belegungsplaner.controller;

import main.java.com.belegungsplaner.model.Benutzer;
import main.java.com.belegungsplaner.model.Buchung;
import main.java.com.belegungsplaner.model.Raum;

import java.util.ArrayList;
import java.util.List;

public class BelegungsplanerController {
    private List<Buchung> buchungen;
    private List<Raum> raeume;

    public BelegungsplanerController() {
        buchungen = new ArrayList<>();
        raeume = new ArrayList<>();

        // Beispielräume hinzufügen
        raeume.add(new Raum("Raum A", 10));
        raeume.add(new Raum("Raum B", 20));
        raeume.add(new Raum("Raum C", 30));
    }

    public void buchungErstellen(Benutzer benutzer, String raumName, String datum, String uhrzeit) {
        Raum raum = findeRaum(raumName);
        if (raum != null && raum.isVerfuegbar()) {
            Buchung buchung = new Buchung(benutzer, raum, datum, uhrzeit);
            buchungen.add(buchung);
            raum.setVerfuegbar(false);
        }
    }

    public Raum findeRaum(String name) {
        for (Raum raum : raeume) {
            if (raum.getName().equals(name)) {
                return raum;
            }
        }
        return null;
    }

    public List<Buchung> getBuchungen() {
        return new ArrayList<>(buchungen);
    }

    public List<Raum> getRaeume() {
        return new ArrayList<>(raeume);
    }
}
