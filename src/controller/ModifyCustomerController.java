package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.customer;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class holds the logic for the Modify Customer form. */
public class ModifyCustomerController implements Initializable {


    Stage stage;
    Parent scene;

    public Label ModifyCustomerLbl;
    public Label ModifyCustomerIDLbl;
    public Label ModifyCustomerNameLbl;
    public Label ModifyCustomerAddressLbl;
    public Label ModifyCustomerPhoneLbl;
    public Label ModifyCustomerCountryLbl;
    public Label ModifyCustomerStateLbl;
    public Label ModifyCustomerCodeLbl;
    public TextField ModifyCustomerIDTxt;
    public TextField ModifyCustomerNameTxt;
    public TextField ModifyCustomerAddressTxt;
    public TextField ModifyCustomerPhoneTxt;
    public ComboBox ModifyCustomerCountryCombo;
    public ComboBox ModifyCustomerStateCombo;
    public TextField ModifyCustomerCodeTxt;
    public Button ModifyCustomerSaveBtn;
    public Button ModifyCustomerCancelBtn;
    public GridPane ModifyCustomerPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    /** Allows the user to choose state from pre-populated combo box selection based on their country selection.
     * @param actionEvent selection */
    public void OnActionCustomerState(ActionEvent actionEvent) {
    }
    /** Allows the user to save the modified customer form.
     * @param actionEvent selection */
    public void OnActionSaveModifyCustomer(ActionEvent actionEvent) {
    }

    /** Allows the user to cancel the modified customer changes and return to the Main screen.
     * @param actionEvent selection */
    public void OnActionCancelModifyCustomer(ActionEvent actionEvent) throws IOException {
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

    /**Populates the textfields and combo boxes in the form with the information from the main screen and database. */

    public void populateCustomer (customer customer) {
        ModifyCustomerIDTxt.setText(String.valueOf(customer.getCustomerID()));
        ModifyCustomerNameTxt.setText(customer.getCustomerName());
        ModifyCustomerAddressTxt.setText(customer.getCustomerAddress());
        ModifyCustomerPhoneTxt.setText(customer.getCustomerPhoneNumber());
        ModifyCustomerCountryCombo.setValue(customer.getCustomerCountryName());
        ModifyCustomerStateCombo.setValue(customer.getCustomerDivisionName());
        ModifyCustomerCodeTxt.setText(customer.getCustomerPostalCode());

        //need to do combo boxes

    }
}
