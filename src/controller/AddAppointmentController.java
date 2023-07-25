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
    public TextField AptUserIDTxt;
    public Button AddApptSaveBtn;
    public Button AddApptCancelBtn;
    public TextField AddApptTitleTxt;
    public TextField AddApptDescriptionTxt;
    public TextField ApptLocationTxt;
    public ComboBox<String> ApptContactCombo;
    public TextField ApptTypeTxt;
    public DatePicker AptStartDatePicker;
    public ComboBox<String> AptStartTimeCombo;
    public DatePicker AptEndDatePicker;
    public ComboBox AptEndTimeCombo;
    public TextField AptCustIDTxt;
    public Label AptUserIDLbl;

    @Override
    //code in initialize to use lambdas to add info to contact, start and end time combo box
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Allows the user to save added appointment information.
     *
     * @param actionEvent button click
     */
    public void OnActionSaveAppointment(ActionEvent actionEvent) {
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
