package view.FinanceManagement;

import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Order;
import model.OrderMenu;
import model.SalesItem;
import services.OrderMenuServices;
import services.OrderServices;
import util.userAlerts.AlertPopUp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PayOrderPopUPController implements Initializable {

    @FXML
    private TableView<Order> OrderTable;

    @FXML
    private TableColumn<Order, String> OIDColumn;

    @FXML
    private TableColumn<Order, String> ONameColumn;

    @FXML
    private TableColumn<Order, String> ONICColumn;

    @FXML
    private TableColumn<Order, String> ODetailsColumn;

    @FXML
    private TableColumn<Order, String> OStatusColumn;

    @FXML
    private TextField SearchTextBox;

    @FXML
    private TextField OIDTextBox;

    @FXML
    private TextField NameTextBox;

    @FXML
    private TextField ODetailsTextBox;

    @FXML
    private TextField NICTextBox;

    @FXML
    private TextField OQuantityTextBox;

    @FXML
    private TextField OAdvanceAmountTextBox;

    @FXML
    private TextField OTotalAmountTextBox;

    @FXML
    private RadioButton AdvanceRadioButton;

    @FXML
    private ToggleGroup PayAs;

    @FXML
    private RadioButton TotalRadioButton;

    @FXML
    private TextField OStatusTextBox;

    @FXML
    private Label PayAmountLabel;

    @FXML
    private Button OCloseButton;

    private static Order existingOrderData;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        searchTable();
        loadData();

    }

    private void loadData() {
        //getting data from main LoginController

        OrderServices orderServices = new OrderServices();

        ObservableList<Order> ordersData;
        ordersData = orderServices.loadData();

        //Setting cell value factory to table view
        OIDColumn.setCellValueFactory(new PropertyValueFactory<>("oID"));
        ONameColumn.setCellValueFactory(new PropertyValueFactory<>("oCustomerName"));
        ONICColumn.setCellValueFactory(new PropertyValueFactory<>("oCustomerNIC"));
        ODetailsColumn.setCellValueFactory(new PropertyValueFactory<>("oDetails"));
        OStatusColumn.setCellValueFactory(new PropertyValueFactory<>("oStatus"));

        OrderTable.setItems(null);
        OrderTable.setItems(ordersData);
    }

    @FXML
    private void loadSelectedData(){
        Order orderModel;
        try{
            orderModel = OrderTable.getSelectionModel().getSelectedItem();
            NameTextBox.setText(orderModel.getoCustomerName());
            NICTextBox.setText(orderModel.getoCustomerNIC());
            OIDTextBox.setText(orderModel.getoID());
            ODetailsTextBox.setText(orderModel.getoDetails());
            OQuantityTextBox.setText(String.valueOf(orderModel.getoQuantity()));
            OStatusTextBox.setText(orderModel.getoStatus());
            OAdvanceAmountTextBox.setText(String.valueOf(orderModel.getoAdvancePay()));
            OTotalAmountTextBox.setText(String.valueOf(orderModel.getoTotal()));
            if(orderModel.getoStatus().equals("Advance Paid")){
                AdvanceRadioButton.setDisable(true);
                TotalRadioButton.setSelected(true);
            }else{
                AdvanceRadioButton.setDisable(false);
                AdvanceRadioButton.setSelected(true);
            }
        }catch(Exception ex){
            //AlertPopUp.generalError(ex);
        }
    }
    @FXML
    private void loadSelectedModel(){
        Order orderModel;
        try{
            orderModel = OrderTable.getSelectionModel().getSelectedItem();
            existingOrderData = orderModel;
        }catch(Exception ex){
            //AlertPopUp.generalError(ex);

        }
    }
    public void searchTable(){

        OrderServices orderServices = new OrderServices();
        //Retrieving sorted data from Main LoginController
        SortedList<Order> sortedData = orderServices.searchTable(SearchTextBox);

        //binding the SortedList to TableView
        sortedData.comparatorProperty().bind(OrderTable.comparatorProperty());
        //adding sorted and filtered data to the table
        OrderTable.setItems(sortedData);


    }
    @FXML
    public void returnPayOrder() throws IOException {

        boolean resultVal = false;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Billing.fxml"));
        Parent root = (Parent) loader.load();
        BillingController billingController = loader.getController();

        SalesItem salesItemModel = new SalesItem();
        OrderMenu orderMenu = new OrderMenu();
        OrderMenuServices orderMenuServices = new OrderMenuServices();
        orderMenu = orderMenuServices.loadSpecificData(existingOrderData.getoOMID());
        String payType = null;
        Double totalPay = null;

        salesItemModel.setsIType("Ordered Product");
        if(existingOrderData.getoStatus().equals("Advance Paid")){
            payType = "Balance Payment";
            totalPay = (double) (existingOrderData.getoTotal() - existingOrderData.getoAdvancePay());
        }else if(AdvanceRadioButton.isSelected()){
            payType = "Advance Payment";
            totalPay = Double.parseDouble(OAdvanceAmountTextBox.getText());
        }else{
            payType = "Total Payment";
            totalPay = Double.parseDouble(OTotalAmountTextBox.getText());
        }

        try{
            if(!orderMenu.getoMIID().isEmpty()){
                salesItemModel.setsIPID(OIDTextBox.getText());
                salesItemModel.setsIPName(orderMenu.getoMIName());
                salesItemModel.setsIPWeight(String.valueOf(orderMenu.getoMIWeight()));
                salesItemModel.setsIUnitPrice(orderMenu.getoMIPrice());
                salesItemModel.setsIPQuantity(Integer.parseInt(OQuantityTextBox.getText()));
                salesItemModel.setsIType(payType);
                salesItemModel.setsITotalAmount(totalPay);

                resultVal = billingController.setOrderPay(salesItemModel);

                if(resultVal){
                    closeStage();
                }
            }
        }catch(NullPointerException ex){
            AlertPopUp.generalError(ex);
        }

    }
    private void closeStage(){
        Stage stage = (Stage) OCloseButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void closeButton(){
        // get a handle to the stage
        closeStage();
    }

}
