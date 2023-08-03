package controller;

import dao.countryDatabase;
import dao.customerDatabase;
import dao.divisionDatabase;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.Main;
import model.country;
import model.division;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Supplier;

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
    public ComboBox<country> AddCustomerCountryCombo;
    public ComboBox<division> AddCustomerStateCombo;
    public TextField AddCustomerPostalTxt;
    public Button CustomerSaveBtn;
    public Button CustomerCancelBtn;

    /**
     * Populates the country combo box with the country options from the SQL database.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /**
         * Lambda expression is used here to populate the customer country combo box with all possible countries. By
         * encapsulating the code inside a lambda,the code becomes clearer and easier to read. It also allows the programmer
         * to reuse the setCountryItems lambda whenever needed to set the items for the AddCustomerCountryCombo.
         * It promotes code usability and reduces duplication.*/

        Runnable setCountryItems = () -> {
            try {
                AddCustomerCountryCombo.setItems(countryDatabase.getAllCountries());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        };

        setCountryItems.run();
    }

    /**
     * Allows the user to choose state from pre-populated combo box selection based on the country selection.
     *
     * @param actionEvent selection
     */
    public void OnActionCustomerState(ActionEvent actionEvent) throws SQLException {

        //using the method to access country ID from MySQL database and set the combo boxes with the correct country states.
        country SelectedCountry = AddCustomerCountryCombo.getSelectionModel().getSelectedItem();
        if ((SelectedCountry.getCountryID() == 1)) {
            ObservableList<division> USDivisions = divisionDatabase.getUSDivisions();
            AddCustomerStateCombo.setItems(USDivisions);
        } else if ((SelectedCountry.getCountryID() == 2)) {
            ObservableList<division> UKDivisions = divisionDatabase.getUKDivisions();
            AddCustomerStateCombo.setItems(UKDivisions);
        } else {
            ObservableList<division> CanadaDivisions = divisionDatabase.getCanadaDivision();
            AddCustomerStateCombo.setItems(CanadaDivisions);
        }
    }


    /**
     * Allows the user to save added customer information.
     *
     * @param actionEvent button click
     */
    public void OnActionSaveCustomer(ActionEvent actionEvent) {
        try {
            String name = AddCustomerNameTxt.getText();
            String address = AddCustomerAddressTxt.getText();
            String phoneNumber = AddCustomerPhoneTxt.getText();
            String postal = AddCustomerPostalTxt.getText();
            int stateID = AddCustomerStateCombo.getSelectionModel().getSelectedItem().getDivisionID();


            if (name.isEmpty() || address.isEmpty() || phoneNumber.isEmpty() || postal.isEmpty() || (AddCustomerCountryCombo.getValue() == null) ||
                    (AddCustomerStateCombo.getValue() == null)) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Error: Fields are blank");
                alert.show();
            } else {
                customerDatabase.addCustomer(name, address, postal, phoneNumber, stateID);
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

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * Lambda is used here to encapsulate the code to show the alert and wait for the user's response.
     * This approach promotes code re-usability and readability. It allows the programmer to call this
     * expression in multiple classes to show the alert without having to rewrite all of the code.
     */

    public static Supplier<Optional<ButtonType>> showAndWaitAlert = () -> {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will cancel any changes. Please confirm to proceed");
        return alert.showAndWait();
    };



    /**
     * Allows the user to cancel added appointment information and return to the Home screen.
     * @param actionEvent button click
     */

    public void OnActionCancelAddCustomer(ActionEvent actionEvent) throws IOException {

// Calling the lambda expression
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


