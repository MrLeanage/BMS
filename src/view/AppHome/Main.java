package view.AppHome;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import services.UserServices;
import util.authenticate.UserAuthentication;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(new AnchorPane());
        UserAuthentication userAuthentication = new UserAuthentication(scene);
        UserServices userServices = new UserServices();
        Boolean resultVal = userServices.addDefaultAdminUser();
        if(resultVal){
            userAuthentication.showLoginScreen();
            primaryStage.setTitle("Bakery Management System");
            primaryStage.getIcons().add(new Image("/lib/img/stage-icon.png"));
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        }

    }


    public static void main(String[] args) {
        launch(args);
    }
}
