package com.room.booking.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Hilfsklasse für die Herstellung und Verwaltung von Datenbankverbindungen.
 */
public class DBConnection {

    // Datenbankverbindungsdetails für PostgreSQL
    private static final String URL = "jdbc:postgresql://localhost:5432/booking_system";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "test";

    // Privater Konstruktor, um Instanziierung zu verhindern
    private DBConnection() {
    }

    /**
     * Stellt eine Verbindung zur Datenbank her.
     *
     * @return Die hergestellte Datenbankverbindung.
     * @throws RuntimeException Wenn ein Fehler beim Herstellen der Verbindung auftritt.
     */
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Herstellen der Datenbankverbindung", e);
        }
    }

    /**
     * Schließt eine Datenbankverbindung.
     *
     * @param connection Die zu schließende Verbindung.
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace(); // In einer realen Anwendung: besseres Fehlerhandling
            }
        }
    }

    /**
     * Schließt ein Statement.
     *
     * @param statement Das zu schließende Statement.
     */
    public static void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace(); // In einer realen Anwendung: besseres Fehlerhandling
            }
        }
    }

    /**
     * Schließt ein ResultSet.
     *
     * @param resultSet Das zu schließende ResultSet.
     */
    public static void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace(); // In einer realen Anwendung: besseres Fehlerhandling
            }
        }
    }
}
