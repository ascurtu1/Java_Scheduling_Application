package model;


/** This creates the model customer class. */
public class customer {
    private String customerName;
    private int customerID;
    private String customerAddress;
    private String customerPostalCode;
    private String customerPhoneNumber;
    private int customerDivID;
    private String customerCountryName;
    private String customerDivisionName;

    /**Declaring constructor for the class. */
    public customer(String customerName, int customerID, String customerAddress, String customerPostalCode, String customerPhoneNumber, int customerDivID, String customerCountryName, String customerDivisionName) {

        this.customerName = customerName;
        this.customerID = customerID;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhoneNumber = customerPhoneNumber;
        this.customerDivID = customerDivID;
        this.customerCountryName = customerCountryName;
        this.customerDivisionName = customerDivisionName;
    }


    public String toString() {
        return "[" + customerID + "] " + customerName;
    }


    /**
     * @return customer's name;
     */
    public String getCustomerName() {
        return customerName;
    }
    /**
     * @param customerName the customer's name;
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    /**
     * @return customer's ID;
     */
    public int getCustomerID() {
        return customerID;
    }
    /**
     * @param customerID the customer's ID;
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
    /**
     * @return customer's address;
     */
    public String getCustomerAddress() {
        return customerAddress;
    }
    /**
     * @param customerAddress the customer's address;
     */
    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }
    /**
     * @return customer's postal;
     */
    public String getCustomerPostalCode() {
        return customerPostalCode;
    }
    /**
     * @param customerPostalCode the customer's postal code;
     */
    public void setCustomerPostalCode(String customerPostalCode) {
        this.customerPostalCode = customerPostalCode;
    }
    /**
     * @return customer's phone number;
     */
    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }
    /**
     * @param customerPhoneNumber the customer's phone number;
     */
    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }
    /**
     * @return customer's division state ID;
     */
    public int getCustomerDivID() {
        return customerDivID;
    }
    /**
     * @param customerDivID the customer's division state ID;
     */
    public void setCustomerDivID(int customerDivID) {
        this.customerDivID = customerDivID;
    }
    /**
     * @return customer's country name;
     */
    public String getCustomerCountryName() {
        return customerCountryName;
    }
    /**
     * @param customerCountryName the customer's country name;
     */
    public void setCustomerCountryName(String customerCountryName) {
        this.customerCountryName = customerCountryName;
    }
    /**
     * @return customer's division state name;
     */
    public String getCustomerDivisionName() {
        return customerDivisionName;
    }
    /**
     * @param customerDivisionName the customer's division state name;
     */
    public void setCustomerDivisionName(String customerDivisionName) {
        this.customerDivisionName = customerDivisionName;
    }

}

