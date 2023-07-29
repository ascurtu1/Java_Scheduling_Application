package dao;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.division;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This class is used for SQL statements to access the divisions database table. */
public class divisionDatabase {

    public static ObservableList<division> getAllDivisions() throws SQLException {

        ObservableList<division> divisionList = FXCollections.observableArrayList();
        String sql = "SELECT Division_ID, Division, Country_ID FROM first_level_divisions";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet Rs = ps.executeQuery();
        while (Rs.next()) {
            int divisionID = Rs.getInt("Division_ID");
            String division = Rs.getString("Division");
            int countryID = Rs.getInt("Country_ID");
            division newDivision = new division(divisionID, division, countryID);
            divisionList.add(newDivision);
        }
        return divisionList;

    }


    public static ObservableList<division> getUSDivisions() throws SQLException {
        ObservableList<division> USDivisionList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = 1";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet Rs = ps.executeQuery();
        while (Rs.next()) {
            int divisionID = Rs.getInt("Division_ID");
            String division = Rs.getString("Division");
            int countryID = Rs.getInt("Country_ID");
            division newDivision2 = new division(divisionID, division, countryID);
            USDivisionList.add(newDivision2);
        }
        return USDivisionList;
    }


    public static ObservableList<division> getUKDivisions() throws SQLException {
        ObservableList<division> UKDivisionList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = 2";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet Rs = ps.executeQuery();
        while (Rs.next()) {
            int divisionID = Rs.getInt("Division_ID");
            String division = Rs.getString("Division");
            int countryID = Rs.getInt("Country_ID");
            division newDivision3 = new division(divisionID, division, countryID);
            UKDivisionList.add(newDivision3);
        }
        return UKDivisionList;
    }

    public static ObservableList<division> getCanadaDivision() throws SQLException {
        ObservableList<division> CanadaDivisionList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = 3";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet Rs = ps.executeQuery();
        while (Rs.next()) {
            int divisionID = Rs.getInt("Division_ID");
            String division = Rs.getString("Division");
            int countryID = Rs.getInt("Country_ID");
            division newDivision3 = new division(divisionID, division, countryID);
            CanadaDivisionList.add(newDivision3);
        }
        return CanadaDivisionList;
    }
}
