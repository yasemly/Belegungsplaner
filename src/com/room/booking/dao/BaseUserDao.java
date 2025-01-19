//package com.room.booking.dao;
//
//import com.room.booking.model.BaseUser;
//import java.util.List;
//
///**
// * DAO-Interface für alle Benutzeroperationen
// */
//public interface BaseUserDao {
//
//    BaseUser getUserByUsernameAndPassword(String username, String password);
//
//    List<BaseUser> getAllUsers();
//
//    BaseUser getUserById(int userId);
//
//    BaseUser createBaseUser(BaseUser user);
//
//    void updateUser(BaseUser user);
//
//    void deleteUser(int userId);
//
//    boolean deleteUserByUsername(String username);
//
//    void registerUser(String username, String fullName, String email, String password);
//
//    void registerEmployer(String username, String fullName, String email, String password, String department);
//}
