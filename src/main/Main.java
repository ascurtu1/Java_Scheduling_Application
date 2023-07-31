package main;

import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

/** This class creates an app that launches the display of a scheduling application as well as opening and closing its connection to a database. */
public class Main extends Application {

    @Override
    public void start (Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../view/login.fxml"));
        stage.setTitle("Scheduling Application");
        stage.setScene(new Scene(root,1200 , 920));
        stage.show();
    }


    public static void main(String[] args) {

        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();

    }
}
