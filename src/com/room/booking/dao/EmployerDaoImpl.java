package com.room.booking.dao;

import com.room.booking.model.BaseUser;
import com.room.booking.model.Employer;

/**
 * Implementierung des EmployerDao-Interfaces für den Datenbankzugriff auf Arbeitgeberdaten.
 * Erweitert die BaseUserDaoImpl-Klasse, um Arbeitgeber-spezifische Methoden bereitzustellen
 */
public class EmployerDaoImpl extends BaseUserDaoImpl implements EmployerDao {

    /**
     * Holt einen Arbeitgeber anhand seiner ID.
     *
     * @param employerId Die ID des Arbeitgebers
     * @return Der Arbeitgeber, falls gefunden, sonst null.
     */
    @Override
    public Employer getEmployerById(int employerId) {
        BaseUser baseUser = super.getUserById(employerId);
        if (baseUser instanceof Employer) {
            return (Employer) baseUser;
        }
        return null;
    }
}
