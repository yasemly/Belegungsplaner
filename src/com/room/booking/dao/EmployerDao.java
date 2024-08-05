package com.room.booking.dao;

import com.room.booking.model.Employer;

/**
 * Erweitert das BaseUserDao-Interface, um zusätzliche Employer-spezifische Methoden bereitzustellen
 */
public interface EmployerDao extends BaseUserDao {

    /**
     * Holt einen Arbeitgeber anhand seiner ID.
     *
     * @param employerId Die ID des Arbeitgebers
     * @return Der Arbeitgeber, falls gefunden, sonst null.
     */
    Employer getEmployerById(int employerId);
}
