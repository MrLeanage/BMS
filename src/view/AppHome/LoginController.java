package view.AppHome;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;
import services.UserServices;
import util.authenticate.UserAuthentication;

import java.io.IOException;


public class LoginController {
    @FXML
    private AnchorPane rootpane;
    @FXML
    private TextField UIDTextField;

    @FXML
    private PasswordField UPasswordPasswordField;

    @FXML
    private Label LoginValidateLabel;
    private UserAuthentication userAuthentication = new UserAuthentication();
    @FXML
    private void AdminHome(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/InventoryManagement/ItemStock.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    @FXML
    private void CashierHome(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/FinanceManagement/Billing.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    @FXML
    private void SupervisorHome(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/InventoryManagement/ItemWithdraw.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
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
