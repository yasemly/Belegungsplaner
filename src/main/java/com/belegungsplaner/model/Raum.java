package main.java.com.belegungsplaner.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Raum implements Comparable<Raum>, Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private double flaeche;
    private int sitzplaetze;
    private int tische;
    private String standort;
    private boolean wlanVerfuegbar;
    private boolean klimatisierung;
    private List<String> ausstattung;
    private List<Buchung> buchungen;

    // Konstruktor
    public Raum(String name, double flaeche, int sitzplaetze, int tische, String standort, boolean wlanVerfuegbar, boolean klimatisierung) {
        this.name = name;
        this.flaeche = flaeche;
        this.sitzplaetze = sitzplaetze;
        this.tische = tische;
        this.standort = standort;
        this.wlanVerfuegbar = wlanVerfuegbar;
        this.klimatisierung = klimatisierung;
        this.ausstattung = new ArrayList<>();
        this.buchungen = new ArrayList<>();
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

    public List<Buchung> getBuchungen() {
        return buchungen;
    }

    public void addAusstattung(String ausstattungElement) {
        this.ausstattung.add(ausstattungElement);
    }

    // Methode zur Prüfung der Verfügbarkeit
    public boolean istVerfuegbar(Date startZeit, Date endZeit) {
        for (Buchung buchung : buchungen) {
            if (buchung.kollidiert(startZeit, endZeit)) {
                return false;
            }
        }
        return true;
    }

    // Methode zur Buchung des Raumes
    public boolean buchungHinzufuegen(Buchung neueBuchung) {
        if (istVerfuegbar(neueBuchung.getStartZeit(), neueBuchung.getEndZeit())) {
            buchungen.add(neueBuchung);
            return true;
        } else {
            return false;
        }
    }

    // Methode zur Stornierung einer Buchung
    public boolean buchungStornieren(Buchung zuStornierendeBuchung) {
        return buchungen.remove(zuStornierendeBuchung);
    }

    // Methode zur Änderung einer Buchung
    public boolean buchungAendern(Buchung alteBuchung, Buchung neueBuchung) {
        if (buchungen.contains(alteBuchung)) {
            buchungen.remove(alteBuchung);
            return buchungHinzufuegen(neueBuchung);
        }
        return false;
    }

    // Abstrakte Methode
    public abstract void nutzungBeschreiben();

    // Implementierung der compareTo-Methode
    @Override
    public int compareTo(Raum other) {
        return Double.compare(this.flaeche, other.flaeche);
    }

    @Override
    public String toString() {
        return "Raum{" +
                "name='" + name + '\'' +
                ", flaeche=" + flaeche +
                ", sitzplaetze=" + sitzplaetze +
                ", tische=" + tische +
                ", standort='" + standort + '\'' +
                ", wlanVerfuegbar=" + wlanVerfuegbar +
                ", klimatisierung=" + klimatisierung +
                ", ausstattung=" + ausstattung +
                '}';
    }
}

