package util.authenticate;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import util.userAlerts.AlertPopUp;

import java.io.IOException;

public class OrderHandler {
    public OrderHandler() {
    }
    public void loadOrderStatus(AnchorPane rootpane){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/OrderManagement/OrdersMenuAdmin.fxml"));
            rootpane.getChildren().setAll(pane);
        }catch(IOException ex){
            AlertPopUp.generalError(ex);
        }
    }
    public void loadOrderMenu(AnchorPane rootpane){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/OrderManagement/OrdersMenuAdmin.fxml"));
            rootpane.getChildren().setAll(pane);
        }catch(IOException ex){
            AlertPopUp.generalError(ex);
        }
    }
    public void loadCompletedOrder(AnchorPane rootpane){
        /*
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/OrderManagement/OrderStatusAdmin.fxml"));
            rootpane.getChildren().setAll(pane);
        }catch(IOException ex){
            AlertPopUp.generalError(ex);
        }

         */
    }
}
