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

/** This class controls the logic to add customers in the application. */
public class AddCustomerController implements Initializable {

    Stage stage;
    Parent scene;

    public Label AddCustomerLbl;
    public Label AddCustomerIDLbl;
    public Label AddCustomerNameLbl;
    public Label AddCustomerAddressLbl;
    public Label AddCustomerPhone;
    public Label AddCustomerCountry;
    public Label AddCustomerState;
    public Label AddCountryPostal;
    public TextField AddCustomerIDTxt;
    public TextField AddCustomerNameTxt;
    public TextField AddCustomerAddressTxt;
    public TextField AddCustomerPhoneTxt;
    public ComboBox<String> AddCustomerCountryCombo;
    public ComboBox<String> AddCustomerStateCombo;
    public TextField AddCustomerPostalTxt;
    public Button CustomerSaveBtn;
    public Button CustomerCancelBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Allows the user to choose state from pre-populated combo box selection based on their country selection.
     *
     * @param actionEvent selection
     */
    public void OnActionCustomerState(ActionEvent actionEvent) {
    }

    /**
     * Allows the user to save added customer information.
     *
     * @param actionEvent button click
     */
    public void OnActionSaveCustomer(ActionEvent actionEvent) {
    }

    /**
     * Allows the user to cancel added appointment information and return to Main screen.
     *
     * @param actionEvent button click
     */
    public void OnActionCancelAddCustomer(ActionEvent actionEvent) throws IOException {
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
