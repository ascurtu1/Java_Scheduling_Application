package model;

/** This creates the user class which is used to login to the application. */
public class user {
    private int userID;
    private String userName;
    private String password;

/**Declaring constructor for the class*/
    public user (int userID, String userName, String password) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
    }

    public String toString() {
        return "[" + userID + "] " + userName;
    }

    /**
     * @return userID;
     */
    public int getUserID () {
        return userID;
    }
    /**
     * @param userID the userID to set
     */
    public void setUserID ( int userID){
        this.userID = userID;
    }
    /**
     * @return userName;
     */
    public String getUserName () {
        return userName;
    }
    /**
     * @param userName the userName to set
     */
    public void setUserName (String userName){
        this.userName = userName;
    }
    /**
     * @return password;
     */
    public String getPassword () {
        return password;
    }
    /**
     * @param password the user password to set
     */
    public void setPassword (String password){
        this.password = password;
    }
}

