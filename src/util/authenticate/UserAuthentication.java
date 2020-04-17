package util.authenticate;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import model.User;
import view.AppHome.LoginController;
import view.FinanceManagement.BillingController;
import view.InventoryManagement.ItemStockController;
import view.InventoryManagement.ItemWithdrawController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserAuthentication {
    private Scene scene;

    public UserAuthentication(Scene scene){
        this.scene = scene;
    }
    public void authenticated(User userSession){
        showMainView(userSession);
    }

    /**
     * Callback method invoked to notify that a user has logged out of the main application.
     * Will show the login application screen.
     */
    public void logout() {
        showLoginScreen();
    }

    public void showLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/AppHome/login.fxml")
            );
            scene.setRoot((Parent) loader.load());
            LoginController controller =
                    loader.<LoginController>getController();
            //controller.initManager(this);
        } catch (IOException ex) {
            Logger.getLogger(UserAuthentication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showMainView(User userSession) {
        if(userSession.getuType().equals("Admin")){
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/view/InventoryManagement/ItemStock.fxml")
                );
                scene.setRoot((Parent) loader.load());
                ItemStockController controller =
                        loader.<ItemStockController>getController();
                //controller.initSessionID(this, userSession);
            } catch (IOException ex) {
                Logger.getLogger(UserAuthentication.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(userSession.getuType().equals("Cashier")){
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/view/FinanceManagement/Billing.fxml")
                );
                scene.setRoot((Parent) loader.load());
                BillingController controller =
                        loader.<BillingController>getController();
                //controller.initSessionID(this, userSession);
            } catch (IOException ex) {
                Logger.getLogger(UserAuthentication.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(userSession.getuType().equals("Supervisor")){
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/view/InventoryManagement/ItemWithdraw.fxml")
                );
                scene.setRoot((Parent) loader.load());
                ItemWithdrawController controller =
                        loader.<ItemWithdrawController>getController();
                //controller.initSessionID(this, userSession);
            } catch (IOException ex) {
                Logger.getLogger(UserAuthentication.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
