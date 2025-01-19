package com.room.booking.dao;

import com.room.booking.model.User;
import com.room.booking.util.DBConnection;

import java.sql.*;

public class UserDaoImpl implements UserDao {

    private static final String INSERT_USER_SQL =
            "INSERT INTO users (username, full_name, email, password) VALUES (?, ?, ?, ?)";
    private static final String SELECT_BY_USERNAME_PASSWORD_SQL =
            "SELECT * FROM users WHERE username = ? AND password = ?";
    private static final String SELECT_BY_ID_SQL =
            "SELECT * FROM users WHERE user_id = ?";
    private static final String UPDATE_USER_SQL =
            "UPDATE users SET username = ?, full_name = ?, email = ?, password = ? WHERE user_id = ?";
    private static final String DELETE_USER_SQL =
            "DELETE FROM users WHERE user_id = ?";

    @Override
    public User createUser(User user) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_USER_SQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getFullName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());
            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    user.setUserId(keys.getInt(1));
                }
            }
            return user;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create user in 'users' table", e);
        }
    }

    @Override
    public User getUserByUsernameAndPassword(String username, String password) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_USERNAME_PASSWORD_SQL)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapToUser(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get user from 'users' table", e);
        }
        return null;
    }

    @Override
    public User getUserById(int userId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID_SQL)) {

            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapToUser(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get user by ID from 'users' table", e);
        }
        return null;
    }

    @Override
    public void updateUser(User user) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_USER_SQL)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getFullName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());
            stmt.setInt(5, user.getUserId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update user in 'users' table", e);
        }
    }

    @Override
    public void deleteUser(int userId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_USER_SQL)) {

            stmt.setInt(1, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete user from 'users' table", e);
        }
    }

    private User mapToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setFullName(rs.getString("full_name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        return user;
    }
}
