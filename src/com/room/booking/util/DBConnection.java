
package com.room.booking.util;
        import java.sql.Connection;
        import java.sql.DriverManager;
        import java.sql.SQLException;

public class DBConnection {

    private static final String jdbcURL = "jdbc:mysql://localhost:3306/booking_system";
    private static final String dbUser = "root";
    private static final String dbPassword = "testtest200";


    public static Connection getConnection() {
        Connection con = null;

        try {
            con = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return con;
    }
}
