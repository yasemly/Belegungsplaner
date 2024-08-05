package com.room.booking.dao;

import com.room.booking.model.BaseUser;
import com.room.booking.model.Employer;
import com.room.booking.model.User;
import com.room.booking.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementierung des BaseUserDao-Interfaces für den Datenbankzugriff auf BaseUser (Employer sowohl User)
 */
public class BaseUserDaoImpl implements BaseUserDao {

    // SQL-Abfragen
    private static final String SELECT_USER_BY_USERNAME_AND_PASSWORD_SQL =
            "SELECT * FROM users WHERE username = ? AND password = ?";
    private static final String SELECT_ALL_USERS_SQL =
            "SELECT * FROM users";
    private static final String INSERT_USER_SQL =
            "INSERT INTO users (username, full_name, email, password, role) VALUES (?, ?, ?, ?, ?)";
    private static final String INSERT_EMPLOYER_SQL =
            "INSERT INTO users (username, full_name, email, password, role, department) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_USER_SQL =
            "UPDATE users SET full_name = ?, email = ?, password = ?, role = ? WHERE user_id = ?";
    private static final String DELETE_USER_SQL =
            "DELETE FROM users WHERE user_id = ?";
    private static final String DELETE_USER_BY_USERNAME_SQL =
            "DELETE FROM users WHERE username = ?";

    /**
     * Ruft einen Benutzer anhand von Benutzername und Passwort ab.
     *
     * @param username Der Benutzername des Benutzers.
     * @param password Das Passwort des Benutzers.
     * @return Der Benutzer mit dem angegebenen Benutzernamen und Passwort oder null, wenn kein Benutzer gefunden wird.
     */
    @Override
    public BaseUser getUserByUsernameAndPassword(String username, String password) {
        BaseUser user = null;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_USER_BY_USERNAME_AND_PASSWORD_SQL)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = mapResultSetToUser(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Abrufen des Benutzers", e);
        }

        return user;
    }

    /**
     * Ruft alle Benutzer aus der Datenbank ab.
     *
     * @return Eine Liste aller Benutzer.
     */
    @Override
    public List<BaseUser> getAllUsers() {
        List<BaseUser> users = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_ALL_USERS_SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Abrufen aller Benutzer", e);
        }

        return users;
    }

    /**
     * Ruft einen Benutzer anhand seiner ID ab.
     *
     * @param userId Die ID des Benutzers.
     * @return Der Benutzer, wenn gefunden, ansonsten null.
     */
    @Override
    public BaseUser getUserById(int userId) {
        BaseUser user = null;
        String sql = "SELECT * FROM users WHERE user_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = mapResultSetToUser(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Abrufen des Benutzers", e);
        }

        return user;
    }

    /**
     * Erstellt einen neuen Benutzer in der Datenbank.
     *
     * @param userId   Die ID des Benutzers.
     * @param username Der Benutzername des Benutzers.
     * @param fullName Der vollständige Name des Benutzers.
     * @param email    Die E-Mail-Adresse des Benutzers.
     * @param password Das Passwort des Benutzers.
     */
    @Override
    public void createUser(int userId, String username, String fullName, String email, String password) {
        String sql = "INSERT INTO users (user_id, username, full_name, email, password) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setString(2, username);
            ps.setString(3, fullName);
            ps.setString(4, email);
            ps.setString(5, password);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Erstellen des Benutzers", e);
        }
    }

    /**
     * Registriert einen neuen Benutzer in der Datenbank.
     *
     * @param username Der Benutzername des Benutzers.
     * @param fullName Der vollständige Name des Benutzers.
     * @param email    Die E-Mail-Adresse des Benutzers.
     * @param password Das Passwort des Benutzers.
     * @param role     Die Rolle des Benutzers (z.B. "user", "employer").
     */
    @Override
    public void registerUser(String username, String fullName, String email, String password, String role) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_USER_SQL)) {

            ps.setString(1, username);
            ps.setString(2, fullName);
            ps.setString(3, email);
            ps.setString(4, password);
            ps.setString(5, role);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Fehler bei der Registrierung des Benutzers", e);
        }
    }

    /**
     * Registriert einen neuen Arbeitgeber in der Datenbank.
     *
     * @param username   Der Benutzername des Arbeitgebers.
     * @param fullName   Der vollständige Name des Arbeitgebers.
     * @param email      Die E-Mail-Adresse des Arbeitgebers.
     * @param password   Das Passwort des Arbeitgebers.
     * @param department Die Abteilung des Arbeitgebers.
     */
    @Override
    public void registerEmployer(String username, String fullName, String email, String password, String department) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_EMPLOYER_SQL)) {

            ps.setString(1, username);
            ps.setString(2, fullName);
            ps.setString(3, email);
            ps.setString(4, password);
            ps.setString(5, "employer");
            ps.setString(6, department);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Fehler bei der Registrierung des Arbeitgebers", e);
        }
    }

    /**
     * Löscht einen Benutzer anhand seines Benutzernamens.
     *
     * @param username Der Benutzername des zu löschenden Benutzers.
     * @return true, wenn der Benutzer erfolgreich gelöscht wurde, andernfalls false.
     */
    @Override
    public boolean deleteUserByUsername(String username) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_USER_BY_USERNAME_SQL)) {

            ps.setString(1, username);
            int rowsAffected = ps.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Löschen des Benutzers", e);
        }
    }

    /**
     * Aktualisiert die Informationen eines Benutzers in der Datenbank.
     *
     * @param userId   Die ID des Benutzers.
     * @param fullName Der vollständige Name des Benutzers.
     * @param email    Die E-Mail-Adresse des Benutzers.
     * @param password Das Passwort des Benutzers.
     * @param role     Die Rolle des Benutzers (z.B. "user", "employer").
     */
    @Override
    public void updateUser(int userId, String fullName, String email, String password, String role) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_USER_SQL)) {

            ps.setString(1, fullName);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setString(4, role);
            ps.setInt(5, userId);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Aktualisieren des Benutzers", e);
        }
    }

    /**
     * Löscht einen Benutzer anhand seiner ID.
     *
     * @param userId Die ID des zu löschenden Benutzers.
     */
    @Override
    public void deleteUser(int userId) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_USER_SQL)) {

            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Löschen des Benutzers", e);
        }
    }

    /**
     * Ordnet einen ResultSet-Eintrag einem BaseUser-Objekt zu.
     *
     * @param rs Das ResultSet, das Benutzerinformationen enthält.
     * @return Ein BaseUser-Objekt, das die Informationen des Benutzers darstellt.
     * @throws SQLException Wenn ein Fehler beim Zugriff auf das ResultSet auftritt.
     */
    protected BaseUser mapResultSetToUser(ResultSet rs) throws SQLException {
        int userId = rs.getInt("user_id");
        String username = rs.getString("username");
        String fullName = rs.getString("full_name");
        String email = rs.getString("email");
        String password = rs.getString("password");
        String role = rs.getString("role");

        if ("employer".equalsIgnoreCase(role)) {
            String department = rs.getString("department"); // Angenommen, Abteilung wird für Arbeitgeber gespeichert
            return new Employer(userId, username, fullName, email, password, department);
        } else {
            // Fehler behoben: userId zum User-Konstruktor hinzugefügt
            return new User(userId, username, fullName, email, password, role);
        }
    }
}
