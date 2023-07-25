package model;

/** This creates the country class which is used to hold country information. */
public class country {

    private int countryID;
    private String countryName;

    /**Declaring constructor for the class*/
    public country(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = countryName;
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
