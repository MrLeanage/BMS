package view.InventoryManagement;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.ItemWithdraw;
import services.ItemWithdrawServices;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class WithdrawedItemsController implements Initializable {

    @FXML
    private TableView<ItemWithdraw> WithdrawTable;

    @FXML
    private TableColumn<ItemWithdraw, String> WIDColumn;

    @FXML
    private TableColumn<ItemWithdraw, String> WIIDColumn;

    @FXML
    private TableColumn<ItemWithdraw, String> WNameColumn;

    @FXML
    private TableColumn<ItemWithdraw, String> WDescriptionColumn;

    @FXML
    private TableColumn<ItemWithdraw, String> WWeightColumn;

    @FXML
    private TableColumn<ItemWithdraw, Integer> WQuantityColumn;

    @FXML
    private TableColumn<ItemWithdraw, String> WDateColumn;

    @FXML
    private TableColumn<ItemWithdraw, String> WTimeColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loadData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void LogoutSession(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/AppHome/login.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    //internal methods
    @FXML
    private void ItemWithdraw(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/InventoryManagement/ItemWithdraw.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    @FXML
    private void WithdrawedItems(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/InventoryManagement/WithdrawedItems.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    @FXML
    private void PendingOrders(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/OrderManagement/NewOrders.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    @FXML
    private void OnGoingOrders(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/OrderManagement/OnGoingOrders.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    @FXML
    private void CompletedOrders(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/OrderManagement/CompletedOrders.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    @FXML
    private void CancelledOrders(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/OrderManagement/CancelledOrders.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    @FXML
    private void OrderMenu(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/OrderManagement/OrdersMenuSupervisor.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    //load data to View table
    private void loadData() throws SQLException {
        //getting data from main LoginController
        ItemWithdrawServices itemWithdrawServices = new ItemWithdrawServices();

        ObservableList<ItemWithdraw> itemWithdrawsData;
        itemWithdrawsData = itemWithdrawServices.loadSpecificUserData("U0001");

        //Setting cell value factory to table view
        WIDColumn.setCellValueFactory(new PropertyValueFactory<>("iWID"));
        WIIDColumn.setCellValueFactory(new PropertyValueFactory<>("iWIID"));
        WNameColumn.setCellValueFactory(new PropertyValueFactory<>("iWIName"));
        WDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("iWDescription"));
        WWeightColumn.setCellValueFactory(new PropertyValueFactory<>("iWWeight"));
        WQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("iWQuantity"));
        WDateColumn.setCellValueFactory(new PropertyValueFactory<>("iWDate"));
        WTimeColumn.setCellValueFactory(new PropertyValueFactory<>("iWTime"));


        WithdrawTable.setItems(null);
        WithdrawTable.setItems(itemWithdrawsData);

    }


}
