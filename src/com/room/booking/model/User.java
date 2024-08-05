package com.room.booking.model;

/**
 * Repräsentiert einen normalen Benutzer im Raumbuchungssystem.
 * Erweitert die Basisklasse BaseUser mit allgemeinen Benutzerattributen.
 */
public class User extends BaseUser {

    /**
     * Konstruktor für die User-Klasse.
     *
     * @param userId   Die ID des Benutzers.
     * @param username Der Benutzername des Benutzers.
     * @param fullName Der vollständige Name des Benutzers.
     * @param email    Die E-Mail-Adresse des Benutzers.
     * @param password Das Passwort des Benutzers.
     */
    public User(int userId, String username, String fullName, String email, String password) {
        super(userId, username, fullName, email, password);
    }
}
