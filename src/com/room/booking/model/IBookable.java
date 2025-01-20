package com.room.booking.model;

/**
 * Dieses Interface definiert Methoden, die für buchbare Objekte (User oder Employer) benötigt werden.
 */
public interface IBookable {
    /**
     * Gibt die eindeutige ID zurück.
     */
    int getId();

    /**
     * Gibt den vollständigen Namen zurück.
     */
    String getFullName();
}
