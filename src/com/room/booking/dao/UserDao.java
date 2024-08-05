package com.room.booking.dao;

import com.room.booking.model.BaseUser;
import com.room.booking.model.User;
import java.util.List;

/**
 * Interface for accessing user data in the database.
 * Extends the BaseUserDao interface to provide additional user-specific methods.
 */
public interface UserDao extends BaseUserDao {

    /**
     * Retrieves a user by their ID.
     *
     * @param userId The ID of the user.
     * @return The user if found, otherwise null.
     */
    User getUserById(int userId);

    /**
     * Retrieves all users from the database.
     *
     * @return A list of all users.
     */
    List<BaseUser> getAllUsers(); // Ensures this returns List<User>
}
