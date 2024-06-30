import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {


        String jdbcURL = "jdbc:mysql://localhost:3306/booking_system";
        String dbUser = "root";
        String dbPassword = "testtest200";

        String sql = "select * from users ";


        // step 1
        Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);


        Statement st = connection.createStatement();
        final ResultSet rs = st.executeQuery(sql);
//       //System.out.println(rs.getString("email"));
//
        while (rs.next()) {
            int userId = rs.getInt("user_id");
            String username = rs.getString("username");
            String fullName = rs.getString("full_name");
            String email = rs.getString("email");
            System.out.println(email);
        }
//
//        // Clean up
        rs.close();
        st.close();
        connection.close();
    }
}
