package com.room.booking.dao;

import com.room.booking.model.Room;
import com.room.booking.model.User;
import com.room.booking.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//This class will only do DB operations related to USer Domain class
//db operation: CRUD operations
// reading from dB
//adding to DB
//deleting from DB
//Updating to DB
public class UserDaoImpl implements UserDao {

   private static final String sql = "select * from users ";

   private static final String GET_USER_BY_NAME_SQL =  "select * from users where username = ?";
    @Override
    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();
        final Connection connection = DBConnection.getConnection();
        Statement st = null;
        ResultSet rs = null;

        try {
            st = connection.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {

                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUserName(rs.getString("username"));
                user.setFullName(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                users.add(user);
            }
            rs.close();
            st.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    @Override
    public User getUserById(int id) {
        return null;
    }

    @Override
    public User getUserByName(String userName) {
        try {
            final PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(GET_USER_BY_NAME_SQL);
            preparedStatement.setString(1, userName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) { // try with resources
                if (resultSet.next()) {
                    int userId = resultSet.getInt("user_id");
                    String username = resultSet.getString("username");
                    String fullName = (resultSet.getString("full_name"));
                    String email = (resultSet.getString("email"));
                    return new User(userId, username, fullName, email);
                } else {
                    return null; // or throw an exception if the room is not found
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
