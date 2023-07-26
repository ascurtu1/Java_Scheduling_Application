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

public class appointmentDatabase {


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


    public static void deleteAppointment(int appointmentID) {
        try {
            String sqldelete = "DELETE FROM appointments WHERE Appointment_ID = ?";
            PreparedStatement deleteAppointment = JDBC.getConnection().prepareStatement(sqldelete);
            deleteAppointment.setInt(1, appointmentID);
            deleteAppointment.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}