package view.AppHome;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.authenticate.UserAuthentication;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(new AnchorPane());
        UserAuthentication userAuthentication = new UserAuthentication(scene);
        userAuthentication.showLoginScreen();
        primaryStage.setTitle("Bakery Management System");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
