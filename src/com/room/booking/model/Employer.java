package com.room.booking.model;

public class Employer {
    private int employerId;
    private String username;
    private String fullName;
    private String email;
    private String password;
    private String department;

    public Employer() {
    }

    public Employer(int employerId, String username, String fullName,
                    String email, String password, String department) {
        this.employerId = employerId;
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.department = department;
    }

    // Getters & setters
    public int getEmployerId() {
        return employerId;
    }
    public void setEmployerId(int employerId) {
        this.employerId = employerId;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
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

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
}
