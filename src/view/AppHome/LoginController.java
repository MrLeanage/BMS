package view.AppHome;

import animatefx.animation.*;
import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.User;
import services.UserServices;
import util.authenticate.UserAuthentication;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class LoginController implements Initializable {
    @FXML
    private Circle Circle1;

    @FXML
    private Circle Circle2;

    @FXML
    private Circle Circle3;

    @FXML
    private Circle Circle4;

    @FXML
    private Circle Circle5;


    @FXML
    private AnchorPane rootpane;

    @FXML
    private AnchorPane LoadingAnchorPane;
    @FXML
    private TextField UIDTextField;

    @FXML
    private PasswordField UPasswordPasswordField;

    @FXML
    private Label LoginValidateLabel;
    private UserAuthentication userAuthentication = new UserAuthentication();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startUpAnimation();
    }
    private void startUpAnimation(){


        new Bounce(Circle1).setCycleCount(5).setDelay(Duration.valueOf("100ms")).play();
        AnimationFX animationFX = new Bounce(Circle2);
            animationFX.setCycleCount(5).setDelay(Duration.valueOf("300ms")).play();
        new Bounce(Circle3).setCycleCount(5).setDelay(Duration.valueOf("400ms")).play();
        new Bounce(Circle4).setCycleCount(5).setDelay(Duration.valueOf("150ms")).play();
        new Bounce(Circle5).setCycleCount(5).setDelay(Duration.valueOf("100ms")).play();

        animationFX.setOnFinished((e -> {
            LoadingAnchorPane.setVisible(false);
        }));

    }
    @FXML
    private void clearLabels(){
        LoginValidateLabel.setText("");
    }
    private void clearFields(){
        UIDTextField.setText("");
        UPasswordPasswordField.setText("");
    }
    @FXML
    private void validateLogin(ActionEvent actionEvent) {

        UserServices userServices = new UserServices();
        User user = new User();
        user = userServices.userValidation(UIDTextField.getText(), UPasswordPasswordField.getText());
        if(!user.getuID().equals("Empty")){
            if(user.getuStatus().equals("Active")){
                userAuthentication.setAuthenticatedSession(user);
                userAuthentication.showMainView(actionEvent, rootpane);
            }else{
                LoginValidateLabel.setText("Your Account is Disabled, Please Contact Administrator!");
                clearFields();
            }
        }else{
            LoginValidateLabel.setText("Invalid User Name or Password Combination");
        }
    }


}
