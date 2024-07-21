package main.java.com.belegungsplaner.model;

import main.java.com.belegungsplaner.view.AnmeldeView;

public class Main {
    public static void main(String[] args) {
        BenutzerVerwaltung benutzerVerwaltung = new BenutzerVerwaltung();
        AnmeldeView anmeldeView = new AnmeldeView(benutzerVerwaltung);
        anmeldeView.setVisible(true);
    }
}
