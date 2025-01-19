//package com.room.booking.dao;
//
//import com.room.booking.model.BaseUser;
//import com.room.booking.model.Employer;
//import com.room.booking.model.User;
//import com.room.booking.util.DBConnection;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Implementierung von BaseUserDao für eine "users"-Tabelle.
// */
//public class BaseUserDaoImpl implements BaseUserDao {
//
//    // SQL-Abfragen (stubs for a 'users' table)
//    private static final String SELECT_BY_USERNAME_PASSWORD =
//            "SELECT * FROM users WHERE username = ? AND password = ?";
//    private static final String SELECT_ALL =
//            "SELECT * FROM users";
//    private static final String SELECT_BY_ID =
//            "SELECT * FROM users WHERE user_id = ?";
//    private static final String INSERT_USER =
//            "INSERT INTO users (username, full_name, email, password, department) VALUES (?, ?, ?, ?, ?)";
//    private static final String UPDATE_USER =
//            "UPDATE users SET username = ?, full_name = ?, email = ?, password = ?, department = ? WHERE user_id = ?";
//    private static final String DELETE_USER =
//            "DELETE FROM users WHERE user_id = ?";
//    private static final String DELETE_USER_BY_USERNAME =
//            "DELETE FROM users WHERE username = ?";
//
//    @Override
//    public BaseUser getUserByUsernameAndPassword(String username, String password) {
//        try (Connection conn = DBConnection.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_USERNAME_PASSWORD)) {
//
//            stmt.setString(1, username);
//            stmt.setString(2, password);
//
//            try (ResultSet rs = stmt.executeQuery()) {
//                if (rs.next()) {
//                    return mapResultSetToUser(rs);
//                }
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException("Fehler beim Abrufen des Benutzers nach Benutzername/Passwort", e);
//        }
//        return null;
//    }
//
//    @Override
//    public List<BaseUser> getAllUsers() {
//        List<BaseUser> result = new ArrayList<>();
//        try (Connection conn = DBConnection.getConnection();
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery(SELECT_ALL)) {
//
//            while (rs.next()) {
//                result.add(mapResultSetToUser(rs));
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException("Fehler beim Abrufen aller Benutzer", e);
//        }
//        return result;
//    }
//
//    @Override
//    public BaseUser getUserById(int userId) {
//        try (Connection conn = DBConnection.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID)) {
//
//            stmt.setInt(1, userId);
//            try (ResultSet rs = stmt.executeQuery()) {
//                if (rs.next()) {
//                    return mapResultSetToUser(rs);
//                }
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException("Fehler beim Abrufen des Benutzers nach ID", e);
//        }
//        return null;
//    }
//
//    @Override
//    public BaseUser createBaseUser(BaseUser user) {
//        String department = (user instanceof Employer)
//                ? ((Employer) user).getDepartment()
//                : null;
//
//        try (Connection conn = DBConnection.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
//
//            stmt.setString(1, user.getUsername());
//            stmt.setString(2, user.getFullName());
//            stmt.setString(3, user.getEmail());
//            stmt.setString(4, user.getPassword());
//            stmt.setString(5, department);
//
//            stmt.executeUpdate();
//
//            try (ResultSet keys = stmt.getGeneratedKeys()) {
//                if (keys.next()) {
//                    user.setUserId(keys.getInt(1));
//                }
//            }
//            return user;
//        } catch (SQLException e) {
//            throw new RuntimeException("Fehler beim Erstellen des Benutzers", e);
//        }
//    }
//
//    @Override
//    public void updateUser(BaseUser user) {
//        String department = (user instanceof Employer)
//                ? ((Employer) user).getDepartment()
//                : null;
//
//        try (Connection conn = DBConnection.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(UPDATE_USER)) {
//
//            stmt.setString(1, user.getUsername());
//            stmt.setString(2, user.getFullName());
//            stmt.setString(3, user.getEmail());
//            stmt.setString(4, user.getPassword());
//            stmt.setString(5, department);
//            stmt.setInt(6, user.getUserId());
//
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException("Fehler beim Aktualisieren des Benutzers", e);
//        }
//    }
//
//    @Override
//    public void deleteUser(int userId) {
//        try (Connection conn = DBConnection.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(DELETE_USER)) {
//
//            stmt.setInt(1, userId);
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException("Fehler beim Löschen des Benutzers nach ID", e);
//        }
//    }
//
//    @Override
//    public boolean deleteUserByUsername(String username) {
//        try (Connection conn = DBConnection.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(DELETE_USER_BY_USERNAME)) {
//
//            stmt.setString(1, username);
//            int rowsAffected = stmt.executeUpdate();
//            return rowsAffected > 0;
//        } catch (SQLException e) {
//            throw new RuntimeException("Fehler beim Löschen des Benutzers nach Benutzername", e);
//        }
//    }
//
//    @Override
//    public void registerUser(String username, String fullName, String email, String password) {
//        // Normaler Benutzer => department = null
//        User user = new User(0, username, fullName, email, password);
//        createBaseUser(user);
//    }
//
//    @Override
//    public void registerEmployer(String username, String fullName, String email, String password, String department) {
//        // Arbeitgeber => department != null
//        Employer employer = new Employer(0, username, fullName, email, password, department);
//        createBaseUser(employer);
//    }
//
//    /**
//     * Hilfsmethode: Wandelt eine Zeile aus dem ResultSet in ein BaseUser-Objekt um.
//     */
//    private BaseUser mapResultSetToUser(ResultSet rs) throws SQLException {
//        int userId = rs.getInt("user_id");
//        String username = rs.getString("username");
//        String fullName = rs.getString("full_name");
//        String email = rs.getString("email");
//        String password = rs.getString("password");
//        String department = rs.getString("department"); // null => normaler User
//
//        if (department != null) {
//            return new Employer(userId, username, fullName, email, password, department);
//        } else {
//            return new User(userId, username, fullName, email, password);
//        }
//    }
//}
