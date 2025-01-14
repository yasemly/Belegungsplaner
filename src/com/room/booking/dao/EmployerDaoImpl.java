package com.room.booking.dao;

import com.room.booking.model.BaseUser;
import com.room.booking.model.Employer;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementierung des EmployerDao-Interfaces für arbeitgeber-spezifische Operationen.
 */
public class EmployerDaoImpl extends BaseUserDaoImpl implements EmployerDao {

    @Override
    public List<Employer> getAllEmployers() {
        // Ruft alle Benutzer ab und filtert nur die Arbeitgeber (department != null)
        return super.getAllUsers().stream()
                .filter(u -> u instanceof Employer)
                .map(u -> (Employer) u)
                .collect(Collectors.toList());
    }

    @Override
    public Employer getEmployerById(int employerId) {
        BaseUser base = super.getUserById(employerId);
        if (base instanceof Employer) {
            return (Employer) base;
        }
        return null; // oder wirf eine Ausnahme, wenn der Benutzer tatsächlich ein normaler User ist
    }
}
