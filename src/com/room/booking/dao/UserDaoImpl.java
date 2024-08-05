package com.room.booking.dao;

import com.room.booking.model.BaseUser;
import com.room.booking.model.User;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the UserDao interface for database access to user data.
 * Extends the BaseUserDaoImpl class to provide user-specific methods.
 */
public class UserDaoImpl extends BaseUserDaoImpl implements UserDao {

    /**
     * Retrieves a user by their ID.
     *
     * @param userId The ID of the user.
     * @return The user if found, otherwise null.
     */
    @Override
    public User getUserById(int userId) {
        BaseUser baseUser = super.getUserById(userId);
        if (baseUser instanceof User) {
            return (User) baseUser;
        }
        return null;
    }

    /**
     * Retrieves all users from the database.
     *
     * @return A list of all users.
     */
    @Override
    public List<BaseUser> getAllUsers() {
        List<BaseUser> baseUsers = super.getAllUsers(); // Call method from BaseUserDaoImpl
        return baseUsers.stream()
                .filter(user -> user instanceof User)
                .map(user -> (User) user)
                .collect(Collectors.toList());
    }
}
