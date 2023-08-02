package controller;

import dao.appointmentDatabase;
import dao.customerDatabase;
import helper.JDBC;
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
import model.appointment;
import model.customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Optional;
import java.util.ResourceBundle;


import static dao.appointmentDatabase.getAllAppointments;
import static dao.customerDatabase.getAllCustomers;

/** This class controls the logic for the application's home screen. */
public class HomeController implements Initializable {


    Stage stage;
    Parent scene;

    public TableColumn CustomersStateColumn;
    public Label SchedulingSystem_Lbl;
    public Label Customers_Lbl;
    public Label Appointments_Lbl;
    public Button Reports_Button;
    public TableColumn CustomerPhoneColumn;
    public TableView CustomersTableView;
    public TableColumn CustomersIDColumn;
    public TableColumn CustomersNameColumn;
    public TableColumn CustomersAddressColumn;
    public TableColumn CustomersPostalColumn;
    public TableColumn CustomersDivColumn;
    public TableView AppointmentsTableView;
    public TableColumn ApptIDColumn;
    public TableColumn ApptTitleColumn;
    public TableColumn ApptDescColumn;
    public TableColumn ApptLocationColumn;
    public TableColumn ApptContactColumn;
    public TableColumn ApptTypeColumn;
    public TableColumn ApptStartColumn;
    public TableColumn ApptEndColumn;
    public TableColumn ApptCustIDColumn;
    public TableColumn ApptUserIDColumn;
    public Button CustomerAdd;
    public Button CustomerModify;
    public Button CustomerDelete;
    public Button ExitApplication;
    public Button AppointmentDelete;
    public Button AppointmentModify;
    public Button AppointmentAdd;
    public RadioButton AppointmentsViewAllRadio;
    public RadioButton AppointmentsViewMonthRadio;
    public RadioButton AppointmentsViewWeekRadio;
    public appointment appointmentToDelete;
    public customer customerToDelete;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Insert customer information pulled from database into Customers table.
        try {
            CustomersTableView.setItems(getAllCustomers());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        CustomersIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        CustomersNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        CustomersAddressColumn.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        CustomersPostalColumn.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
        CustomerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("customerPhoneNumber"));
        CustomersDivColumn.setCellValueFactory(new PropertyValueFactory<>("customerDivID"));
        CustomersDivColumn.setCellValueFactory(new PropertyValueFactory<>("customerDivID"));
        CustomersStateColumn.setCellValueFactory(new PropertyValueFactory<>("customerDivisionName"));


        //Insert appointment information pulled from database into Appointments table.
        try {
            AppointmentsTableView.setItems(getAllAppointments());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        ApptIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        ApptTitleColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        ApptDescColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentDesc"));
        ApptLocationColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        ApptContactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        ApptTypeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        ApptStartColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentStartDateTime"));
        ApptEndColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentEndDateTime"));
        ApptCustIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        ApptUserIDColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));


    }

    /**
     * Allows the user to go to Reports screen.
     *
     * @param actionEvent button click
     */
    public void OnActionGoToReports(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/reports.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1080, 720);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Allows the user to go to Add Customer screen.
     *
     * @param actionEvent button click
     */
    public void OnActionAddCustomer(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/addCustomer.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 720);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Allows the user to go to Modify Customer screen.
     *
     * @param actionEvent button click
     */
    public void OnActionModifyCustomer(ActionEvent actionEvent) throws IOException, SQLException {
        customer customerToModify = (customer) CustomersTableView.getSelectionModel().getSelectedItem();
        if (customerToModify == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("ERROR: No Customer Selected");
            alert.show();
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/modifyCustomer.fxml"));
            loader.load();
            ModifyCustomerController customerModify = loader.getController();
            customerModify.populateCustomer((customer) CustomersTableView.getSelectionModel().getSelectedItem());
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * Allows the user to delete customer if they do not have an appointment.
     *
     * @param actionEvent button click
     */
    public void OnActionDeleteCustomer(ActionEvent actionEvent) throws SQLException {
        customerToDelete = (customer) CustomersTableView.getSelectionModel().getSelectedItem();
        int customerWAppointmentID = ((customer) CustomersTableView.getSelectionModel().getSelectedItem()).getCustomerID();
        if (customerToDelete == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("ERROR: No Customer Selected");
            alert.show();
        } else if (appointmentDatabase.checkAssociatedAppointment(customerWAppointmentID)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("ERROR: Unable to delete customer that has an appointment");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("WARNING: Please confirm you would like to delete");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get()== ButtonType.OK) {
                customerDatabase.deleteCustomer(customerWAppointmentID);
                CustomersTableView.setItems(getAllCustomers());

            }
        }
    }

    /**
     * Allows the user to exit the application.
     * @param actionEvent button click
     */
    public void OnActionExit (ActionEvent actionEvent){
        JDBC.closeConnection();
        System.exit(0);
    }

    /**
     * Allows the user to delete the appointment if appropriate.
     *
     * @param actionEvent button click
     */
    public void OnActionDeleteAppointment (ActionEvent actionEvent) throws SQLException, IOException {
        appointmentToDelete = (appointment) AppointmentsTableView.getSelectionModel().getSelectedItem();
        if (appointmentToDelete == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("ERROR: No Appointment Selected");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("WARNING: Please confirm you would like to delete");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                appointmentDatabase.deleteAppointment(appointmentToDelete.getAppointmentID());
                AppointmentsTableView.setItems(getAllAppointments());
            }
            Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Appointment ID: " + appointmentToDelete.getAppointmentID() + " with type: " + appointmentToDelete.getAppointmentType() + " was deleted.");
            alert.show();
        }
    }

    /**
     * Allows the user to go to Modify Appointment screen.
     *
     * @param actionEvent button click
     */
    public void OnActionModifyAppointment (ActionEvent actionEvent) throws IOException, SQLException {
        appointment appointmentToModify = (appointment) AppointmentsTableView.getSelectionModel().getSelectedItem();
        if (appointmentToModify == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("ERROR: No Appointment Selected");
            alert.show();
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/modifyAppointment.fxml"));
            loader.load();
            ModifyAppointmentController apptModify = loader.getController();
            apptModify.populateAppointment((appointment) AppointmentsTableView.getSelectionModel().getSelectedItem());
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /** Allows the user to go to Add Appointment screen.
     * @param actionEvent button click */
    public void OnActionAddAppointment (ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/addAppointment.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 720);
        stage.setScene(scene);
        stage.show();
    }

    /** Allows the user to view all appointments.
     * @param actionEvent button click */
    public void OnActionViewAll (ActionEvent actionEvent){
    }
    /** Allows the user to view appointments this month.
     * @param actionEvent button click */
    public void OnActionViewMonth (ActionEvent actionEvent){
    }
    /** Allows the user to view appointments this week.
     * @param actionEvent button click */
    public void OnActionViewWeek (ActionEvent actionEvent){
    }
}