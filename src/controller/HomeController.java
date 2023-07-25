package controller;

import helper.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** This class controls the logic for the application's home screen. */
public class HomeController implements Initializable {


    Stage stage;
    Parent scene;

    public Label SchedulingSystem_Lbl;
    public Label Customers_Lbl;
    public Label Appointments_Lbl;
    public Button Reports_Button;
    public TableView CustomersTableView;
    public TableColumn CustomersIDColumn;
    public TableColumn CustomersNameColumn;
    public TableColumn CustomersAddressColumn;
    public TableColumn CustomersPostalColumn;
    public TableColumn CustomersPhoneColumn;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
    public void OnActionModifyCustomer(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/modifyCustomer.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 720);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Allows the user to delete customer if appropriate.
     *
     * @param actionEvent button click
     */
    public void OnActionDeleteCustomer(ActionEvent actionEvent) {
    }

    /**
     * Allows the user to exit the application.
     *
     * @param actionEvent button click
     */
    public void OnActionExit(ActionEvent actionEvent) {
        JDBC.closeConnection();
        System.exit(0);
    }

    /**
     * Allows the user to delete the appointment if appropriate.
     *
     * @param actionEvent button click
     */
    public void OnActionDeleteAppointment(ActionEvent actionEvent) {
    }

    /**
     * Allows the user to go to Modify Appointment screen.
     *
     * @param actionEvent button click
     */
    public void OnActionModifyAppointment(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/modifyAppointment.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 720);
        stage.setScene(scene);
        stage.show();
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
