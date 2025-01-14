package com.room.booking.dao;

import com.room.booking.model.BaseUser;
import java.util.List;

/**
 * DAO-Interface für alle Benutzeroperationen (sowohl User als auch Employer).
 * Unterscheidung erfolgt durch department != null für Employer, null für normalen User.
 */
public interface BaseUserDao {

    /**
     * Gibt einen Benutzer (User oder Employer) anhand von Benutzername und Passwort zurück.
     */
    BaseUser getUserByUsernameAndPassword(String username, String password);

    /**
     * Gibt alle Benutzer in der 'users'-Tabelle zurück (sowohl normale Benutzer als auch Arbeitgeber).
     */
    List<BaseUser> getAllUsers();

    /**
     * Gibt einen einzelnen Benutzer (User oder Employer) anhand der numerischen ID zurück.
     */
    BaseUser getUserById(int userId);

    /**
     * Fügt einen BaseUser (User oder Employer) in die DB ein.
     */
    BaseUser createBaseUser(BaseUser user);

    /**
     * Aktualisiert einen bestehenden Benutzer (User oder Employer).
     */
    void updateUser(BaseUser user);

    /**
     * Löscht einen Benutzer anhand der numerischen ID.
     */
    void deleteUser(int userId);

    /**
     * Löscht einen Benutzer anhand des Benutzernamens.
     */
    boolean deleteUserByUsername(String username);

    /**
     * Registriert einen normalen Benutzer (department = null).
     */
    void registerUser(String username, String fullName, String email, String password);

    /**
     * Registriert einen Arbeitgeber (department != null).
     */
    void registerEmployer(String username, String fullName, String email, String password, String department);
}
