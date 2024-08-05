package com.room.booking.dao;

import com.room.booking.model.BaseUser;
import java.util.List;

/**
 * Interface for accessing base user data in the database.
 */
public interface BaseUserDao {

    BaseUser getUserByUsernameAndPassword(String username, String password);

    // Change this to return List<BaseUser>
    List<BaseUser> getAllUsers();

    BaseUser getUserById(int userId);

    void createUser(int userId, String username, String fullName, String email, String password);

    void registerUser(String username, String fullName, String email, String password);

    void registerEmployer(String username, String fullName, String email, String password, String department);

    boolean deleteUserByUsername(String username);

    void updateUser(int userId, String fullName, String email, String password, String role);

    void deleteUser(int userId);
}
