package controller;

import dao.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import main.Main;
import model.appointment;
import model.contact;
import model.customer;
import model.user;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.util.Optional;
import java.util.ResourceBundle;


/** This class controls the logic to add appointments in the application. */
public class AddAppointmentController implements Initializable {


    Stage stage;
    Parent scene;

    public Label AddApptLbl;
    public GridPane AddApptPane;
    public Label AddApptIDLbl;
    public Label AddApptTitleLbl;
    public Label AddApptDescriptionLbl;
    public Label ApptLocationLbl;
    public Label ContactApptLbl;
    public Label ApptTypeLbl;
    public Label ApptStartDateLbl;
    public TextField AddApptIDTxt;
    public Label AptStartTimeLbl;
    public Label AptEndDateLbl;
    public Label AptEndTimeLbl;
    public Label AptCustIDLbl;
    public Button AddApptSaveBtn;
    public Button AddApptCancelBtn;
    public TextField AddApptTitleTxt;
    public TextField AddApptDescriptionTxt;
    public TextField ApptLocationTxt;
    public ComboBox<contact> ApptContactCombo;
    public TextField ApptTypeTxt;
    public DatePicker AptStartDatePicker;
    public ComboBox<LocalTime> AptStartTimeCombo;
    public DatePicker AptEndDatePicker;
    public ComboBox<LocalTime> AptEndTimeCombo;
    public Label AptUserIDLbl;
    public ComboBox<customer> CustIDComboBox;
    public ComboBox<user> UserIDComboBox;


    @Override
/** Populates the start/end times, contact, customer ID, and User ID combo boxes with data from the database. */

    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ApptContactCombo.setItems(contactDatabase.getAllContacts());
            CustIDComboBox.setItems(customerDatabase.getAllCustomers());
            UserIDComboBox.setItems(userDatabase.getAllUsers());
        } catch (SQLException e) {
            e.printStackTrace();

        }
        LocalTime start = LocalTime.of(0, 0);
        LocalTime end = LocalTime.of(23, 0);
        while (start.isBefore(end.plusSeconds(1))) {
            AptStartTimeCombo.getItems().add(start);
            start = start.plusMinutes(10);
        }
        end = LocalTime.of(0, 0);
        while (end.isBefore(LocalTime.of(23, 0))) {
            AptEndTimeCombo.getItems().add(end);
            end = end.plusMinutes(10);
        }
        AptStartTimeCombo.getSelectionModel().select(LocalTime.of(8, 0));
        AptEndTimeCombo.getSelectionModel().select(LocalTime.of(9, 0));
    }
    /** Validates that the EST date/time is correctly translated to local time to be compared and allow the user to only enter appointments between ET business hours.
     * @param appointmentEnd
     * @param appointmentStart */
    public static boolean ValidateTimezone(LocalDateTime appointmentStart, LocalDateTime appointmentEnd) {
        boolean CorrectTimezone = true;

        ZoneId localZoneID = ZoneId.systemDefault();
        ZonedDateTime localStartDT = appointmentStart.atZone(localZoneID);
        ZonedDateTime localEndDT = appointmentEnd.atZone(localZoneID);
        ZonedDateTime estStartDT = localStartDT.withZoneSameInstant(ZoneId.of("America/New_York"));
        ZonedDateTime estEndDT = localEndDT.withZoneSameInstant(ZoneId.of("America/New_York"));

        LocalTime localStartTime = estStartDT.toLocalTime();
        LocalTime localEndTime = estEndDT.toLocalTime();

        if (localStartTime.isBefore(LocalTime.of(8, 0, 0 )) || localEndTime.isAfter(LocalTime.of(22, 0,0))) {
            CorrectTimezone = false;
        }

        return CorrectTimezone;
    }
    /** Validates that the user is not able to enter an appointment that overlaps with one already scheduled in the app.
     * @param customerID
     * @param appointmentEnd
     * @param appointmentStart */
    public static boolean ValidateAppointmentOverlap(int customerID, LocalDateTime appointmentStart, LocalDateTime appointmentEnd) throws SQLException {
        boolean NoAppointmentOverlap = true;

        ObservableList<appointment> appointmentOverlapCheckList = appointmentDatabase.getAllAppointments();
        for (appointment a : appointmentOverlapCheckList) {
            if (a.getCustomerID() == customerID) {
                if (appointmentStart.isEqual(a.getAppointmentStartDateTime()) || appointmentEnd.isEqual(a.getAppointmentEndDateTime()) || appointmentStart.isAfter(a.getAppointmentStartDateTime()) && appointmentStart.isBefore(a.getAppointmentEndDateTime())) {
                    NoAppointmentOverlap = false;
                }
            }

        } return NoAppointmentOverlap;
    }


    /**
     * Allows the user to save added appointment information.
     *
     * @param actionEvent button click
     */
    public void OnActionSaveAppointment(ActionEvent actionEvent) throws SQLException, IOException {

        try {
            String title = AddApptTitleTxt.getText();
            String description = AddApptDescriptionTxt.getText();
            String location = ApptLocationTxt.getText();
            String type = ApptTypeTxt.getText();
            int contactID = ApptContactCombo.getValue().getContactID();
            int customerID = CustIDComboBox.getValue().getCustomerID();
            int userID = UserIDComboBox.getValue().getUserID();

            LocalTime startTime = AptStartTimeCombo.getValue();
            LocalDate startDate = AptStartDatePicker.getValue();

            LocalTime endTime = AptEndTimeCombo.getValue();
            LocalDate endDate = AptEndDatePicker.getValue();

            LocalDateTime appointmentStart = LocalDateTime.of(startDate, startTime);
            LocalDateTime appointmentEnd = LocalDateTime.of(endDate, endTime);

            boolean CorrectTimezone = ValidateTimezone(appointmentStart, appointmentEnd);
            boolean CorrectAppointmentTime = ValidateAppointmentOverlap(customerID, appointmentStart, appointmentEnd);

            if (title.isEmpty() || description.isEmpty() || location.isEmpty() || type.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Error: No fields may be left blank");
                alert.show();
            } else if (CorrectTimezone == false) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Error: Appointments must be scheduled in accordance to our business hours between 8:00am and 10:00pm ET");
                alert.show();
            } else if (CorrectAppointmentTime == false) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Error: There is an overlapping appointment");
                alert.show();
            } else {
                appointmentDatabase.addAppointment(title, description, location, type, appointmentStart, appointmentEnd, customerID, userID, contactID);
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/home.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 1080, 720);
                stage.setTitle("Scheduling Application");
                stage.setScene(scene);
                stage.show();
            }
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Error: No fields may be left blank");
            alert.show();
        }
    }


    /**
     * Allows the user to cancel added appointment information and return to Main screen.
     *
     * @param actionEvent button click
     */
    public void OnActionCancelAppointment(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will cancel any changes. Please confirm to proceed.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/View/home.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1080, 720);
            stage.setScene(scene);
            stage.show();


        }
    }
}
