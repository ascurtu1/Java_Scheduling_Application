package dao;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This class is used for SQL statements to access the countries database table. */

public class countryDatabase {


    /** This method creates a list of all countries and their information.
     * @return country List list of all countries and their information from the MySQL database. */
    public static ObservableList<country> getAllCountries() throws SQLException {
            ObservableList<country> countryList = FXCollections.observableArrayList();
            String sql = "SELECT Country_ID, Country FROM countries";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet Rs = ps.executeQuery();
                while (Rs.next()) {
                    int countryID = Rs.getInt("Country_ID");
                    String countryName = Rs.getString("Country");
                    country newCountry = new country(countryID, countryName);
                    countryList.add(newCountry);
                }
            return countryList;

        }

    }

