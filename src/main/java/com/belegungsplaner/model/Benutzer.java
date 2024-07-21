package main.java.com.belegungsplaner.model;

import java.io.Serializable;

public class Benutzer implements Serializable {
    private static final long serialVersionUID = 1L;
    private String benutzername;
    private String passwort;
    private String email;

    // Konstruktor
    public Benutzer(String benutzername, String passwort, String email) {
        this.benutzername = benutzername;
        this.passwort = passwort;
        this.email = email;
    }

    // Getter und Setter
    public String getBenutzername() {
        return benutzername;
    }

    public void setBenutzername(String benutzername) {
        this.benutzername = benutzername;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Benutzer{" +
                "benutzername='" + benutzername + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
