package dao;


import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.contact;
import model.country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This class is used for SQL statements to access the contacts database table. */
public class contactDatabase {
    public static ObservableList<contact> getAllContacts() throws SQLException {
        ObservableList<contact> contactList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM contacts";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet Rs = ps.executeQuery();
        while (Rs.next()) {
            int contactID = Rs.getInt("Contact_ID");
            String contactName = Rs.getString("Contact_Name");
            String contactEmail = Rs.getString("Email");
            contact newContact = new contact(contactID, contactName, contactEmail);
            contactList.add(newContact);
        }
        return contactList;

    }

}
