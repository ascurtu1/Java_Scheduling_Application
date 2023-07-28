package model;

/** This creates the model division (state) class. */
public class division {

    private int divisionID;
    private String divisionName;
    private int countryID;


    public String toString() {
        return (divisionName);
    }

    /**
     * @return division's ID;
     */
    public int getDivisionID() {
        return divisionID;
    }
    /**
     * @param divisionID the division ID;
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }
    /**
     * @return division's name;
     */
    public String getDivisionName() {
        return divisionName;
    }
    /**
     * @param divisionName the division name;
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }
    /**
     * @return country's ID;
     */
    public int getCountryID() {
        return countryID;
    }
    /**
     * @param countryID the country ID;
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /** Declaring constructor for the class. */
    public division(int divisionID, String divisionName, int countryID) {
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.countryID = countryID;
    }
}
