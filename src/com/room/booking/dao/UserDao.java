package com.room.booking.dao;



import com.room.booking.model.User;

import java.util.List;

public interface UserDao {

    List<User> getAllUsers();
    User getUserById(int id);

    User getUserByName(String userName);
}
