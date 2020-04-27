package util.authenticate;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.userAlerts.AlertPopUp;

import java.io.IOException;

public class AdminManagementHandler {
    public AdminManagementHandler() {
    }
    public void loadItemStock(ActionEvent actionEvent){
        try {
            AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/InventoryManagement/ItemStock.fxml"));

            Scene scene = new Scene(home_page);
            Stage app=(Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            app.setScene(scene);
            app.show();
        } catch (IOException ex) {
            AlertPopUp.generalError(ex);
        }
    }
    public void loadSalesCounter(ActionEvent actionEvent){
        try {
            AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/FinanceManagement/SalesCounterAdmin.fxml"));

            Scene scene = new Scene(home_page);
            Stage app=(Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            app.setScene(scene);
            app.show();
        } catch (IOException ex) {
            AlertPopUp.generalError(ex);
        }
    }
    public void loadOrderStatus(ActionEvent actionEvent){
        try {
            AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/OrderManagement/OrdersStatusAdmin.fxml"));

            Scene scene = new Scene(home_page);
            Stage app=(Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            app.setScene(scene);
            app.show();
        } catch (IOException ex) {
            System.out.println(ex);
            AlertPopUp.generalError(ex);
        }
    }
    public void loadEmployees(ActionEvent actionEvent){
        try {
            AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/EmployeeManagement/Employee.fxml"));

            Scene scene = new Scene(home_page);
            Stage app=(Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            app.setScene(scene);
            app.show();
        } catch (IOException ex) {
            AlertPopUp.generalError(ex);
        }
    }
}
