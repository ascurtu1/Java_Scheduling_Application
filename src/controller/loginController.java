package controller;

import dao.userDatabase;
import helper.JDBC;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.user;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;



/** This class controls the logic to log in to the application. */
public class loginController implements Initializable {


    Stage stage;
    Parent scene;

    public Label Username_Lbl;
    public Label Pwd_Lbl;
    public Label Timezone_Lbl;
    public TextField Username_TxtField;
    public TextField Pwd_TxtField;
    public Button Login_Button;
    public Button ExitButton;
    public Label UserTimezone;
    boolean LoginSuccess = false;

    @Override

    /** Initializes the login screen and sets the user's login display to either English or French based on computer language settings.
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
            ZoneId LocalZoneId = ZoneId.of(TimeZone.getDefault().getID());
            UserTimezone.setText(String.valueOf(LocalZoneId));

            ResourceBundle Rb = ResourceBundle.getBundle("Language/Language", Locale.getDefault());
            if (Locale.getDefault().getLanguage().equals("en") || Locale.getDefault().getLanguage().equals("fr")) {
                Username_Lbl.setText(Rb.getString("Username"));
                Pwd_Lbl.setText(Rb.getString("Password"));
                Timezone_Lbl.setText(Rb.getString("Timezone"));
                Login_Button.setText(Rb.getString("Login"));
                ExitButton.setText(Rb.getString("Exit"));
            }

    }

    /**
     * Allows the user to login to the application by ensuring correct username and password. Activity log is also implemented to track user login activity.
     *
     * @param actionEvent button click
     */
    public void OnActionLogIn(ActionEvent actionEvent) throws SQLException, IOException {
        String UserName = Username_TxtField.getText();
        String password = Pwd_TxtField.getText();
        String filename = "login_activity.txt";
        FileWriter fileWriter = new FileWriter(filename,true);
        PrintWriter outputFile = new PrintWriter(fileWriter);
        Timestamp LoginAttemptTimestamp = Timestamp.valueOf(LocalDateTime.now());
        outputFile.print("Timestamp: " + LoginAttemptTimestamp + " ");


        ObservableList<user> userLogIn = userDatabase.getAllUsers();
        for (user UserLoggingIn : userLogIn) {
            if (UserName.equals(UserLoggingIn.getUserName()) && password.equals(UserLoggingIn.getPassword())) {
                LoginSuccess = true;
                break;
                }
            }
        if (LoginSuccess) {
            Parent root = FXMLLoader.load(getClass().getResource("/View/home.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1080, 720);
            stage.setScene(scene);
            stage.show();
            outputFile.println("Timestamp: " + LoginAttemptTimestamp + "Login Attempt Successful By " + UserName);
        } else if (!LoginSuccess) {
            ResourceBundle Rb = ResourceBundle.getBundle("Language/Language", Locale.getDefault());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(Rb.getString("ErrorContent"));
            alert.show();
            outputFile.println("Timestamp: " + LoginAttemptTimestamp + " Login Attempted By " + UserName);
        }
            outputFile.close();
        }

    /**
     * Allows the user to exit the application.
     *
     * @param actionEvent button click
     */
    public void OnActionExit (ActionEvent actionEvent){
            JDBC.closeConnection();
            System.exit(0);
        }
}
