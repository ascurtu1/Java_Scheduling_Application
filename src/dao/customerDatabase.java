package dao;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.customer;
import model.user;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/** This class is used for SQL statements to access the customers database table. */
public class customerDatabase {


    /** This method creates a list of all customers and their information.
     * @return customerList of all customers and their information from the MySQL database. */
    public static ObservableList<customer> getAllCustomers() throws SQLException {
        ObservableList<customer> customerList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Postal_Code, customers.Phone, customers.Division_ID, first_level_divisions.Division, countries.Country FROM customers INNER JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID INNER JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet Rs = ps.executeQuery();
            while (Rs.next()) {
                int customerID = Rs.getInt("Customer_ID");
                String customerName = Rs.getString("Customer_Name");
                String customerAddress = Rs.getString("Address");
                String customerPostalCode = Rs.getString("Postal_Code");
                String phone = Rs.getString("Phone");
                int customerDivID = Rs.getInt("Division_ID");
                String customerCountry = Rs.getString("Country");
                String customerDivision = Rs.getString("Division");
                customer newCustomer = new customer(customerName, customerID, customerAddress, customerPostalCode, phone, customerDivID, customerCountry, customerDivision);
                customerList.add(newCustomer);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return customerList;

    }

    /** This method deletes customers from the database based on their selected ID.
     * @param customerID the ID. */
    public static void deleteCustomer(int customerID) {
        try {
            String sqldeletec = "DELETE FROM customers WHERE Customer_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sqldeletec);
            ps.setInt(1, customerID);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
