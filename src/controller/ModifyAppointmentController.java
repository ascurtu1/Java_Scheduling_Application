package controller;

import dao.appointmentDatabase;
import dao.contactDatabase;
import dao.customerDatabase;
import dao.userDatabase;
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
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class controls the logic to modify appointments in the application. */
public class ModifyAppointmentController implements Initializable {


    Stage stage;
    Parent scene;

    public Label ModifyApptLbl;
    public GridPane ModifyApptPane;
    public Label ModifyIDLbl;
    public Label ModifyTitleLbl;
    public Label ModifyDescriptionLbl;
    public Label ModifyLocationLbl;
    public Label ModifyContactLbl;
    public Label ModifyTypeLbl;
    public Label ModifyStartDateLbl;
    public TextField ModifyAppointmentTxt;
    public TextField ModifyTitleTxt;
    public TextField ModifyDescriptionTxt;
    public TextField ModifyLocationTxt;
    public ComboBox<contact> ModifyContactCombo;
    public TextField ModifyTypeTxt;
    public DatePicker ModifyStartDatePicker;
    public Label ModifyStartTimeLbl;
    public ComboBox<LocalTime> ModifyStartTimeCombo;
    public Label ModifyEndDateLbl;
    public DatePicker ModifyEndDatePicker;
    public Label ModifyEndTimeLbl;
    public ComboBox<LocalTime> ModifyEndTimeCombo;
    public Label ModifyCustIDLbl;
    public Label ModifyUserIDLbl;
    public Button SaveModifyAppointmentBtn;
    public Button CancelModifyAppointmentBtn;
    public ComboBox<customer> CustomerIDComboBox;
    public ComboBox<user> UserIDComboBox;
    private customer newCustomer;
    private user newUser;
    private contact newContact;


    /** Populates the start/end times, contact, customer ID, and User ID combo boxes with data from the database. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ModifyContactCombo.setItems(contactDatabase.getAllContacts());
            CustomerIDComboBox.setItems(customerDatabase.getAllCustomers());
            UserIDComboBox.setItems(userDatabase.getAllUsers());
        } catch (SQLException e) {
            e.printStackTrace();

        }

        LocalTime start = LocalTime.of(0, 0);
        LocalTime end = LocalTime.of(23, 0);
        while (start.isBefore(end.plusSeconds(1))) {
            ModifyStartTimeCombo.getItems().add(start);
            start = start.plusMinutes(10);
        }
        end = LocalTime.of(0, 0);
        while (end.isBefore(LocalTime.of(23, 0))) {
            ModifyEndTimeCombo.getItems().add(end);
            end = end.plusMinutes(10);
        }
        ModifyStartTimeCombo.getSelectionModel().select(LocalTime.of(8, 0));
        ModifyEndTimeCombo.getSelectionModel().select(LocalTime.of(9, 0));
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
     * Allows the user to save the modified appointment form.
     *
     * @param actionEvent selection
     */
    public void OnActionSaveModifyAppointment(ActionEvent actionEvent) {

        try {
            String title = ModifyTitleTxt.getText();
            String description = ModifyDescriptionTxt.getText();
            String location = ModifyLocationTxt.getText();
            String type = ModifyTypeTxt.getText();
            int contactID = ModifyContactCombo.getValue().getContactID();
            int customerID = CustomerIDComboBox.getValue().getCustomerID();
            int userID = UserIDComboBox.getValue().getUserID();

            LocalTime startTime = ModifyStartTimeCombo.getValue();
            LocalDate startDate = ModifyStartDatePicker.getValue();

            LocalTime endTime = ModifyEndTimeCombo.getValue();
            LocalDate endDate = ModifyEndDatePicker.getValue();

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
        } catch (NullPointerException | SQLException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Error: No fields may be left blank");
            alert.show();
        }

    }

    /**
     * Allows the user to cancel any changes to the modified appointment form and return to Home screen.
     *
     * @param actionEvent selection
     */
    public void OnActionCancelModifyAppointment(ActionEvent actionEvent) throws IOException {
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

    public void populateAppointment(appointment appointment) throws SQLException {
        ModifyAppointmentTxt.setText(String.valueOf(appointment.getAppointmentID()));
        ModifyTitleTxt.setText(appointment.getAppointmentTitle());
        ModifyDescriptionTxt.setText(appointment.getAppointmentDesc());
        ModifyLocationTxt.setText(appointment.getAppointmentLocation());
        ModifyTypeTxt.setText(appointment.getAppointmentType());
        ModifyStartDatePicker.setValue(appointment.getAppointmentStartDateTime().toLocalDate());
        ModifyStartTimeCombo.setValue(appointment.getAppointmentStartDateTime().toLocalTime());
        ModifyEndDatePicker.setValue(appointment.getAppointmentEndDateTime().toLocalDate());
        ModifyEndTimeCombo.setValue(appointment.getAppointmentEndDateTime().toLocalTime());

        ObservableList<customer> CustomersToPopulate = customerDatabase.getAllCustomers();
        for (customer customer : CustomersToPopulate) {
            if (appointment.getCustomerID() == customer.getCustomerID()) {
                this.newCustomer = customer;
                CustomerIDComboBox.setValue(newCustomer);
            }

        }
        ObservableList<user> UsersToPopulate = userDatabase.getAllUsers();
        for (user u : UsersToPopulate) {
            if (appointment.getUserID() == u.getUserID()) {
                this.newUser = u;
                UserIDComboBox.setValue(newUser);
            }

        }
        ObservableList<contact> ContactsToPopulate = contactDatabase.getAllContacts();
        for (contact c : ContactsToPopulate) {
            if (appointment.getContactID() == c.getContactID()) {
                this.newContact = c;
                ModifyContactCombo.setValue(newContact);
            }
        }

    }
}