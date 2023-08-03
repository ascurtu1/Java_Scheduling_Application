package dao;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.user;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This class is used for SQL statements to access the user database table. */
public class userDatabase {

    /** This method creates a list of all users and their information.
     * @return userList list of all users and their information from the MySQL database. */
    public static ObservableList<user> getAllUsers () throws SQLException {
        ObservableList<user> userList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM users";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet Rs = ps.executeQuery();
            while (Rs.next()) {
                int userID = Rs.getInt("User_ID");
                String userName = Rs.getString("User_Name");
                String password = Rs.getString("Password");
                user newUser = new user(userID, userName, password);
                userList.add(newUser);
            }
            return userList;
        }
    }


