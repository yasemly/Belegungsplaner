package com.room.booking.model;

/**
 * Repräsentiert einen Arbeitgeber im Raumbuchungssystem.
 * Enthält zusätzliche Attribute wie die Abteilung.
 */
public class Employer extends BaseUser {

    private String department;

    /**
     * Konstruktor für die Employer-Klasse.
     *
     * @param userId     Die ID des Arbeitgebers.
     * @param username   Der Benutzername des Arbeitgebers.
     * @param fullName   Der vollständige Name des Arbeitgebers.
     * @param email      Die E-Mail-Adresse des Arbeitgebers.
     * @param password   Das Passwort des Arbeitgebers.
     * @param department Die Abteilung des Arbeitgebers.
     */
    public Employer(int userId, String username, String fullName, String email, String password, String department) {
        super(userId, username, fullName, email, password);
        this.department = department;
    }

    /**
     * Gibt die Abteilung des Arbeitgebers zurück.
     *
     * @return Die Abteilung.
     */
    public String getDepartment() {
        return department;
    }

    /**
     * Setzt die Abteilung des Arbeitgebers.
     *
     * @param department Die neue Abteilung.
     */
    public void setDepartment(String department) {
        this.department = department;
    }
}
