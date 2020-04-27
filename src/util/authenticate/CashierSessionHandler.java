package util.authenticate;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.userAlerts.AlertPopUp;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CashierSessionHandler {

    public CashierSessionHandler() {

    }

    public void loadBilling(ActionEvent actionEvent){

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
    public void loadProducts(AnchorPane rootpane){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/InventoryManagement/Products.fxml"));
            rootpane.getChildren().setAll(pane);
        }catch(IOException ex){
            AlertPopUp.generalError(ex);
        }

    }
    public void loadOrder(AnchorPane rootpane){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/OrderManagement/Orders.fxml"));
            rootpane.getChildren().setAll(pane);
        }catch(IOException ex){
            AlertPopUp.generalError(ex);
        }
    }
    public void loadOrderMenu(AnchorPane rootpane){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/OrderManagement/OrdersMenuCashier.fxml"));
            rootpane.getChildren().setAll(pane);
        }catch(IOException ex){
            AlertPopUp.generalError(ex);
        }
    }
    public void loadOrderStatus(AnchorPane rootpane){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/OrderManagement/OrdersStatusCashier.fxml"));
            rootpane.getChildren().setAll(pane);
        }catch(IOException ex){
            AlertPopUp.generalError(ex);
        }
    }
    public void loadSalesInfo(AnchorPane rootpane){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/FinanceManagement/SalesInfoCashier.fxml"));
            rootpane.getChildren().setAll(pane);
        }catch(IOException ex){
            AlertPopUp.generalError(ex);
        }
    }
}
