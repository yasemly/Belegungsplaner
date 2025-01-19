package com.room.booking.dao;

import com.room.booking.model.Employer;

public interface EmployerDao {
    Employer createEmployer(Employer employer);
    Employer getEmployerByUsernameAndPassword(String username, String password);
    Employer getEmployerById(int employerId);
    void updateEmployer(Employer employer);
    void deleteEmployer(int employerId);
    boolean deleteEmployerByUsername(String username);

}
