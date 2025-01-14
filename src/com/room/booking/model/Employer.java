package com.room.booking.model;

public class Employer extends BaseUser {

    private String department;

    public Employer(int userId, String username, String fullName, String email, String password, String department) {
        super(userId, username, fullName, email, password);
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
}
