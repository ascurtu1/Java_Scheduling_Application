package dao;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.appointment;
import model.customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;


/** This class is used for SQL statements to access the appointments database table. */
public class appointmentDatabase {

    /**
     * Gets all appointment information from the database.
     *
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

    /**
     * Deletes the appointment information from the database identified from the selected appointment ID.
     *
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

    /**
     * Checks if there is an associated appointment with the customer by matching customer ID on both tables.
     *
     * @param customerID
     */

    public static boolean checkAssociatedAppointment(int customerID) throws SQLException {
        ObservableList<Integer> ListCustomerID = FXCollections.observableArrayList();
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

        } catch (SQLException e){
                e.printStackTrace();
            }
        return false;
    }
}


