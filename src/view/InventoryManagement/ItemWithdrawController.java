package view.InventoryManagement;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import model.ItemStock;
import model.ItemWithdraw;
import services.ItemStockServices;
import services.ItemWithdrawServices;
import util.authenticate.SupervisorHandler;
import util.authenticate.UserAuthentication;
import util.userAlerts.AlertPopUp;
import util.validation.DataValidation;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class ItemWithdrawController implements Initializable {

    @FXML
    private TableView<ItemWithdraw> WithdrawTable;

    @FXML
    private TableColumn<ItemWithdraw, String> WIDColumn;

    @FXML
    private TableColumn<ItemWithdraw, String> WNameColumn;

    @FXML
    private TableColumn<ItemWithdraw, String> WDescriptionColumn;

    @FXML
    private TableColumn<ItemWithdraw, Integer> WQuantityColumn;

    @FXML
    private TableColumn<ItemWithdraw, Float> WWeightColumn;

    @FXML
    private TableColumn<ItemWithdraw, String> WActionColumn;

    @FXML
    private TextField SearchTextBox;

    @FXML
    private TextArea IWDescriptionTextArea;

    @FXML
    private TextField IWeightTextBox;

    @FXML
    private TextField INameTextBox;

    @FXML
    private TextField IIDTextBox;

    @FXML
    private TableView<ItemStock> ItemTable;

    @FXML
    private TableColumn<ItemStock, String> IIDColumn;

    @FXML
    private TableColumn<ItemStock, String> INameColumn;

    @FXML
    private TableColumn<ItemStock, Float> IWeightColumn;

    @FXML
    private TableColumn<ItemStock, Integer> IQuantityColumn;

    @FXML
    private TableColumn<ItemStock, String> IStatusColumn;

    @FXML
    private Spinner<Integer> IQuantitySpinner;

    @FXML
    private Label IQuantityLabel;

    @FXML
    private Label IDescriptionLabel;

    @FXML
    private MenuItem AdminPanelMenuItem;

    @FXML
    private MenuItem CashierPanelMenuItem;

    @FXML
    private MenuItem SupervisorPanelMenuItem;

    @FXML
    private  MenuButton OptionMenuButton;

    @FXML
    private AnchorPane rootpane;
    private LinkedList<ItemWithdraw> itemWithdrawsList = new LinkedList<ItemWithdraw>();
    private LinkedList<ItemStock> itemStocksList = new LinkedList<ItemStock>();

    private static ItemStock existingItemStockData;


    private SupervisorHandler supervisorHandler = new SupervisorHandler();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadData();
        //to auto refresh search results
        searchTable();
        OptionMenuButton.setText(UserAuthentication.getAuthenticatedSession().getuName());
        setSupervisorInfo();

    }
    private void setSupervisorInfo(){
        if(UserAuthentication.getCurrentAdminType().equals("default")){
            AdminPanelMenuItem.setVisible(false);
            CashierPanelMenuItem.setVisible(false);
            SupervisorPanelMenuItem.setVisible(false);
        }else{
            AdminPanelMenuItem.setVisible(true);
            CashierPanelMenuItem.setVisible(true);
            SupervisorPanelMenuItem.setVisible(true);
        }
    }
    @FXML
    private void LogoutSession(ActionEvent event){
        UserAuthentication userAuthentication = new UserAuthentication();
        userAuthentication.endAuthenticatedSession(OptionMenuButton);
    }
    @FXML
    private void adminPanel(ActionEvent event){
        UserAuthentication userAuthentication = new UserAuthentication();
        userAuthentication.getAdminMenu(OptionMenuButton);
    }
    @FXML
    private void cashierPanel(ActionEvent event){
        UserAuthentication userAuthentication = new UserAuthentication();
        userAuthentication.getCashierMenu(OptionMenuButton);
    }
    @FXML
    private void supervisorPanel(ActionEvent event){
        UserAuthentication userAuthentication = new UserAuthentication();
        userAuthentication.getSupervisorMenu(OptionMenuButton);
    }
    @FXML
    private void ItemWithdraw(ActionEvent event) {
        supervisorHandler.loadItemWithdraw(event);
    }
    @FXML
    private void WithdrawedItems(ActionEvent event) {
        supervisorHandler.loadWithdrawedItems(rootpane);
    }
    @FXML
    private void PendingOrders(ActionEvent event){
        supervisorHandler.loadPendingOrders(rootpane);
    }
    @FXML
    private void OnGoingOrders(ActionEvent event) {
        supervisorHandler.loadOnGoingOrders(rootpane);
    }
    @FXML
    private void CompletedOrders(ActionEvent event) {
        supervisorHandler.loadCompletedOrders(rootpane);
    }
    @FXML
    private void CancelledOrders(ActionEvent event) {
        supervisorHandler.loadCancelledOrders(rootpane);
    }
    @FXML
    private void OrderMenu(ActionEvent event) {
        supervisorHandler.loadOrderMenu(rootpane);
    }
    //internal methods
    @FXML
    private void clearFields(){

        try{
            IIDTextBox.setText("");
            INameTextBox.setText("");
            IWeightTextBox.setText("");
            IWDescriptionTextArea.setText("");
            SpinnerValueFactory<Integer> quantityValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 0);
            IQuantitySpinner.setValueFactory(quantityValueFactory);
            clearLabels();
            existingItemStockData = null;

        }catch (NullPointerException ex){
            AlertPopUp.generalError(ex);
        }
    }

    private void clearLabels(){

        try{
            IQuantityLabel.setText("");
            IDescriptionLabel.setText("");
        }catch(NullPointerException ex){

        }
    }
    //validate inputs on inserting and updating
    private boolean dataValidate(){

        boolean returnVal = false;

        if(DataValidation.TextFieldNotEmpty(IQuantitySpinner.getValue())
                && DataValidation.TextFieldNotEmpty(IWDescriptionTextArea.getText())
                //Checking for maximum Characters
                && DataValidation.isValidMaximumLength(IWDescriptionTextArea.getText(),100)){
            returnVal = true;
        }


        return returnVal;
    }
    //prompting user to fix validation errors
    private void dataValidateMessage(){

        //checking for being empty
        if(!(DataValidation.TextFieldNotEmpty(IQuantitySpinner.getValue())
                && DataValidation.TextFieldNotEmpty(IWDescriptionTextArea.getText()))){

            DataValidation.TextFieldNotEmpty(IQuantitySpinner.getValue(), IQuantityLabel,"Withdrawing Quantity is Required");
            DataValidation.TextFieldNotEmpty(IWDescriptionTextArea.getText(), IDescriptionLabel,"Withdrawing Description is Required");
            //checking for exceeding limit

        }
        if(!(DataValidation.isValidMaximumLength(IWDescriptionTextArea.getText(),100))){
            DataValidation.isValidMaximumLength(IWDescriptionTextArea.getText(),100, IDescriptionLabel,"Description Character Limit 100 Exceeded");
        }
    }

    //load data to View table
    private void loadData() {
        //getting data from main LoginController
        ItemStockServices itemStockServices = new ItemStockServices();

        ObservableList<ItemStock> itemStocksData;
        itemStocksData = itemStockServices.loadData();

        //Setting cell value factory to table view
        IIDColumn.setCellValueFactory(new PropertyValueFactory<>("iID"));
        INameColumn.setCellValueFactory(new PropertyValueFactory<>("iName"));
        IWeightColumn.setCellValueFactory(new PropertyValueFactory<>("iWeightPerBlock"));
        IQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("iAvailableQuantity"));
        IStatusColumn.setCellValueFactory(new PropertyValueFactory<>("iStatus"));

        ItemTable.setItems(null);
        ItemTable.setItems(itemStocksData);

    }

    //Clearing All Items in Cart
    @FXML
    public void clearCart() throws Exception {
        ItemWithdrawServices itemWithdrawServices = new ItemWithdrawServices();
        boolean resultVal = itemWithdrawServices.reAddStockQuantity(itemStocksList);
        if(resultVal){
            itemWithdrawsList.clear();
            refreshCartTable();
            loadData();
        }else{
            AlertPopUp.generalError("Clearing Cart");
        }
    }
    @FXML
    public void refreshCartTable()throws Exception{
        ObservableList<ItemWithdraw> itemStocksData = FXCollections.observableArrayList(itemWithdrawsList);
        //Setting cell value factory to table view
        WIDColumn.setCellValueFactory(new PropertyValueFactory<>("iWID"));
        WNameColumn.setCellValueFactory(new PropertyValueFactory<>("iWIName"));
        WDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("iWDescription"));
        WWeightColumn.setCellValueFactory(new PropertyValueFactory<>("iWWeight"));
        WQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("iWQuantity"));
        WActionColumn.setCellValueFactory(new PropertyValueFactory<>("Dummy"));

        WIDColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(WithdrawTable.getItems().indexOf(p.getValue()) + 1 +" "));
        WIDColumn.setSortable(false);
        Callback<TableColumn<ItemWithdraw, String>, TableCell<ItemWithdraw, String>> parentCellFactory
                =
                new Callback<TableColumn<ItemWithdraw, String>, TableCell<ItemWithdraw, String>>() {
                    @Override
                    public TableCell call(final TableColumn<ItemWithdraw, String> param) {
                        final TableCell<ItemWithdraw, String> cell = new TableCell<ItemWithdraw, String>() {

                            final Button btn = new Button("Remove Record");
                            ItemWithdrawServices itemWithdrawServices = new ItemWithdrawServices();
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
                                        int index = WithdrawTable.getItems().indexOf(getTableView().getItems().get(getIndex()));
                                        LinkedList<ItemStock> reAddStock = new LinkedList<>();
                                        //getting removing Item info and add back to the Items Stock Quantity
                                        reAddStock.add(itemStocksList.get(index));
                                        //Removing Specific item from saved ItemStock List
                                        itemStocksList.remove(index);
                                        //Removing Specific item from Withdrawing List
                                        itemWithdrawsList.remove(index);
                                        try {
                                            //re adding Stock back
                                            itemWithdrawServices.reAddStockQuantity(reAddStock);
                                            refreshCartTable();
                                            loadData();
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
        WActionColumn.setCellFactory(parentCellFactory);

        WithdrawTable.setItems(null);
        WithdrawTable.setItems(itemStocksData);
        clearFields();
        clearLabels();
    }
    //Add Supplier
    @FXML
    private void addDataToCart(ActionEvent event) throws Exception{

        clearLabels();
        ItemWithdraw itemWithdraw = new ItemWithdraw();
        ItemStock itemStock = new ItemStock();
        ItemWithdrawServices itemWithdrawServices = new ItemWithdrawServices();
        ItemStock revertItemStockData = new ItemStock();

        if(!existingItemStockData.getiID().isEmpty()){
            if(dataValidate()){

                itemWithdraw.setiWIID(IIDTextBox.getText());
                itemWithdraw.setiWIName(INameTextBox.getText());
                itemWithdraw.setiWWeight(IWeightTextBox.getText());
                itemWithdraw.setiWQuantity(Integer.parseInt(String.valueOf(IQuantitySpinner.getValue())));
                itemWithdraw.setiWDescription((IWDescriptionTextArea.getText()));
                itemWithdraw.setiWUser(UserAuthentication.getAuthenticatedSession().getuID());
                itemWithdraw.setiWDate(String.valueOf(LocalDate.now()));
                itemWithdraw.setiWTime(String.valueOf(LocalTime.now().truncatedTo(ChronoUnit.SECONDS)));

                //Setting new Available Stock Quantity to ItemStock Model with ItemStock ID
                itemStock.setiID(IIDTextBox.getText());
                itemStock.setiAvailableQuantity(existingItemStockData.getiAvailableQuantity() - Integer.parseInt(String.valueOf(IQuantitySpinner.getValue())));

                //Saving Previous Available Quantity to use if items reverted from cart
                revertItemStockData.setiID(existingItemStockData.getiID());
                revertItemStockData.setiAvailableQuantity(existingItemStockData.getiAvailableQuantity());
                itemStocksList.add(revertItemStockData);

                //Updating New Available Quantity with Existing Quantity
                boolean resultVal = itemWithdrawServices.withdrawStockQuantity(itemStock);
                if(resultVal){
                    //adding ItemStock to cart if Updating availability success
                    itemWithdrawsList.add(itemWithdraw);
                    refreshCartTable();
                    loadData();
                }else{
                    AlertPopUp.updateFailed( "Available Quantity");
                }


            }else{
                dataValidateMessage();
            }
        }else{
            AlertPopUp.emptyInsertionFailed("No Item Record to add to Cart");
        }
    }
    @FXML
    private void withdrawItemList(ActionEvent event) throws Exception {
        ItemWithdrawServices itemWithdrawServices = new ItemWithdrawServices();
        if(!itemWithdrawsList.isEmpty()){
            boolean resultVal = itemWithdrawServices.insertData(itemWithdrawsList);
            if(resultVal){
                itemWithdrawsList.clear();
                refreshCartTable();
            }
        }else{
            AlertPopUp.emptyInsertionFailed("No Cart Items Found");
        }

    }
    @FXML
    private void loadSelectedData(){

        ItemStock itemStockModel;

        try{
            itemStockModel = ItemTable.getSelectionModel().getSelectedItem();
            IIDTextBox.setText(itemStockModel.getiID());
            INameTextBox.setText(itemStockModel.getiName());
            IWeightTextBox.setText(String.valueOf(itemStockModel.getiWeightPerBlock()));
            SpinnerValueFactory<Integer> quantityValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, itemStockModel.getiAvailableQuantity(),0);
            IQuantitySpinner.setValueFactory(quantityValueFactory);
            clearLabels();
        }catch(Exception ex){
            AlertPopUp.generalError(ex);
        }
    }
    @FXML
    private void loadSelectedDataModel(){
        try{
            ItemStock itemStockModel;
            itemStockModel = ItemTable.getSelectionModel().getSelectedItem();
            existingItemStockData = itemStockModel;
        }catch (NullPointerException ex){

        }
    }
    public void searchTable(){

        ItemStockServices itemStockServices = new ItemStockServices();
        //Retrieving sorted data from Services
        SortedList<ItemStock> sortedData = itemStockServices.searchTable(SearchTextBox);

        //binding the SortedList to TableView
        sortedData.comparatorProperty().bind(ItemTable.comparatorProperty());
        //adding sorted and filtered data to the table
        ItemTable.setItems(sortedData);
    }

}
