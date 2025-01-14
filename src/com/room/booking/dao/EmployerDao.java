package com.room.booking.dao;

import com.room.booking.model.Employer;
import java.util.List;

/**
 * Spezielles DAO-Interface für Arbeitgeber.
 */
public interface EmployerDao extends BaseUserDao {

    /**
     * Gibt nur die Arbeitgeber (wo department nicht null ist) zurück.
     */
    List<Employer> getAllEmployers();

    /**
     * Gibt einen Arbeitgeber anhand der ID zurück.
     */
    Employer getEmployerById(int employerId);
}
