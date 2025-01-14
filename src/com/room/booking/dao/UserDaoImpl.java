package com.room.booking.dao;

import com.room.booking.model.BaseUser;
import com.room.booking.model.User;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementierung des UserDao-Interfaces für benutzer-spezifische Operationen.
 */
public class UserDaoImpl extends BaseUserDaoImpl implements UserDao {

    @Override
    public List<User> getAllNormalUsers() {
        // Ruft alle Benutzer ab und filtert nur die normalen User (department = null)
        return super.getAllUsers().stream()
                .filter(u -> u instanceof User && !(u instanceof com.room.booking.model.Employer))
                .map(u -> (User) u)
                .collect(Collectors.toList());
    }

    @Override
    public User getUserById(int userId) {
        BaseUser base = super.getUserById(userId);
        if (base instanceof User) {
            return (User) base;
        }
        return null; // oder wirf eine Ausnahme, wenn der Benutzer tatsächlich ein Employer ist
    }
}
