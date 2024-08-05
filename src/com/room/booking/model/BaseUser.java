package com.room.booking.model;

/**
 * Basis-Klasse für Benutzer.
 */
public class BaseUser {
    private int userId;
    private String username;
    private String fullName;
    private String email;
    private String password;

    /**
     * Konstruktor für die BaseUser-Klasse.
     *
     * @param userId   Die ID des Benutzers.
     * @param username Der Benutzername des Benutzers.
     * @param fullName Der vollständige Name des Benutzers.
     * @param email    Die E-Mail-Adresse des Benutzers.
     * @param password Das Passwort des Benutzers.
     */
    public BaseUser(int userId, String username, String fullName, String email, String password) {
        this.userId = userId;
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }

    // Getter und Setter Methoden
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
