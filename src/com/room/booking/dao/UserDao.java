package com.room.booking.dao;

import com.room.booking.model.User;

import java.util.List;

public interface UserDao {
    User getUserByUsernameAndPassword(String username, String password);
    List<User> getAllUsers();
    User getUserByUserame(String username);
    User getUserById(int id);
    void registerUser(String username, String fullName, String email, String password);
    void registerEmployer(String username, String fullName, String email, String password);
    void deleteUser(String username);


}
