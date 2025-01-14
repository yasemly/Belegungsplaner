package com.room.booking.dao;

import com.room.booking.model.User;
import java.util.List;

/**
 * Spezielles DAO-Interface für normale Benutzer.
 */
public interface UserDao extends BaseUserDao {

    /**
     * Gibt nur die normalen Benutzer (wo department null ist) zurück.
     */
    List<User> getAllNormalUsers();

    /**
     * Gibt einen normalen Benutzer anhand der ID zurück.
     */
    User getUserById(int userId);
}
