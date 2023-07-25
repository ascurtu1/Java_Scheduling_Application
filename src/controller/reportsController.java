package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class reportsController implements Initializable {

    Stage stage;
    Parent scene;

    public Label ReportsLbl;
    public TableView TypeAndMonthTableview;
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
    public ComboBox SelectContactCombo;
    public TableView ApptByCountryTable;
    public TableColumn ApptbyCountryLbl;
    public TableColumn ApptbyCountryTotalLbl;
    public Button SaveBtn;
    public Button CancelBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//add data here to populate the tables
    }
    /** Allows the user to choose contact from pre-populated combo box selection.
     * @param actionEvent selection */
    public void OnActionSelectContact(ActionEvent actionEvent) {
    }

    /** Allows the user to cancel any changes to the reports form and return to Home screen.
     * @param actionEvent selection */
    public void OnActionCancelReports(ActionEvent actionEvent) throws IOException {
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
