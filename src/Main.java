import com.room.booking.dao.UserDaoImpl;
import com.room.booking.model.User;

import java.sql.*;
import java.util.List;


public class Main {
    public static void main(String[] args) throws SQLException {


        UserDaoImpl userDao = new UserDaoImpl();

        final List<User> allUsers = userDao.getAllUsers();

        allUsers.forEach(user -> System.out.println(user.getFullName()));


//        String jdbcURL = "jdbc:mysql://localhost:3306/booking_system";
//        String dbUser = "root";
//        String dbPassword = "pwd";
//
//        String sql = "select * from users ";
//
//
//        // step 1
//        Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
//
//
//        Statement st = connection.createStatement();
//        final ResultSet rs = st.executeQuery(sql);
////       //System.out.println(rs.getString("email"));
////
//        while (rs.next()) {
//            int userId = rs.getInt("user_id");
//            String username = rs.getString("username");
//            String fullName = rs.getString("full_name");
//            String email = rs.getString("email");
//            System.out.println(email);
//        }
////
////        // Clean up
//        rs.close();
//        st.close();
//        connection.close();
    }
}