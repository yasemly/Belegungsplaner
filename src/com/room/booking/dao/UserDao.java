package com.room.booking.dao;

import com.room.booking.model.User;

public interface UserDao {
    User createUser(User user);
    User getUserByUsernameAndPassword(String username, String password);
    User getUserById(int userId);
    void updateUser(User user);
    void deleteUser(int userId);
}
