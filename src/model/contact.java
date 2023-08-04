package model;

/** This creates the model contact class. */
public class contact {
    private int contactID;
    private String contactName;
    private String contactEmail;

    //to string needed to properly populate the combo boxes
    public String toString() {
        return (contactName);
    }

    /**Declaring constructor for the class. */
    public contact(int contactID, String contactName, String contactEmail) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }


    /**
     * @return contact's ID;
     */
    public int getContactID() {
        return contactID;
    }
    /**
     * @param contactID the contact's ID;
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }
    /**
     * @return contact Name;
     */
    public String getContactName() {
        return contactName;
    }
    /**
     * @param contactName the contact's name;
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
    /**
     * @return contact email;
     */
    public String getContactEmail() {
        return contactEmail;
    }
    /**
     * @param contactEmail the contact's email;
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
}


