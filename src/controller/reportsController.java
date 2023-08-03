package controller;

import dao.appointmentDatabase;
import dao.contactDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.contact;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

import static controller.AddCustomerController.showAndWaitAlert;

/** This class holds the logic for the reports  form. */
public class reportsController implements Initializable {


    Stage stage;
    Parent scene;

    public Label ReportsLbl;
    public TableView TypeAndMonthTableview;
    public TableColumn AppointmentTypeColumn;
    public TableColumn AppointmentMonthColumn;
    public TableColumn AppointmentTotalColumn;
    public TableView ContactScheduleTable;
    public TableColumn ContactScheduleID;
    public TableColumn ContactScheduleAppointmentTitleColumn;
    public TableColumn ContactScheduleAppointmentTypeColumn;
    public TableColumn ContactScheduleAppointmentDescriptionColumn;
    public TableColumn ContactScheduleAppointmentStartColumn;
    public TableColumn ContactScheduleAppointmentEndColumn;
    public TableColumn ContactScheduleAppointmentCustomerColumn;
    public Label SelectContactLbl;
    public ComboBox <contact> SelectContactCombo;
    public TableView ApptByCountryTable;
    public TableColumn ApptbyCountryLbl;
    public TableColumn ApptbyCountryTotalLbl;
    public Button CancelBtn;


    /** Populates the contact combo box and the tables with information from the database.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//populating the contact combo box
        try {
            SelectContactCombo.setItems(contactDatabase.getAllContacts());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
//populating the contact schedule table
        ContactScheduleID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        ContactScheduleAppointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        ContactScheduleAppointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        ContactScheduleAppointmentDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentDesc"));
        ContactScheduleAppointmentStartColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentStartDateTime"));
        ContactScheduleAppointmentEndColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentEndDateTime"));
        ContactScheduleAppointmentCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));


//populating the appointment type and month table
        try {
            TypeAndMonthTableview.setItems(appointmentDatabase.getAppointmentsByTypeMonth());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        AppointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        AppointmentMonthColumn.setCellValueFactory(new PropertyValueFactory<>("aptMonth"));
        AppointmentTotalColumn.setCellValueFactory(new PropertyValueFactory<>("aptCount"));

//populating the appointment country and country table
        try {
            ApptByCountryTable.setItems(appointmentDatabase.getAppointmentByCountry());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        ApptbyCountryLbl.setCellValueFactory(new PropertyValueFactory<>("country"));
        ApptbyCountryTotalLbl.setCellValueFactory(new PropertyValueFactory<>("aptCount"));

    }

    /** Allows the user to choose contact from pre-populated combo box selection.
     * @param actionEvent selection */
    public void OnActionSelectContact(ActionEvent actionEvent) throws SQLException {
        String newSelectedContact = String.valueOf(SelectContactCombo.getSelectionModel().getSelectedItem());
        int contactID = contactDatabase.getContactID(newSelectedContact);
        if (appointmentDatabase.getAllAppointmentsByContactID(contactID).isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "There are no scheduled appointments for this contact");
            alert.show();
        }
            ContactScheduleTable.setItems(appointmentDatabase.getAllAppointmentsByContactID(contactID));
        }




    /** Allows the user to cancel any changes to the reports form and return to Home screen.
     * The alert is created by calling a lambda expression.
     * @param actionEvent selection */
    public void OnActionCancelReports(ActionEvent actionEvent) throws IOException {
        Optional<ButtonType> result = showAndWaitAlert.get();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/View/home.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1080, 720);
            stage.setScene(scene);
            stage.show();
        }
    }
}
