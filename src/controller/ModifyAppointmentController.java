package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
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
    public ComboBox ModifyContactCombo;
    public TextField ModifyTypeTxt;
    public DatePicker ModifyStartDatePicker;
    public Label ModifyStartTimeLbl;
    public ComboBox ModifyStartTimeCombo;
    public Label ModifyEndDateLbl;
    public DatePicker ModifyEndDatePicker;
    public Label ModifyEndTimeLbl;
    public ComboBox ModifyEndTimeCombo;
    public Label ModifyCustIDLbl;
    public TextField ModifyCustIDTxt;
    public Label ModifyUserIDLbl;
    public TextField ModifyUserIDTxt;
    public Button SaveModifyAppointmentBtn;
    public Button CancelModifyAppointmentBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    /** Allows the user to save the modified appointment form.
     * @param actionEvent selection */
    public void OnActionSaveModifyAppointment(ActionEvent actionEvent) {
    }
    /** Allows the user to cancel any changes to the modified appointment form and return to Home screen.
     * @param actionEvent selection */
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
}
