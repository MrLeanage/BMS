package util.authenticate;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;
import util.userAlerts.AlertPopUp;
import util.userNotifications.Notification;
import view.AppHome.LoginController;


import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserAuthentication {
    public static Scene scene;
    private static User userSession = null;
    private static String currentAdminType = "default";

    public UserAuthentication(Scene scene){
        this.scene = scene;
    }

    public UserAuthentication() {

    }

    public void setAuthenticatedSession(User userSession){
        this.userSession = userSession;

    }
    public static User getAuthenticatedSession(){
        return userSession;
    }
    public static String getCurrentAdminType(){
        return currentAdminType;
    }
    public static void setCurrentAdminType(String adminType){
        currentAdminType = adminType;
    }
    /**
     * Callback method invoked to notify that a user has logged out of the main application.
     * Will show the login application screen.
     */
    public void endAuthenticatedSession(MenuButton menuButton) {


        Optional<ButtonType> action = AlertPopUp.sessionEndConfirmation("Make sure you have no record to Save, Do you want to logout from the System now??");
        //Checking for confirmation
        if(action.get() == ButtonType.OK){
            try{
                Notification.stopExecution();

                AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/AppHome/login.fxml"));

                Scene scene = new Scene(home_page);
                Stage app = (Stage) menuButton.getScene().getWindow();
                app.setScene(scene);
                app.show();
            }catch(IOException ex){
                AlertPopUp.generalError(ex);
            }
        }else if(action.get() == ButtonType.CANCEL){
        }
    }

    public void showLoginScreen() {

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/AppHome/login.fxml")
            );
            scene.setRoot((Parent) loader.load());
            LoginController controller =
                    loader.getController();
        } catch (IOException ex) {
            Logger.getLogger(UserAuthentication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showMainView(ActionEvent actionEvent, AnchorPane rootpane) {
        Notification notification = new Notification();
        if(userSession.getuType().equals("Admin")){
            try {
                AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/InventoryManagement/ItemStock.fxml"));

                Scene scene = new Scene(home_page);
                Stage app=(Stage)((Node) actionEvent.getSource()).getScene().getWindow();
                app.setScene(scene);
                app.show();
            } catch (IOException ex) {
                AlertPopUp.generalError(ex);
            }
        }else if(userSession.getuType().equals("Supervisor")){

            try {
                AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/InventoryManagement/ItemWithdraw.fxml"));

                Scene scene = new Scene(home_page);
                Stage app=(Stage)((Node) actionEvent.getSource()).getScene().getWindow();
                app.setScene(scene);
                app.show();
            } catch (IOException ex) {
                Logger.getLogger(UserAuthentication.class.getName()).log(Level.SEVERE, null, ex);
            }



        }else if(userSession.getuType().equals("Cashier")){
            try {
                AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/FinanceManagement/Billing.fxml"));

                Scene scene = new Scene(home_page);
                Stage app=(Stage)((Node) actionEvent.getSource()).getScene().getWindow();
                app.setScene(scene);
                app.show();
            } catch (IOException ex) {
                Logger.getLogger(UserAuthentication.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void getCashierMenu(MenuButton menuButton){
        Notification.stopExecution();
        userSession.setuType("Cashier");
        setCurrentAdminType(userSession.getuType());
        Notification notification = new Notification();
        try {
            AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/FinanceManagement/Billing.fxml"));

            Scene scene = new Scene(home_page);
            Stage app = (Stage) menuButton.getScene().getWindow();;
            app.setScene(scene);
            app.show();
        } catch (IOException ex) {
            Logger.getLogger(UserAuthentication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void getSupervisorMenu(MenuButton menuButton){
        Notification.stopExecution();
        userSession.setuType("Supervisor");
        setCurrentAdminType(userSession.getuType());
        Notification notification = new Notification();
        try {
            AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/InventoryManagement/ItemWithdraw.fxml"));

            Scene scene = new Scene(home_page);
            Stage app = (Stage) menuButton.getScene().getWindow();;
            app.setScene(scene);
            app.show();
        } catch (IOException ex) {
            Logger.getLogger(UserAuthentication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void getAdminMenu(MenuButton menuButton){
        Notification.stopExecution();
        userSession.setuType("Admin");
        setCurrentAdminType(userSession.getuType());
        Notification notification = new Notification();
        try {
            AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/InventoryManagement/ItemStock.fxml"));

            Scene scene = new Scene(home_page);
            Stage app = (Stage) menuButton.getScene().getWindow();
            app.setScene(scene);
            app.show();
        } catch (IOException ex) {
            Logger.getLogger(UserAuthentication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
