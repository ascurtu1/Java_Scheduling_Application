package dao;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.appointment;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;



/** This class is used for SQL statements to access the appointments database table. */
public class appointmentDatabase {


    /** Gets all appointment information from the database.
     * @return appointmentList the list of all appointment information.
     */
    public static ObservableList<appointment> getAllAppointments() throws SQLException {
        ObservableList<appointment> appointmentList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT appointments.Appointment_ID, appointments.Title, appointments.Description, appointments.Location, appointments.Type, appointments.Start, appointments.End, appointments.Customer_ID, appointments.User_ID, appointments.Contact_ID, contacts.Contact_Name from appointments JOIN contacts on appointments.Contact_ID = contacts.Contact_ID";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet Rs = ps.executeQuery();
            while (Rs.next()) {
                int appointmentID = Rs.getInt("Appointment_ID");
                String appointmentTitle = Rs.getString("Title");
                String Description = Rs.getString("Description");
                String Location = Rs.getString("Location");
                String appointmentType = Rs.getString("Type");
                LocalDateTime appointmentStart = Rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime appointmentEnd = Rs.getTimestamp("End").toLocalDateTime();
                int customerID = Rs.getInt("Customer_ID");
                int userID = Rs.getInt("User_ID");
                int contactID = Rs.getInt("Contact_ID");
                String contactName = Rs.getString("Contact_Name");

                appointment newAppointment = new appointment(appointmentID, appointmentTitle, Description, Location, appointmentType, appointmentStart, appointmentEnd, customerID, userID, contactID, contactName);
                appointmentList.add(newAppointment);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return appointmentList;

    }

    /** Deletes the appointment information from the database identified from the selected appointment ID.
     * @param appointmentID
     */
    public static void deleteAppointment(int appointmentID) {
        try {
            String sqldelete = "DELETE FROM appointments WHERE Appointment_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sqldelete);
            ps.setInt(1, appointmentID);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Checks if there is an associated appointment with the customer by matching customer ID on both tables.
     * @param customerID
     */
    public static boolean checkAssociatedAppointment(int customerID) throws SQLException {
        try {
            String IDquery = "SELECT Customer_ID FROM appointments";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(IDquery);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int newCustomerID = rs.getInt("Customer_ID");
                if (newCustomerID == customerID) {
                    return true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    /** This method adds appointment information to the database.
     * @param title
     * @param description
     * @param location
     * @param type
     * @param appointmentStart
     * @param appointmentEnd
     * @param customerID
     * @param userID
     * @param contactID
     */

    public static void addAppointment(String title, String description, String location, String type, LocalDateTime appointmentStart, LocalDateTime appointmentEnd, int customerID, int userID, int contactID) throws SQLException {

        String sql = "INSERT INTO APPOINTMENTS (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement psti = JDBC.getConnection().prepareStatement(sql);
        psti.setString(1, title);
        psti.setString(2, description);
        psti.setString(3, location);
        psti.setString(4, type);
        psti.setTimestamp(5, Timestamp.valueOf(appointmentStart));
        psti.setTimestamp(6, Timestamp.valueOf(appointmentEnd));
        psti.setInt(7, customerID);
        psti.setInt(8, userID);
        psti.setInt(9, contactID);
        psti.execute();
    }

    /** This method creates a list of all appointment information related to each contact ID. This is used in the reports controller.
     * @param ContactID the contact's ID.
     * @return appointmentListByContactID.
     */
    public static ObservableList<appointment> getAllAppointmentsByContactID(int ContactID) throws SQLException {

        ObservableList<appointment> appointmentListByContactID = FXCollections.observableArrayList();
        try {
            String sql = "SELECT appointments.Appointment_ID, appointments.Title, appointments.Type, appointments.Description, appointments.Start, appointments.End, appointments.Customer_ID, appointments.Contact_ID FROM appointments JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID WHERE appointments.Contact_ID  = '" + ContactID + "';";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet Rs = ps.executeQuery();
            while (Rs.next()) {
                int appointmentID = Rs.getInt("Appointment_ID");
                String appointmentTitle = Rs.getString("Title");
                String appointmentType = Rs.getString("Type");
                String appointmentDesc = Rs.getString("Description");
                LocalDateTime appointmentStartDateTime = Rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime appointmentEndDateTime = Rs.getTimestamp("End").toLocalDateTime();
                int customerID = Rs.getInt("Customer_ID");


                appointment newAppointment = new appointment(appointmentID, appointmentTitle, appointmentDesc, appointmentType, appointmentStartDateTime, appointmentEndDateTime, customerID);
                appointmentListByContactID.add(newAppointment);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return appointmentListByContactID;

    }


    /** This method creates a list of all appointment types by month. This is used in the reports controller.
     * @return appointmentListByMonth.
     */
    public static ObservableList<appointment> getAppointmentsByTypeMonth() throws SQLException {

        ObservableList<appointment> appointmentListByTypeMonth = FXCollections.observableArrayList();
        try {
            String sql = "SELECT appointments.Type, DATE_FORMAT(Start, '%M') AS StartMonth, COUNT(*) FROM appointments GROUP BY StartMonth, type;";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet Rs = ps.executeQuery();
            while (Rs.next()) {
                String appointmentType = Rs.getString("Type");
                String aptMonth = Rs.getString("StartMonth");
                int aptCount = Rs.getInt("COUNT(*)");

                appointment typeMonthAppointment = new appointment(appointmentType, aptMonth, aptCount);
                appointmentListByTypeMonth.add(typeMonthAppointment);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return appointmentListByTypeMonth;
    }

    /** This method creates a list of all appointments and their totals by country. This is used in the reports controller.
     * @return appointmentListByCountry.
     */

    public static ObservableList getAppointmentByCountry() throws SQLException {
        ObservableList<appointment> appointmentListByCountry = FXCollections.observableArrayList();
        try {
            String sql = "SELECT COUNT(*) AS appointment_count, countries.country FROM appointments JOIN customers ON appointments.Customer_ID = customers.Customer_ID JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID GROUP BY countries.country";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet Rs = ps.executeQuery();
            while (Rs.next()) {
                String country = Rs.getString("country");
                int aptCount = Rs.getInt("appointment_count");
                appointment appointmentByCountry = new appointment(country, aptCount);
                appointmentListByCountry.add(appointmentByCountry);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return appointmentListByCountry;

    }
    /** This method creates a list of all appointments and filters them by month. This is used in the Home controller.
     * @return appointmentByMonthList.
     */
    public static ObservableList getAppointmentByMonth() {
        ObservableList<appointment> appointmentByMonthList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT appointments.Appointment_ID, appointments.Title, appointments.Description, appointments.Location, appointments.Type, appointments.Start, appointments.End, appointments.Customer_ID, appointments.User_ID, appointments.Contact_ID, contacts.Contact_Name from appointments JOIN contacts on appointments.Contact_ID = contacts.Contact_ID WHERE MONTH(appointments.Start) = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setObject(1, LocalDate.now().getMonthValue());
            ResultSet Rs = ps.executeQuery();
            while (Rs.next()) {
                int appointmentID = Rs.getInt("Appointment_ID");
                String appointmentTitle = Rs.getString("Title");
                String Description = Rs.getString("Description");
                String Location = Rs.getString("Location");
                String appointmentType = Rs.getString("Type");
                LocalDateTime appointmentStart = Rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime appointmentEnd = Rs.getTimestamp("End").toLocalDateTime();
                int customerID = Rs.getInt("Customer_ID");
                int userID = Rs.getInt("User_ID");
                int contactID = Rs.getInt("Contact_ID");
                String contactName = Rs.getString("Contact_Name");

                appointment AppointmentByMonth = new appointment(appointmentID, appointmentTitle, Description, Location, appointmentType, appointmentStart, appointmentEnd, customerID, userID, contactID, contactName);
                appointmentByMonthList.add(AppointmentByMonth);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return appointmentByMonthList;

    }

    /** This method creates a list of all appointments and filters them by week. This is used in the Home controller.
     * @return appointmentByWeekList. */

    public static ObservableList getAppointmentByWeek() {
        ObservableList<appointment> appointmentByWeekList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT appointments.Appointment_ID, appointments.Title, appointments.Description, appointments.Location, appointments.Type, appointments.Start, appointments.End, appointments.Customer_ID, appointments.User_ID, appointments.Contact_ID, contacts.Contact_Name from appointments JOIN contacts on appointments.Contact_ID = contacts.Contact_ID WHERE WEEK(appointments.Start) = WEEK(NOW())";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet Rs = ps.executeQuery();
            while (Rs.next()) {
                int appointmentID = Rs.getInt("Appointment_ID");
                String appointmentTitle = Rs.getString("Title");
                String Description = Rs.getString("Description");
                String Location = Rs.getString("Location");
                String appointmentType = Rs.getString("Type");
                LocalDateTime appointmentStart = Rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime appointmentEnd = Rs.getTimestamp("End").toLocalDateTime();
                int customerID = Rs.getInt("Customer_ID");
                int userID = Rs.getInt("User_ID");
                int contactID = Rs.getInt("Contact_ID");
                String contactName = Rs.getString("Contact_Name");

                appointment AppointmentByWeek = new appointment(appointmentID, appointmentTitle, Description, Location, appointmentType, appointmentStart, appointmentEnd, customerID, userID, contactID, contactName);
                appointmentByWeekList.add(AppointmentByWeek);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return appointmentByWeekList;

    }

    /** This method updates the appointment list in the database.
     * @param appointmentID
     * @param appointmentTitle
     * @param Description
     * @param Location
     * @param appointmentType
     * @param appointmentStart
     * @param appointmentEnd
     * @param customerID
     * @param userID
     * @param contactID
     *
     * */
    public static void updateAppointment (int appointmentID, String appointmentTitle, String Description, String Location, String appointmentType, LocalDateTime appointmentStart, LocalDateTime appointmentEnd, int customerID, int userID, int contactID) throws SQLException {
        try {
            String sql = "UPDATE APPOINTMENTS set Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE APPOINTMENT_ID = ?";
            PreparedStatement pst = JDBC.getConnection().prepareStatement(sql);
            pst.setString(1, appointmentTitle);
            pst.setString(2, Description);
            pst.setString(3, Location);
            pst.setString(4, appointmentType);
            pst.setTimestamp(5, Timestamp.valueOf(appointmentStart));
            pst.setTimestamp(6, Timestamp.valueOf(appointmentEnd));
            pst.setInt(7, customerID);
            pst.setInt(8, userID);
            pst.setInt(9, contactID);
            pst.setInt(10, appointmentID);

            pst.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



