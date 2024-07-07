package com.room.booking.model;

public class User {

    private int userId; //column names
    private String userName;
    private String fullName;
    private String email;


    public User(int userId, String userName, String fullName, String email) {
        this.userId = userId;
        this.userName = userName;
        this.fullName = fullName;
        this.email = email;
    }

    public User() {

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



}
