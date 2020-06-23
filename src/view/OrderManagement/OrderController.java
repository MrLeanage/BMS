package view.OrderManagement;

import com.jfoenix.controls.JFXTimePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Order;
import model.OrderMenu;
import services.OrderMenuServices;
import services.OrderServices;
import util.authenticate.CashierHandler;
import util.authenticate.UserAuthentication;
import util.userAlerts.AlertPopUp;
import util.utility.UtilityMethod;
import util.validation.DataValidation;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderController implements Initializable {

    @FXML
    AnchorPane rootpane;
    private CashierHandler cashierHandler = new CashierHandler();

    @FXML
    private TableView<Order> OrderTable;

    @FXML
    private TableColumn<Order, String> OIDColumn;

    @FXML
    private TableColumn<Order, String> OCustomerNameColumn;

    @FXML
    private TableColumn<Order, String> OCustomerNICColumn;

    @FXML
    private TableColumn<Order, String> OTypeColumn;

    @FXML
    private TableColumn<Order, String> ODetailsColumn;

    @FXML
    private TableColumn<Order, Integer> OQuantityColumn;

    @FXML
    private TableColumn<Order, String> OAdvancePayColumn;

    @FXML
    private TableColumn<Order, String> OTotalColumn;

    @FXML
    private TableColumn<Order, String> OStatusColumn;

    @FXML
    private TableColumn<Order, String> OProcessingStatusColumn;

    @FXML
    private TableColumn<Order, String> OActionColumn;
    @FXML
    private TextField SearchTextBox;

    @FXML
    private TextField OMenuIDTextBox;

    @FXML
    private ChoiceBox<String> OTypeChoiceBox;

    @FXML
    private TextArea ODetailsTextArea;

    @FXML
    private TextField OQuantityTextBox;

    @FXML
    private DatePicker ODeliveryDateDatePicker;

    @FXML
    private JFXTimePicker ODeliveryTimeTimePicker;

    @FXML
    private TextField OCustomerNameTextBox;

    @FXML
    private TextField OCustomerNICTextBox;

    @FXML
    private TextField OCustomerPhoneTextBox;

    @FXML
    private Label OMenuLabel;

    @FXML
    private Label ODeliveryDateLabel;

    @FXML
    private Label OCustomerNameLabel;

    @FXML
    private Label OTypeLabel;

    @FXML
    private Label ODeliveryTimeLabel;

    @FXML
    private Label OCustomerNICLabel;

    @FXML
    private Label ODetailsLabel;

    @FXML
    private Label OCustomerPhoneLabel;

    @FXML
    private Label OQuantityLabel;

    private static Order existingOrderModel;

    private static OrderMenu orderMenu;
    private ObservableList<String> orderTypeChoiceboxList = FXCollections.observableArrayList("Menu Item","Customized");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       OTypeChoiceBox.setValue("Menu Item");
       OTypeChoiceBox.setItems(orderTypeChoiceboxList);
        loadData();
        searchTable();
    }
    @FXML
    private void Billing(ActionEvent event){
        cashierHandler.loadBilling(event);
    }
    @FXML
    private void Products(ActionEvent event){
        cashierHandler.loadProducts(rootpane);
    }
    @FXML
    private void OrderMenu(ActionEvent event){
        cashierHandler.loadOrderMenu(rootpane);
    }
    @FXML
    private void Order(ActionEvent event) {
        cashierHandler.loadOrder(rootpane);
    }
    @FXML
    private void OrderStatus(ActionEvent event){
        cashierHandler.loadOrderStatus(rootpane);
    }
    @FXML
    private void SalesInfo(ActionEvent event) {
        cashierHandler.loadSalesInfo(rootpane);
    }
    @FXML
    private void clearFields(){

        try{
            OMenuIDTextBox.setText("");
            OTypeChoiceBox.setValue("");
            ODetailsTextArea.setText("");
            OQuantityTextBox.setText("");
            ODeliveryDateDatePicker.setValue(null);
            ODeliveryTimeTimePicker.setValue(null);
            OCustomerNameTextBox.setText("");
            OCustomerNICTextBox.setText("");
            OCustomerPhoneTextBox.setText("");


        }catch (NullPointerException ex){
            AlertPopUp.generalError(ex);
        }
    }

    private void clearLabels(){

        try{
            OMenuLabel.setText("");
            OTypeLabel.setText("");
            ODetailsLabel.setText("");
            OQuantityLabel.setText("");
            ODeliveryDateLabel.setText("");
            ODeliveryTimeLabel.setText("");
            OCustomerNameLabel.setText("");
            OCustomerNICLabel.setText("");
            OCustomerPhoneLabel.setText("");
        }catch(NullPointerException ex){

        }
    }
    //validate inputs on inserting and updating
    private boolean dataValidate(){

        boolean returnVal = false;

        if(DataValidation.TextFieldNotEmpty(OMenuIDTextBox)
                && DataValidation.TextFieldNotEmpty(OTypeChoiceBox.getValue())
                && DataValidation.TextFieldNotEmpty(OQuantityTextBox.getText())
                && DataValidation.DatePickerNotEmpty(ODeliveryDateDatePicker)
                && DataValidation.TimePickerNotEmpty(ODeliveryTimeTimePicker)
                && DataValidation.TextFieldNotEmpty(OCustomerNameTextBox.getText())
                && DataValidation.TextFieldNotEmpty(OCustomerNICTextBox.getText())
                && DataValidation.TextFieldNotEmpty(OCustomerPhoneTextBox.getText())
                //Checking for maximum Characters
                && DataValidation.isValidMaximumLength(ODetailsTextArea.getText(),500)
                && DataValidation.isValidMaximumLength(OQuantityTextBox.getText(),5)
                && DataValidation.isValidMaximumLength(OCustomerNameTextBox.getText(),80)
                //Checking for Specific Data Validation
                && DataValidation.isValidNumberFormat(OQuantityTextBox.getText())
                && DataValidation.isValidNIC(OCustomerNICTextBox)
                && DataValidation.isValidNumberFormat(OCustomerPhoneTextBox.getText())){
            returnVal = true;
        }
        return returnVal;
    }
    //prompting user to fix validation errors
    private void dataValidateMessage(){

        //checking for being empty
        if(!(DataValidation.TextFieldNotEmpty(OMenuIDTextBox)
                && DataValidation.TextFieldNotEmpty(OTypeChoiceBox.getValue())
                && DataValidation.TextFieldNotEmpty(OQuantityTextBox.getText())
                && DataValidation.DatePickerNotEmpty(ODeliveryDateDatePicker)
                && DataValidation.TimePickerNotEmpty(ODeliveryTimeTimePicker)
                && DataValidation.TextFieldNotEmpty(OCustomerNameTextBox.getText())
                && DataValidation.TextFieldNotEmpty(OCustomerNICTextBox.getText())
                && DataValidation.TextFieldNotEmpty(OCustomerPhoneTextBox.getText()))){


            DataValidation.TextFieldNotEmpty(OMenuIDTextBox.getText(), OMenuLabel, "Select a Order Menu..");
            DataValidation.TextFieldNotEmpty(OTypeChoiceBox.getValue(), OTypeLabel, "Select the Type of Order..");
            DataValidation.TextFieldNotEmpty(OQuantityTextBox.getText(), OQuantityLabel, "Order Quantity is Required");
            DataValidation.DatePickerNotEmpty(ODeliveryDateDatePicker, ODeliveryDateLabel, "Order Delivery Date is Required");
            DataValidation.TimePickerNotEmpty(ODeliveryTimeTimePicker, ODeliveryTimeLabel, "Order Delivery Time is Required");
            DataValidation.TextFieldNotEmpty(OCustomerNameTextBox.getText(), OCustomerNameLabel, "Customer Name is Required");
            DataValidation.TextFieldNotEmpty(OCustomerNICTextBox.getText(), OCustomerNICLabel, "Customer NIC is Required");
            DataValidation.TextFieldNotEmpty(OCustomerPhoneTextBox.getText(), OCustomerPhoneLabel, "Customer Phone Number is Required");
            //checking for exceeding limit

        }
        if(!(DataValidation.isValidMaximumLength(ODetailsTextArea.getText(),500)
                && DataValidation.isValidMaximumLength(OQuantityTextBox.getText(),5)
                && DataValidation.isValidMaximumLength(OCustomerNameTextBox.getText(),80))){


            DataValidation.isValidMaximumLength(ODetailsTextArea.getText(), 500, ODetailsLabel,"Exceeding Character Limit-500");
            DataValidation.isValidMaximumLength(OQuantityTextBox.getText(),5, OQuantityLabel,"Quantity Amount is too Long(Limit - 5)");
            DataValidation.isValidMaximumLength(OCustomerNameTextBox.getText(),80, OCustomerNameLabel, "Customer Name is too Long (Limit 80)");
        }
        //checking for specific properties
        if(!(DataValidation.isValidNumberFormat(OQuantityTextBox.getText())
                && DataValidation.isValidNIC(OCustomerNICTextBox)
                && DataValidation.isValidNumberFormat(OCustomerPhoneTextBox.getText()))){
            //Checking for Specific Data Validation
            DataValidation.isValidNumberFormat(OQuantityTextBox.getText(),OQuantityLabel,"Ordering Quantity can Contain Digits Only");
            DataValidation.isValidNumberFormat(OCustomerNICTextBox.getText(), OCustomerNICLabel, "Invalid NIC Number");
            DataValidation.isValidNumberFormat(OCustomerPhoneTextBox.getText(), OCustomerPhoneLabel, "Invalid Phone Number");
        }
    }
    @FXML
    private void selectOrderMenu(ActionEvent event){

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("OrderMenuPopUP.fxml"));

        try{
            loader.load();

        }catch (IOException ex){
            Logger.getLogger(OrderMenuPopUPController.class.getName()).log(Level.SEVERE, null, ex);
        }
        OrderMenuPopUPController orderMenuPopUPController = loader.getController();

        Parent p = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        stage.setResizable(false);
        stage.showAndWait();

        try{
            OMenuIDTextBox.setText(orderMenu.getoMIID());
        }catch (NullPointerException ex){

        }
    }
    public boolean setOrderMenuItem(OrderMenu orderMenu){

        boolean resultval = false;

        try{
            // setting supplierInfo data to a static variable for later use and returning true to close stage
            this.orderMenu = orderMenu;
            resultval = true;
        }catch(NullPointerException ex){
            AlertPopUp.generalError(ex);
        }
        return resultval;
    }


    //load data from Main LoginController to View table
    private void loadData() {
        //getting data
        OrderServices orderServices = new OrderServices();
        OrderMenuServices orderMenuServices = new OrderMenuServices();

        OrderMenu orderMenuModel;
        ObservableList<Order> ordersData;

        ordersData = orderServices.loadUnPaidData("Total Payment");

        //Setting cell value factory to table view
        OIDColumn.setCellValueFactory(new PropertyValueFactory<>("oID"));
        OCustomerNameColumn.setCellValueFactory(new PropertyValueFactory<>("oCustomerName"));
        OCustomerNICColumn.setCellValueFactory(new PropertyValueFactory<>("oCustomerNIC"));
        OTypeColumn.setCellValueFactory(new PropertyValueFactory<>("oType"));
        ODetailsColumn.setCellValueFactory(new PropertyValueFactory<>("oDetails"));
        OQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("oQuantity"));
        OAdvancePayColumn.setCellValueFactory(new PropertyValueFactory<>("oAdvancePay"));
        OTotalColumn.setCellValueFactory(new PropertyValueFactory<>("oTotal"));
        OStatusColumn.setCellValueFactory(new PropertyValueFactory<>("oStatus"));
        OProcessingStatusColumn.setCellValueFactory(new PropertyValueFactory<>("oProcessingStatus"));
        OActionColumn.setCellValueFactory(new PropertyValueFactory<>("Dummy"));

        Callback<TableColumn<Order, String>, TableCell<Order, String>> parentCellFactory
                =
                new Callback<TableColumn<Order, String>, TableCell<Order, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Order, String> param) {
                        final TableCell<Order, String> cell = new TableCell<Order, String>() {

                            final Button btn = new Button("Order Action");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    btn.setOnMouseClicked(event -> {
                                        // student = StudentTable.getSelectionModel().getSelectedItem();
                                        //String sID = student.getsID();
                                    });
                                    btn.setOnAction(event -> {
                                        //pdf generate and status Update method here
                                        Order order = getTableView().getItems().get(getIndex());
                                        OrderServices orderServices = new OrderServices();

                                        Alert action = new Alert(Alert.AlertType.CONFIRMATION);
                                        action.setTitle("Select Your Action");
                                        action.setHeaderText("You Selected a Request with");
                                        action.setContentText(order.getoType()+" item of "+order.getoOMName());

                                        ButtonType cancelOrderBtn = new ButtonType("Cancel Order");
                                        ButtonType processPendingBtn = new ButtonType("set Process Pending");
                                        ButtonType cancelBtn = new ButtonType("Cancel");
                                        if(order.getoProcessingStatus().equals("Completed")){
                                            action.getButtonTypes().setAll(cancelBtn);
                                        }else{
                                            if(order.getoProcessingStatus().equals("Cancelled")){
                                                action.getButtonTypes().setAll(processPendingBtn, cancelBtn);
                                            }else{
                                                action.getButtonTypes().setAll(cancelOrderBtn, cancelBtn);
                                            }

                                        }

                                        Optional<ButtonType> clickAction = action.showAndWait();
                                        if(clickAction.isPresent() && clickAction.get() == processPendingBtn){
                                            try {
                                                orderServices.updateOrderStatus(order.getoID(),"Process Pending");
                                                loadData();
                                            } catch ( Exception ex) {
                                                ex.printStackTrace();
                                            }
                                        }
                                        if(clickAction.isPresent() && clickAction.get() == cancelOrderBtn){
                                            try {
                                                orderServices.updateOrderStatus(order.getoID(),"Cancelled");
                                                loadData();
                                            } catch ( Exception ex) {
                                                ex.printStackTrace();
                                            }
                                        }

                                    });
                                    setGraphic(btn);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };
        OActionColumn.setCellFactory(parentCellFactory);

        OrderTable.setItems(null);
        OrderTable.setItems(ordersData);

    }

    //refresh Data in the Table
    @FXML
    public void refreshTable()throws Exception{
        clearFields();
        loadData();
        clearLabels();
    }
    //Add Supplier
    @FXML
    private void addData(ActionEvent event) throws Exception{

        clearLabels();
        Order orderModel = new Order();
        OrderServices orderServices = new OrderServices();

        if(dataValidate()){

            orderModel.setoOMID(OMenuIDTextBox.getText());
            orderModel.setoType(OTypeChoiceBox.getValue());
            orderModel.setoDetails(ODetailsTextArea.getText());
            orderModel.setoQuantity(Integer.parseInt(OQuantityTextBox.getText()));
            orderModel.setoDeliveryDate(String.valueOf(ODeliveryDateDatePicker.getValue()));
            orderModel.setoDeliveryTime(String.valueOf(ODeliveryTimeTimePicker.getValue()));
            orderModel.setoCustomerName(OCustomerNameTextBox.getText());
            orderModel.setoCustomerNIC(OCustomerNICTextBox.getText());
            orderModel.setoCustomerPhone(OCustomerPhoneTextBox.getText());
            orderModel.setoTakenDate(String.valueOf(LocalDate.now()));
            orderModel.setoTakenTime(UtilityMethod.currentTime());
            orderModel.setoTakenUID(UserAuthentication.getAuthenticatedSession().getuID());
            orderModel.setoStatus("Payment Pending");
            orderModel.setoProcessingStatus("Process Pending");
            Boolean resultVal = orderServices.insertData(orderModel);
            if(resultVal){
                refreshTable();
            }
        }else{
            dataValidateMessage();
        }
    }
    @FXML
    private void loadSelectedData( ){

        OrderServices orderServices = new OrderServices();
        Order orderTableModel;
        Order orderModel;
        try{
            orderTableModel = OrderTable.getSelectionModel().getSelectedItem();
            orderModel = orderServices.loadSpecificData(UtilityMethod.seperateID(orderTableModel.getoID()));


            OMenuIDTextBox.setText(orderModel.getoOMID());
            OTypeChoiceBox.setValue(orderModel.getoType());
            ODetailsTextArea.setText(orderModel.getoDetails());
            OQuantityTextBox.setText(String.valueOf(orderModel.getoQuantity()));
            ODeliveryDateDatePicker.setValue(LocalDate.parse(orderModel.getoDeliveryDate()));
            ODeliveryTimeTimePicker.setValue(LocalTime.parse(orderModel.getoDeliveryTime()));
            OCustomerNameTextBox.setText(orderModel.getoCustomerName());
            OCustomerNICTextBox.setText(orderModel.getoCustomerNIC());


            OCustomerPhoneTextBox.setText("0"+orderModel.getoCustomerPhone());
            clearLabels();
        }catch(Exception ex){
            AlertPopUp.generalError(ex);
        }
    }

    @FXML
    public void loadSelectedModelData(){
        Order  orderTableModel;
        try{
            orderTableModel = OrderTable.getSelectionModel().getSelectedItem();
            existingOrderModel = orderTableModel;
        }catch(Exception ex){
            AlertPopUp.generalError(ex);
        }
    }


    @FXML
    private void updateData(ActionEvent event)throws Exception{

        clearLabels();
        Order orderModel = new Order();
        OrderServices orderServices = new OrderServices();

        try{
            if(!(existingOrderModel.getoID().isEmpty() )){

                if(dataValidate()){
                    //getting selected ID

                    //Overriding existing values retreiving from table
                    orderModel.setoID(existingOrderModel.getoID());
                    orderModel.setoOMID(OMenuIDTextBox.getText());
                    orderModel.setoType(OTypeChoiceBox.getValue());
                    orderModel.setoDetails(ODetailsTextArea.getText());
                    orderModel.setoQuantity(Integer.parseInt(OQuantityTextBox.getText()));
                    orderModel.setoDeliveryDate(String.valueOf(ODeliveryDateDatePicker.getValue()));
                    orderModel.setoDeliveryTime(String.valueOf(ODeliveryTimeTimePicker.getValue()));
                    orderModel.setoCustomerName(OCustomerNameTextBox.getText());
                    orderModel.setoCustomerNIC(OCustomerNICTextBox.getText());
                    orderModel.setoCustomerPhone(OCustomerPhoneTextBox.getText());
                    orderModel.setoTakenDate(String.valueOf(LocalDate.now()));
                    orderModel.setoTakenTime(UtilityMethod.currentTime());
                    orderModel.setoTakenUID(UserAuthentication.getAuthenticatedSession().getuID());
                    orderModel.setoStatus("Payment Pending");
                    orderModel.setoProcessingStatus("Process Pending");

                    boolean resultVal = orderServices.updateData(orderModel);
                    if(resultVal){
                        refreshTable();
                    }
                }else{
                    dataValidateMessage();
                }
            }
        }catch (NullPointerException ex){
            AlertPopUp.selectRowToUpdate("Order");
        }


    }
    @FXML
    private void deleteData(ActionEvent event){

        int ID;
        Order orderModel;
        OrderServices orderServices = new OrderServices();
        orderModel = OrderTable.getSelectionModel().getSelectedItem();

        //checking for null ID Selection with try
        try{
            ID = UtilityMethod.seperateID(orderModel.getoID());
            if(ID != 0){
                Optional<ButtonType> action = AlertPopUp.deleteConfirmation("Order");
                //Checking for confirmation
                if(action.get() == ButtonType.OK){
                    Boolean resultVal = orderServices.deleteData(ID);
                    if(resultVal){
                        refreshTable();
                    }
                }else if(action.get() == ButtonType.CANCEL){
                    refreshTable();
                }
            }
        }catch(Exception ex){
            AlertPopUp.selectRowToDelete("Order");
        }
    }
    public void searchTable(){

        OrderServices orderServices = new OrderServices();
        //Retrieving sorted data
        SortedList<Order> sortedData = orderServices.searchUnPayedDataTable(SearchTextBox, "Total Payment");

        //binding the SortedList to TableView
        sortedData.comparatorProperty().bind(OrderTable.comparatorProperty());
        //adding sorted and filtered data to the table
        OrderTable.setItems(sortedData);
    }
}
