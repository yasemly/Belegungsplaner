package com.room.booking.dao;

import com.room.booking.model.Employer;
import com.room.booking.util.DBConnection;

import java.sql.*;

public class EmployerDaoImpl implements EmployerDao {

    private static final String INSERT_EMPLOYER_SQL =
            "INSERT INTO employers (username, full_name, email, password, department) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_BY_USERNAME_PASSWORD_SQL =
            "SELECT * FROM employers WHERE username = ? AND password = ?";
    private static final String SELECT_BY_ID_SQL =
            "SELECT * FROM employers WHERE employer_id = ?";
    private static final String UPDATE_EMPLOYER_SQL =
            "UPDATE employers SET username = ?, full_name = ?, email = ?, password = ?, department = ? WHERE employer_id = ?";
    private static final String DELETE_EMPLOYER_SQL =
            "DELETE FROM employers WHERE employer_id = ?";

    private static final String DELETE_EMPLOYER_BY_USERNAME_SQL =
            "DELETE FROM employers WHERE username = ?";

    @Override
    public boolean deleteEmployerByUsername(String username) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_EMPLOYER_BY_USERNAME_SQL)) {

            stmt.setString(1, username);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete employer by username", e);
        }
    }
    @Override
    public Employer createEmployer(Employer employer) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_EMPLOYER_SQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, employer.getUsername());
            stmt.setString(2, employer.getFullName());
            stmt.setString(3, employer.getEmail());
            stmt.setString(4, employer.getPassword());
            stmt.setString(5, employer.getDepartment());
            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    employer.setEmployerId(keys.getInt(1));
                }
            }
            return employer;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create employer in 'employers' table", e);
        }
    }

    @Override
    public Employer getEmployerByUsernameAndPassword(String username, String password) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_USERNAME_PASSWORD_SQL)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapToEmployer(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get employer from 'employers' table", e);
        }
        return null;
    }

    @Override
    public Employer getEmployerById(int employerId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID_SQL)) {

            stmt.setInt(1, employerId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapToEmployer(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get employer by ID from 'employers' table", e);
        }
        return null;
    }

    @Override
    public void updateEmployer(Employer employer) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_EMPLOYER_SQL)) {

            stmt.setString(1, employer.getUsername());
            stmt.setString(2, employer.getFullName());
            stmt.setString(3, employer.getEmail());
            stmt.setString(4, employer.getPassword());
            stmt.setString(5, employer.getDepartment());
            stmt.setInt(6, employer.getEmployerId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to update employer in 'employers' table", e);
        }
    }

    @Override
    public void deleteEmployer(int employerId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_EMPLOYER_SQL)) {

            stmt.setInt(1, employerId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete employer from 'employers' table", e);
        }
    }

    private Employer mapToEmployer(ResultSet rs) throws SQLException {
        Employer e = new Employer();
        e.setEmployerId(rs.getInt("employer_id"));
        e.setUsername(rs.getString("username"));
        e.setFullName(rs.getString("full_name"));
        e.setEmail(rs.getString("email"));
        e.setPassword(rs.getString("password"));
        e.setDepartment(rs.getString("department"));
        return e;
    }
}
