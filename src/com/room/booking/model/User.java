package com.room.booking.model;

public class User extends BaseUser {

    public User(int userId, String username, String fullName, String email, String password) {
        super(userId, username, fullName, email, password);
    }

    // No extra fields; just a normal user
}
