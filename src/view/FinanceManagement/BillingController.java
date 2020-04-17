package view.FinanceManagement;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.SalesItem;
import services.BillingServices;
import services.ProductServices;
import util.userAlerts.AlertPopUp;
import util.validation.DataValidation;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BillingController implements Initializable {


    @FXML
    private TableView<SalesItem> BillTable;

    @FXML
    private TableColumn<SalesItem, String> BIDColumn;

    @FXML
    private TableColumn<SalesItem, String> BNameColumn;

    @FXML
    private TableColumn<SalesItem, String> BWeightColumn;

    @FXML
    private TableColumn<SalesItem, Float> BPriceColumn;

    @FXML
    private TableColumn<SalesItem, Integer> BQuantityColumn;

    @FXML
    private TableColumn<SalesItem, Double> BTotalColumn;

    @FXML
    private TableColumn<SalesItem, String> BActionColumn;

    @FXML
    private TableView<SalesItem> ProductsTable;

    @FXML
    private TableColumn<SalesItem, String> PIDColumn;

    @FXML
    private TableColumn<SalesItem, String> PNameColumn;

    @FXML
    private TableColumn<SalesItem, String> PWeightPerUnitColumn;

    @FXML
    private TableColumn<SalesItem, Float> PPriceColumn;

    @FXML
    private TextField SearchTextBox;

    @FXML
    private Spinner<Integer> PQuantitySpinner;

    @FXML
    private TextField PNameTextbox;

    @FXML
    private TextField PIDTextbox;

    @FXML
    private TextField PWeightTextbox;

    @FXML
    private TextField PPriceTextbox;

    @FXML
    private TextField PTotalPriceTextbox;

    @FXML
    private Label TotalItemLabel;

    @FXML
    private Label DateLabel;

    @FXML
    private Label TotalAmountLabel;

    @FXML
    private Label PQuantityLabel;

    @FXML
    private Label OrderPaymentLabel;

    private static SalesItem existingSalesItemData;

    private LinkedList<SalesItem> salesItemLinkedList = new LinkedList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
        searchTable();
    }
    @FXML
    private void LogoutSession(ActionEvent event) throws IOException {


        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/AppHome/login.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();

    }
    @FXML
    private void ItemStock(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/InventoryManagement/ItemStock.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }

    @FXML
    private void Employees(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/EmployeeManagement/Employee.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    //internal methods
    @FXML
    private void FoodProducts(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/InventoryManagement/BakeryProducts.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    @FXML
    private void AgencyProduct(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/InventoryManagement/AgencyProduct.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    @FXML
    private void Supplier(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/InventoryManagement/Supplier.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    @FXML
    private void Billing(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/FinanceManagement/Billing.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    @FXML
    private void Products(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/InventoryManagement/Products.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    @FXML
    private void OrderMenu(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/OrderManagement/OrdersMenuCashier.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    @FXML
    private void Order(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/OrderManagement/Orders.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    @FXML
    private void OrderStatus(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/OrderManagement/OrdersStatusCashier.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    @FXML
    private void SalesInfo(ActionEvent event) throws IOException {

        AnchorPane home_page = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/FinanceManagement/SalesInfoCashier.fxml"));

        Scene scene = new Scene(home_page);
        Stage app=(Stage)((Node) event.getSource()).getScene().getWindow();
        app.setScene(scene);
        app.show();
    }
    @FXML
    private void clearFields(){

        try{
            PIDTextbox.setText("");
            PNameTextbox.setText("");
            PWeightTextbox.setText("");
            PPriceTextbox.setText("");
            SpinnerValueFactory<Integer> quantityValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 0);
            PQuantitySpinner.setValueFactory(quantityValueFactory);
            PTotalPriceTextbox.setText("");
            PTotalPriceTextbox.setDisable(true);
            clearLabels();

        }catch (NullPointerException ex){
            AlertPopUp.generalError(ex);
        }
    }

    private void clearLabels(){

        try{
            PQuantityLabel.setText("");
            OrderPaymentLabel.setText("");
        }catch(NullPointerException ex){

        }
    }
    //internal methods
    @FXML
    private void playBeep(){
        AudioClip note = new AudioClip(this.getClass().getResource("/lib/SoundTracks/beep-bit.wav").toString());
        note.play();
    }
    //getting selected supplier from popup

    public boolean setOrderPay(SalesItem salesItemData){

        boolean resultval = false;
        try{
            // setting supplierInfo data to a static variable for later use and returning true to close stage
            existingSalesItemData = salesItemData;
            resultval = true;

        }catch(NullPointerException ex){
            AlertPopUp.generalError(ex);
        }
        return resultval;
    }
    //validate inputs on inserting and updating
    private boolean dataValidate(){

        boolean returnVal = false;

        if(DataValidation.TextFieldNotEmpty(PQuantitySpinner.getValue())){
            returnVal = true;
        }


        return returnVal;
    }
    //prompting user to fix validation errors
    private void dataValidateMessage(){

        //checking for being empty
        if(!(DataValidation.TextFieldNotEmpty(PQuantitySpinner.getValue()))){

            DataValidation.TextFieldNotEmpty(PQuantitySpinner.getValue(), PQuantityLabel,"Item Quantity is Required");
        }
    }
    //load data  to View table
    private void loadData() {
        //getting data from main LoginController
        ProductServices productServices = new ProductServices();

        ObservableList<SalesItem> productsData = FXCollections.observableArrayList(productServices.loadData());
        //Setting cell value factory to table view
        PIDColumn.setCellValueFactory(new PropertyValueFactory<>("sIPID"));
        PNameColumn.setCellValueFactory(new PropertyValueFactory<>("sIPName"));
        PWeightPerUnitColumn.setCellValueFactory(new PropertyValueFactory<>("sIPWeight"));
        PPriceColumn.setCellValueFactory(new PropertyValueFactory<>("sIUnitPrice"));

        ProductsTable.setItems(null);
        ProductsTable.setItems(productsData);
        DateLabel.setText(String.valueOf(LocalDate.now()));

    }
    @FXML
    private void addDataToCart(ActionEvent event) throws Exception{

        clearLabels();
        SalesItem salesItem = new SalesItem();
        try{
            if(!existingSalesItemData.getsIPID().isEmpty()){
                if(dataValidate()){
                    salesItem.setsIPID(PIDTextbox.getText());
                    salesItem.setsIPName(PNameTextbox.getText());
                    salesItem.setsIPWeight(PWeightTextbox.getText());
                    salesItem.setsIUnitPrice(Float.parseFloat(PPriceTextbox.getText()));
                    salesItem.setsIPQuantity(Integer.parseInt(String.valueOf(PQuantitySpinner.getValue())));
                    salesItem.setsIUser("noobmaster95");
                    if(PTotalPriceTextbox.getText().isEmpty()){
                        salesItem.setsITotalAmount(Float.parseFloat(PPriceTextbox.getText()) * Integer.parseInt(String.valueOf(PQuantitySpinner.getValue())));
                    }else{
                        salesItem.setsITotalAmount(Float.parseFloat(PTotalPriceTextbox.getText()));
                    }
                    if((existingSalesItemData.getsIType().equals("Agency Product"))
                            || (existingSalesItemData.getsIType().equals("Order SalesItem"))
                            || (existingSalesItemData.getsIType().equals("Advance Payment"))
                            || (existingSalesItemData.getsIType().equals("Total Payment"))
                            || (existingSalesItemData.getsIType().equals("Balance Payment"))){
                        salesItem.setsIType(existingSalesItemData.getsIType());
                    }else{
                        salesItem.setsIType("Bakery Product");
                    }
                    salesItemLinkedList.add(salesItem);
                    refreshCartTable();
                }else{
                    dataValidateMessage();
                }
            }
        }catch(NullPointerException ex){
            System.out.println("Ex :" + ex);
            AlertPopUp.emptyInsertionFailed("No Sales Item Record to add to Bill");
        }
    }
    @FXML
    private void generateBill(ActionEvent event) throws Exception {
        BillingServices billingServices = new BillingServices();
        if(!salesItemLinkedList.isEmpty()){
            boolean resultVal = billingServices.insertData(salesItemLinkedList);
            if(resultVal){
                salesItemLinkedList.clear();
                refreshCartTable();
            }
        }else{
            AlertPopUp.emptyInsertionFailed("No Billing Items Found");
        }

    }
    @FXML
    public void refreshCartTable()throws Exception{
        ObservableList<SalesItem> productsData = FXCollections.observableArrayList(salesItemLinkedList);
        //Setting cell value factory to table view
        BIDColumn.setCellValueFactory(new PropertyValueFactory<>("Dummy"));
        BNameColumn.setCellValueFactory(new PropertyValueFactory<>("sIPName"));
        BWeightColumn.setCellValueFactory(new PropertyValueFactory<>("sIPWeight"));
        BPriceColumn.setCellValueFactory(new PropertyValueFactory<>("sIUnitPrice"));
        BQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("sIPQuantity"));
        BTotalColumn.setCellValueFactory(new PropertyValueFactory<>("sITotalAmount"));
        BActionColumn.setCellValueFactory(new PropertyValueFactory<>("Dummy"));

        BIDColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(BillTable.getItems().indexOf(p.getValue()) + 1 +" "));
        BIDColumn.setSortable(false);
        Callback<TableColumn<SalesItem, String>, TableCell<SalesItem, String>> parentCellFactory
                =
                new Callback<TableColumn<SalesItem, String>, TableCell<SalesItem, String>>() {
                    @Override
                    public TableCell call(final TableColumn<SalesItem, String> param) {
                        final TableCell<SalesItem, String> cell = new TableCell<SalesItem, String>() {

                            final Button btn = new Button("Remove Record");
                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {

                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    btn.setOnAction(event -> {
                                        int index = BillTable.getItems().indexOf(getTableView().getItems().get(getIndex()));

                                        //Removing Specific item from billing List
                                        salesItemLinkedList.remove(index);
                                        try {
                                            refreshCartTable();
                                        } catch ( Exception e) {
                                            e.printStackTrace();
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
        BActionColumn.setCellFactory(parentCellFactory);

        BillTable.setItems(null);
        BillTable.setItems(productsData);
        billingInfo();
        clearFields();
        clearLabels();
    }
    //Clearing All Items in Cart
    @FXML
    public void clearCart() throws Exception {
        if(!salesItemLinkedList.isEmpty()){
            salesItemLinkedList.clear();
            refreshCartTable();
        }else{
            AlertPopUp.emptyInsertionFailed("No Billing Products Found");
        }
    }
    private void billingInfo(){
        int count = salesItemLinkedList.size();
        double total = 0;
        for(SalesItem salesItem : salesItemLinkedList){
            total += salesItem.getsITotalAmount();
        }
        TotalItemLabel.setText(String.valueOf(count));
        TotalAmountLabel.setText("Rs : "+total+"0");
        DateLabel.setText(String.valueOf(LocalDate.now()));
    }
    @FXML
    public void loadSelectedData(){
        SalesItem salesItem;
        try{
            salesItem = ProductsTable.getSelectionModel().getSelectedItem();
            PIDTextbox.setText(salesItem.getsIPID());
            PNameTextbox.setText(salesItem.getsIPName());
            PWeightTextbox.setText(String.valueOf(salesItem.getsIPWeight()));
            PPriceTextbox.setText(String.valueOf(salesItem.getsIUnitPrice()));
            SpinnerValueFactory<Integer> quantityValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000,0);
            PQuantitySpinner.setValueFactory(quantityValueFactory);

        }catch (Exception ex){
            //AlertPopUp.generalError(ex);
        }
    }
    @FXML
    public void loadSelectedDataModel(){
        SalesItem salesItem;
        try{
            salesItem = ProductsTable.getSelectionModel().getSelectedItem();
            existingSalesItemData = salesItem;

        }catch (Exception ex){
           // AlertPopUp.generalError(ex);
        }
    }
    @FXML
    public void getPayOrder(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("PayOrderPopUP.fxml"));
        try{
            loader.load();
        }catch (IOException ex){
            Logger.getLogger(PayOrderPopUPController.class.getName()).log(Level.SEVERE, null, ex);
        }
        PayOrderPopUPController payOrderPopUPController = loader.getController();
        // String sID = getID();

        Parent p = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        stage.setResizable(false);

        stage.showAndWait();
        try{
            PIDTextbox.setText(existingSalesItemData.getsIPID());
            if(existingSalesItemData.getsIType().equals("Total Payment") || existingSalesItemData.getsIType().equals("Advance Payment") || existingSalesItemData.getsIType().equals("Balance Payment")){
                PNameTextbox.setText(existingSalesItemData.getsIPName() + " ("+ existingSalesItemData.getsIType()+")");
            }else{
                PNameTextbox.setText(existingSalesItemData.getsIPName());
            }
            PWeightTextbox.setText(String.valueOf(existingSalesItemData.getsIPWeight()));
            PPriceTextbox.setText(String.valueOf(existingSalesItemData.getsIUnitPrice()));
            PTotalPriceTextbox.setText(String.valueOf(existingSalesItemData.getsITotalAmount()));
            PTotalPriceTextbox.setDisable(false);
            OrderPaymentLabel.setText(existingSalesItemData.getsIType()+ ":");
            SpinnerValueFactory<Integer> quantityValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(existingSalesItemData.getsIPQuantity(), existingSalesItemData.getsIPQuantity(), existingSalesItemData.getsIPQuantity());
            PQuantitySpinner.setValueFactory(quantityValueFactory);
        }catch (Exception ex){
            AlertPopUp.generalError(ex);
        }
    }
    public void searchTable(){

        ProductServices productServices = new ProductServices();
        //Retrieving sorted data
        SortedList<SalesItem> sortedData = productServices.searchTable(SearchTextBox);

        //binding the SortedList to TableView
        sortedData.comparatorProperty().bind(ProductsTable.comparatorProperty());
        //adding sorted and filtered data to the table
        ProductsTable.setItems(sortedData);
    }

}
