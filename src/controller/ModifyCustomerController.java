package controller;

import dao.countryDatabase;
import dao.customerDatabase;
import dao.divisionDatabase;
import javafx.collections.FXCollections;
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
import model.country;
import model.customer;
import model.division;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

import static controller.AddCustomerController.showAndWaitAlert;

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
    public ComboBox<country> ModifyCustomerCountryCombo;
    public ComboBox<division> ModifyCustomerStateCombo;
    public TextField ModifyCustomerCodeTxt;
    public Button ModifyCustomerSaveBtn;
    public Button ModifyCustomerCancelBtn;
    public GridPane ModifyCustomerPane;
    private country newSelectedCountry;
    private division newSelectedDivision;

    /**
     * Setting the country combo box to populate with the country options from the database.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ModifyCustomerCountryCombo.setItems(countryDatabase.getAllCountries());
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    /**
     * Allows the user to choose state from pre-populated combo box selection based on their country selection.
     *
     * @param actionEvent selection
     */
    public void OnActionCustomerState(ActionEvent actionEvent) throws SQLException {
        country newSelectedCountry = ModifyCustomerCountryCombo.getSelectionModel().getSelectedItem();

        if ((newSelectedCountry.getCountryID() == 1)) {
            ObservableList<division> USDivisions = divisionDatabase.getUSDivisions();
            ModifyCustomerStateCombo.setItems(USDivisions);
        } else if ((newSelectedCountry.getCountryID() == 2)) {
            ObservableList<division> UKDivisions = divisionDatabase.getUKDivisions();
            ModifyCustomerStateCombo.setItems(UKDivisions);
        } else {
            ObservableList<division> CanadaDivisions = divisionDatabase.getCanadaDivision();
            ModifyCustomerStateCombo.setItems(CanadaDivisions);
        }
    }


    /**
     * Allows the user to save the modified customer form.
     *
     * @param actionEvent selection
     */
    public void OnActionSaveModifyCustomer(ActionEvent actionEvent) {
        try {
            int customerID = Integer.parseInt(ModifyCustomerIDTxt.getText());
            String name = ModifyCustomerNameTxt.getText();
            String address = ModifyCustomerAddressTxt.getText();
            String phoneNumber = ModifyCustomerPhoneTxt.getText();
            String postal = ModifyCustomerCodeTxt.getText();
            int stateID = ModifyCustomerStateCombo.getSelectionModel().getSelectedItem().getDivisionID();


            if (name.isEmpty() || address.isEmpty() || phoneNumber.isEmpty() || postal.isEmpty() || (ModifyCustomerCountryCombo.getValue() == null) ||
                    (ModifyCustomerStateCombo.getValue() == null)) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Error: Fields are blank");
                alert.show();
            } else {
                customerDatabase.updateCustomer(customerID, name, address, postal, phoneNumber, stateID);
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
        } catch (IOException | SQLException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * Allows the user to cancel the modified customer changes and return to the Main screen.
     * The alert is called using a lambda expression that is being called to reduce redundancy.
     * @param actionEvent selection
     */
    public void OnActionCancelModifyCustomer(ActionEvent actionEvent) throws IOException {
        Optional<ButtonType> result = showAndWaitAlert.get();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/View/home.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1080, 720);
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Populates the text fields and combo boxes in the Modify Customer form with the information from the main screen and database.
     * @param customer
     */

    public void populateCustomer(customer customer) throws SQLException {
//creating observable lists to pull information from the database dao
        ObservableList<country> ModifyCustomerCountryList = countryDatabase.getAllCountries();
        ObservableList<division> ModifyCustomerStateList = divisionDatabase.getAllDivisions();
        ObservableList<division> StateSelected = FXCollections.observableArrayList();
//setting the fields with the previously populated information from the selected customer
        ModifyCustomerIDTxt.setText(String.valueOf(customer.getCustomerID()));
        ModifyCustomerNameTxt.setText(customer.getCustomerName());
        ModifyCustomerAddressTxt.setText(customer.getCustomerAddress());
        ModifyCustomerPhoneTxt.setText(customer.getCustomerPhoneNumber());
        ModifyCustomerCodeTxt.setText(customer.getCustomerPostalCode());
        ModifyCustomerCountryCombo.setItems(ModifyCustomerCountryList);
        ModifyCustomerStateCombo.setItems(StateSelected);

        for (country country : ModifyCustomerCountryList) {
            if (Objects.equals(country.getCountryName(), customer.getCustomerCountryName())) {
                this.newSelectedCountry = country;

            }
        }

        ModifyCustomerCountryCombo.setValue(newSelectedCountry);
        for (division division : ModifyCustomerStateList) {
            if (newSelectedCountry.getCountryID() == division.getCountryID()) {
                StateSelected.add(division);
            }
        }
        ModifyCustomerStateCombo.setItems(StateSelected);
        for (division division2 : StateSelected) {
            if (customer.getCustomerDivID() == division2.getDivisionID()) {
                this.newSelectedDivision = division2;
            }
        }

        ModifyCustomerStateCombo.setValue(newSelectedDivision);
    }
}