package main.java.com.belegungsplaner.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Raum implements Serializable {
    private String name;
    private double flaeche;
    private int sitzplaetze;
    private int tische;
    private List<Buchung> buchungen; // Liste für Buchungen

    // Konstruktor
    public Raum(String name, double flaeche, int sitzplaetze, int tische) {
        this.name = name;
        this.flaeche = flaeche;
        this.sitzplaetze = sitzplaetze;
        this.tische = tische;
        this.buchungen = new ArrayList<>(); // Initialisieren der Buchungsliste
    }

    // Getter und Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getFlaeche() {
        return flaeche;
    }

    public void setFlaeche(double flaeche) {
        this.flaeche = flaeche;
    }

    public int getSitzplaetze() {
        return sitzplaetze;
    }

    public void setSitzplaetze(int sitzplaetze) {
        this.sitzplaetze = sitzplaetze;
    }

    public int getTische() {
        return tische;
    }

    public void setTische(int tische) {
        this.tische = tische;
    }

    // Methode zur Prüfung der Verfügbarkeit
    public boolean istVerfuegbar(Date startZeit, Date endZeit) {
        for (Buchung buchung : buchungen) {
            if (buchung.kollidiert(startZeit, endZeit)) {
                return false; // Raum ist nicht verfügbar
            }
        }
        return true; // Raum ist verfügbar
    }

    // Methode zur Buchung des Raumes
    public boolean buchungHinzufuegen(Buchung neueBuchung) {
        if (istVerfuegbar(neueBuchung.getStartZeit(), neueBuchung.getEndZeit())) {
            buchungen.add(neueBuchung);
            return true; // Buchung erfolgreich
        } else {
            return false; // Raum ist zur gewünschten Zeit nicht verfügbar
        }
    }

    // Methode zur Buchungsliste
    public List<Buchung> getBuchungen() {
        return buchungen;
    }

    // Abstrakte Methode, die von Unterklassen implementiert werden muss
    public abstract void nutzungBeschreiben();

    // Implementierung der compareTo-Methode aus dem Comparable-Interface
    @Override
    public int compareTo(Raum other) {
        // Beispiel: Räume nach Fläche vergleichen
        return Double.compare(this.flaeche, other.flaeche);
    }

    @Override
    public String toString() {
        return "Raum{" +
                "name='" + name + '\'' +
                ", flaeche=" + flaeche +
                ", sitzplaetze=" + sitzplaetze +
                ", tische=" + tische +
                '}';
    }
}
