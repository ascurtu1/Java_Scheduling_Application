package model;

import java.time.LocalDateTime;

/** This creates the model appointment class. */

public class appointment {

    private int appointmentID;
    private String appointmentTitle;
    private String appointmentDesc;
    private String appointmentLocation;
    private String appointmentType;
    private LocalDateTime appointmentStartDateTime;
    private LocalDateTime appointmentEndDateTime;
    private int customerID;
    private int userID;
    private int contactID;
    private String contact;
    private String aptMonth;
    private int aptCount;
    private String country;

    /** Declaring constructor for the class and creating overloaded constructors to be able to create different objects that hold limited data for reports. */

    public appointment(int appointmentID, String appointmentTitle, String appointmentDesc, String appointmentLocation, String appointmentType, LocalDateTime appointmentStartDateTime, LocalDateTime appointmentEndDateTime, int customerID, int userID, int contactID, String contact) {
        this.appointmentID = appointmentID;
        this.appointmentTitle = appointmentTitle;
        this.appointmentDesc = appointmentDesc;
        this.appointmentLocation = appointmentLocation;
        this.appointmentType = appointmentType;
        this.appointmentStartDateTime = appointmentStartDateTime;
        this.appointmentEndDateTime = appointmentEndDateTime;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
        this.contact = contact;
    }

    // constructor used for contacts schedule table in reports
    public appointment(int appointmentID, String appointmentTitle, String appointmentDesc, String appointmentType, LocalDateTime appointmentStartDateTime, LocalDateTime appointmentEndDateTime, int customerID) {
        this.appointmentID = appointmentID;
        this.appointmentTitle = appointmentTitle;
        this.appointmentDesc = appointmentDesc;
        this.appointmentType = appointmentType;
        this.appointmentStartDateTime = appointmentStartDateTime;
        this.appointmentEndDateTime = appointmentEndDateTime;
        this.customerID = customerID;

    }
    // constructor used for appointments by type and month table in reports
    public appointment(String appointmentType, String aptMonth, int aptCount) {
        this.appointmentType = appointmentType;
        this.aptMonth = aptMonth;
        this.aptCount = aptCount;
    }
    // constructor used for total appointments by country table in reports
    public appointment(String country, int aptCount) {
        this.country = country;
        this.aptCount = aptCount;
    }

    /**
     * @return country;
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set;
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return aptMonth;
     */
    public String getAptMonth() {
        return aptMonth;
    }

    /**
     * @param aptMonth the month of the appointment to set;
     */
    public void setAptMonth(String aptMonth) {
        this.aptMonth = aptMonth;
    }
    /**
     * @return aptCount;
     */
    public int getAptCount() {
        return aptCount;
    }

    /**
     * @param aptCount the count of appointments to set;
     */
    public void setAptCount(int aptCount) {
        this.aptCount = aptCount;
    }

    /**
     * @return appointmentID;
     */
    public int getAppointmentID() {
        return appointmentID;
    }
    /**
     * @param appointmentID the appointment ID to set
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }
    /**
     * @return appointment Title;
     */
    public String getAppointmentTitle() {
        return appointmentTitle;
    }
    /**
     * @param appointmentTitle the appointment Title to set
     */
    public void setAppointmentTitle(String appointmentTitle) {
        this.appointmentTitle = appointmentTitle;
    }
    /**
     * @return appointment Description;
     */
    public String getAppointmentDesc() {
        return appointmentDesc;
    }
    /**
     * @param appointmentDesc the appointment description to set
     */
    public void setAppointmentDesc(String appointmentDesc) {
        this.appointmentDesc = appointmentDesc;
    }
    /**
     * @return appointment Location;
     */
    public String getAppointmentLocation() {
        return appointmentLocation;
    }
    /**
     * @param appointmentLocation the appointment location to set
     */
    public void setAppointmentLocation(String appointmentLocation) {
        this.appointmentLocation = appointmentLocation;
    }
    /**
     * @return appointment Type;
     */
    public String getAppointmentType() {
        return appointmentType;
    }
    /**
     * @param appointmentType the appointment type to set
     */
    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }
    /**
     * @return start Time;
     */
    public LocalDateTime getAppointmentStartDateTime() {
        return appointmentStartDateTime;
    }
    /**
     * @param appointmentStartDateTime the appointment start information to set
     */
    public void setAppointmentStartDateTime(LocalDateTime appointmentStartDateTime) {
        this.appointmentStartDateTime = appointmentStartDateTime;
    }
    /**
     * @return End Time;
     */
    public LocalDateTime getAppointmentEndDateTime() {
        return appointmentEndDateTime;
    }
    /**
     * @param appointmentEndDateTime the appointment end information to set
     */
    public void setAppointmentEndDateTime(LocalDateTime appointmentEndDateTime) {
        this.appointmentEndDateTime = appointmentEndDateTime;
    }
    /**
     * @return Customer ID;
     */
    public int getCustomerID() {
        return customerID;
    }
    /**
     * @param customerID the customer ID to set
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
    /**
     * @return user ID;
     */
    public int getUserID() {
        return userID;
    }
    /**
     * @param userID the user ID to set
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }
    /**
     * @return Contact ID;
     */
    public int getContactID() {
        return contactID;
    }
    /**
     * @param contactID the contact ID to set
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }
    /**
     * @return Contact Name;
     */
    public String getContact() {
        return contact;
    }
    /**
     * @param contact the contact name to set
     */
    public void setContact(String contact) {
        this.contact = contact;
    }
}