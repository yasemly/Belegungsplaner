package main.java.com.belegungsplaner.model;

import java.util.Date;

public class Buchung {
    private Date startZeit;
    private Date endZeit;

    public Buchung(Date startZeit, Date endZeit) {
        this.startZeit = startZeit;
        this.endZeit = endZeit;
    }

    public Date getStartZeit() {
        return startZeit;
    }

    public Date getEndZeit() {
        return endZeit;
    }

    // Methode zur Prüfung, ob sich zwei Buchungen überschneiden
    public boolean kollidiert(Date startZeit, Date endZeit) {
        return this.startZeit.before(endZeit) && this.endZeit.after(startZeit);
    }

    @Override
    public String toString() {
        return "Buchung{" +
                "startZeit=" + startZeit +
                ", endZeit=" + endZeit +
                '}';
    }
}

