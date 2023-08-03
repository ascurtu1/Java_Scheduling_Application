package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This creates the model country class. */
public class country {

    private int countryID;
    private String countryName;


    /**Declaring constructor for the class. */
    public country(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = countryName;
    }

    public String toString() {
        return (countryName);
    }



    /**
     * @return country's ID;
     */
    public int getCountryID() {
        return countryID;
    }
    /**
     * @param countryID the country's ID;
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }
    /**
     * @return country's name;
     */
    public String getCountryName() {
        return countryName;
    }
    /**
     * @param countryName the country's name;
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
