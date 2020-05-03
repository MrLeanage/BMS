package view.OrderManagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import model.OrderMenu;
import services.OrderMenuServices;
import util.authenticate.SupervisorHandler;
import util.userAlerts.AlertPopUp;
import util.utility.UtilityMethod;
import util.validation.DataValidation;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class OrderMenuSupervisorController implements Initializable {


    @FXML
    private TableView<OrderMenu> OrderMenuTable;

    @FXML
    private TableColumn<OrderMenu, String> OMIIDColumn;

    @FXML
    private TableColumn<OrderMenu, ImageView> OMIImageColumn;

    @FXML
    private TableColumn<OrderMenu, String> OMINameColumn;

    @FXML
    private TableColumn<OrderMenu, String> OMIDescriptionColumn;

    @FXML
    private TableColumn<OrderMenu, Float> OMIWeightPerUnitColumn;

    @FXML
    private TableColumn<OrderMenu, Float> OMIPriceColumn;

    @FXML
    private TableColumn<OrderMenu, String> OMIStatusColumn;

    @FXML
    private TextField SearchTextBox;

    @FXML
    private TextField OMINameTextField;

    @FXML
    private TextField OMIWeightTextField;

    @FXML
    private Label OMINameLabel;

    @FXML
    private Label OMIWeightLabel;

    @FXML
    private TextField OMIPriceTextField;

    @FXML
    private Label OMIPriceLabel;

    @FXML
    private TextArea OMIDescriptionTextArea;

    @FXML
    private Label OMIDescriptionLabel;

    @FXML
    private ChoiceBox<String> OMIStatusChoiceBox;

    @FXML
    private Label OMIImageViewLabel;

    @FXML
    private ImageView OMIImageView;

    private static OrderMenu  existingOrderMenuProductModel;

    private static File staticFile;

    private ObservableList<String> choiceboxList = FXCollections.observableArrayList("Available","Not Available");

    @FXML
    AnchorPane rootpane;
    SupervisorHandler supervisorHandler = new SupervisorHandler();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        OMIStatusChoiceBox.setValue("Available");
        OMIStatusChoiceBox.setItems(choiceboxList);
        loadData();
        searchTable();
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



    @FXML
    private void imageChooser(ActionEvent event) throws IOException{
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files","*.jpg"));
        File file = fileChooser.showOpenDialog(null);
        if(file != null){
            System.out.println(file.getAbsolutePath());
            //OMIImageView.setImage();
            staticFile = file;
            Image image = new Image(file.toURI().toString());
            OMIImageView.setImage(image);
        }
    }
    @FXML
    private void clearFields(){

        try{
            OMINameTextField.setText("");
            OMIWeightTextField.setText("");
            OMIPriceTextField.setText("");
            OMIDescriptionTextArea.setText("");
            OMIImageView.setImage(null);

        }catch (NullPointerException ex){
            AlertPopUp.generalError(ex);
        }
    }

    private void clearLabels(){

        try{
            OMINameLabel.setText("");
            OMIWeightLabel.setText("");
            OMIPriceLabel.setText("");
            OMIDescriptionLabel.setText("");
            OMIImageViewLabel.setText("");

        }catch(NullPointerException ex){

        }
    }
    //validate inputs on inserting and updating
    private boolean dataValidate(){

        boolean returnVal = false;

        if(DataValidation.TextFieldNotEmpty(OMINameTextField)
                && DataValidation.TextFieldNotEmpty(OMIWeightTextField)
                && DataValidation.TextFieldNotEmpty(OMIPriceTextField)
                && DataValidation.TextAreaNotEmpty(OMIDescriptionTextArea)
                && DataValidation.ImageFieldNotEmpty(OMIImageView)
                //Checking for maximum Characters
                && DataValidation.isValidMaximumLength(OMINameTextField.getText(),80)
                && DataValidation.isValidMaximumLength(OMIWeightTextField.getText(),10)
                && DataValidation.isValidMaximumLength(OMIPriceTextField.getText(),10)
                && DataValidation.isValidMaximumLength(OMIDescriptionTextArea.getText(),250)
                //Checking for Specific Data Validation
                && DataValidation.isValidNumberFormat(OMIWeightTextField.getText())
                && DataValidation.isValidNumberFormat(OMIPriceTextField.getText())){
            returnVal = true;
        }


        return returnVal;
    }
    //prompting user to fix validation errors
    private void dataValidateMessage(){

        //checking for being empty
        if(!(DataValidation.TextFieldNotEmpty(OMINameTextField)
                && DataValidation.TextFieldNotEmpty(OMIWeightTextField)
                && DataValidation.TextFieldNotEmpty(OMIPriceTextField)
                && DataValidation.TextAreaNotEmpty(OMIDescriptionTextArea)
                && DataValidation.ImageFieldNotEmpty(OMIImageView))){


            DataValidation.TextFieldNotEmpty(OMINameTextField, OMINameLabel,"Name for Bakery SalesItem is Required");
            DataValidation.TextFieldNotEmpty(OMIWeightTextField, OMIWeightLabel,"Weight of SalesItem is Required(KG/L)");
            DataValidation.TextFieldNotEmpty(OMIPriceTextField, OMIPriceLabel,"Price of the SalesItem is Required");
            DataValidation.TextFieldNotEmpty(OMIDescriptionTextArea, OMIDescriptionLabel, "Description about the SalesItem is Required");
            DataValidation.ImageFieldNotEmpty(OMIImageView, OMIImageViewLabel, "Please Select a Image of the SalesItem");
            //checking for exceeding limit

        }
        if(!( DataValidation.isValidMaximumLength(OMINameTextField.getText(),80)
                && DataValidation.isValidMaximumLength(OMIWeightTextField.getText(),10)
                && DataValidation.isValidMaximumLength(OMIPriceTextField.getText(),10)
                && DataValidation.isValidMaximumLength(OMIDescriptionTextArea.getText(),250))){


            DataValidation.isValidMaximumLength(OMINameTextField.getText(),80, OMINameLabel,"Order Menu SalesItem Name is too Long!..");
            DataValidation.isValidMaximumLength(OMIWeightTextField.getText(),10, OMIWeightLabel,"Error!..Exceeding Character limit 10");
            DataValidation.isValidMaximumLength(OMIDescriptionTextArea.getText(),250, OMIDescriptionLabel, "Error!..Exceeding Character limit 250 Characters");
            DataValidation.isValidMaximumLength(OMIPriceTextField.getText(),10, OMIPriceLabel, "Error!..Exceeding Character limit 10");

        }
        //checking for specific properties
        if(!(DataValidation.isValidNumberFormat(OMIWeightTextField.getText())
                && DataValidation.isValidNumberFormat(OMIPriceTextField.getText()))){
            //Checking for Specific Data Validation
            DataValidation.isValidNumberFormat(OMIWeightTextField.getText(), OMIWeightLabel,"SalesItem Weight can contain only Digits");
            DataValidation.isValidNumberFormat(OMIPriceTextField.getText(), OMIPriceLabel, "SalesItem Price can contain only Digits");
        }
    }

    //load data from Main Controller to View table
    private void loadData() {
        //getting data from main LoginController
        OrderMenuServices orderMenuServices = new OrderMenuServices();

        ObservableList<OrderMenu> orderMenuData;
        orderMenuData = orderMenuServices.loadData();

        //Setting cell value factory to table view
        OMIIDColumn.setCellValueFactory(new PropertyValueFactory<>("oMIID"));
        OMINameColumn.setCellValueFactory(new PropertyValueFactory<>("oMIName"));
        OMIDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("oMIDescription"));
        OMIWeightPerUnitColumn.setCellValueFactory(new PropertyValueFactory<>("oMIWeight"));
        OMIPriceColumn.setCellValueFactory(new PropertyValueFactory<>("oMIPrice"));
        OMIStatusColumn.setCellValueFactory(new PropertyValueFactory<>("oMIStatus"));

        OMIImageColumn.setCellFactory(param -> {
            //Set up the ImageView
            final ImageView imageview = new ImageView();
            imageview.setFitHeight(50);
            imageview.setFitWidth(80);

            //Set up the Table
            TableCell<OrderMenu, ImageView> cell = new TableCell<OrderMenu, ImageView>() {
                public void updateItem(ImageView item, boolean empty) {
                    if(item != null){
                        if(!empty){
                            imageview.setImage(item.getImage());
                        }
                    }
                }
            };
            // Attach the imageview to the cell
            cell.setGraphic(null);
            cell.setGraphic(imageview);
            return cell;
        });
        OMIImageColumn.setCellValueFactory(new PropertyValueFactory<>("oMIImage"));
        OrderMenuTable.setItems(null);
        OrderMenuTable.setItems(orderMenuData);
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
        OrderMenu orderMenuModel = new OrderMenu();
        OrderMenuServices orderMenuServices = new OrderMenuServices();

        if(dataValidate()){

            orderMenuModel.setoMIName(OMINameTextField.getText());
            orderMenuModel.setoMIImage(OMIImageView);
            orderMenuModel.setoMIDescription(OMIDescriptionTextArea.getText());
            orderMenuModel.setoMIWeight(Float.parseFloat(OMIWeightTextField.getText()));
            orderMenuModel.setoMIPrice(Float.parseFloat(OMIPriceTextField.getText()));
            orderMenuModel.setoMIStatus(OMIStatusChoiceBox.getValue());

            Boolean resultVal = orderMenuServices.insertData(orderMenuModel);
            if(resultVal){
                refreshTable();
            }
        }else{
            dataValidateMessage();
        }
    }
    @FXML
    private void loadSelectedData( ){

        OrderMenu orderMenuModel;

        try{
            orderMenuModel = OrderMenuTable.getSelectionModel().getSelectedItem();
            OMINameTextField.setText(orderMenuModel.getoMIName());
            OMIImageView.setImage(orderMenuModel.getoMIImage().getImage());
            OMIWeightTextField.setText(String.valueOf(orderMenuModel.getoMIWeight()));
            OMIDescriptionTextArea.setText(orderMenuModel.getoMIDescription());
            OMIPriceTextField.setText(String.valueOf(orderMenuModel.getoMIPrice()));
            OMIStatusChoiceBox.setValue(orderMenuModel.getoMIStatus());
            clearLabels();
        }catch(Exception ex){
            AlertPopUp.generalError(ex);
        }
    }

    @FXML
    public void loadSelectedModelData(){
        OrderMenu orderMenuModel;
        try{
            orderMenuModel = OrderMenuTable.getSelectionModel().getSelectedItem();
            existingOrderMenuProductModel = orderMenuModel;
        }catch(Exception ex){
            AlertPopUp.generalError(ex);
        }
    }


    @FXML
    private void updateData(ActionEvent event)throws Exception{

        clearLabels();
        OrderMenu orderMenuModel = new OrderMenu();
        OrderMenuServices orderMenuServices = new OrderMenuServices();

        try{
            if(!(existingOrderMenuProductModel.getoMIID().isEmpty() )){
                if(dataValidate()){
                    //getting selected ID

                    //Overriding existing values retreiving from table
                    orderMenuModel.setoMIID(existingOrderMenuProductModel.getoMIID());
                    orderMenuModel.setoMIImage(OMIImageView);
                    orderMenuModel.setoMIName(OMINameTextField.getText());
                    orderMenuModel.setoMIDescription(OMIDescriptionTextArea.getText());
                    orderMenuModel.setoMIWeight(Float.parseFloat(OMIWeightTextField.getText()));
                    orderMenuModel.setoMIPrice(Float.parseFloat(OMIPriceTextField.getText()));
                    orderMenuModel.setoMIStatus(OMIStatusChoiceBox.getValue());
                    boolean resultVal = orderMenuServices.updateData(orderMenuModel);
                    if(resultVal){
                        refreshTable();
                    }
                }else{
                    dataValidateMessage();
                }
            }
        }catch (NullPointerException ex){
            AlertPopUp.selectRowToUpdate("Order Menu Item");
        }


    }
    @FXML
    private void deleteData(){

        int ID;
        OrderMenu orderMenuModel;
        OrderMenuServices orderMenuServices = new OrderMenuServices();
        orderMenuModel = OrderMenuTable.getSelectionModel().getSelectedItem();

        //checking for null ID Selection with try
        try{
            ID = UtilityMethod.seperateID(orderMenuModel.getoMIID());

            if(ID != 0){
                Optional<ButtonType> action = AlertPopUp.deleteConfirmation("Order Menu Item");
                //Checking for confirmation
                if(action.get() == ButtonType.OK){
                    Boolean resultVal = orderMenuServices.deleteData(ID);
                    if(resultVal){
                        refreshTable();
                    }
                }else if(action.get() == ButtonType.CANCEL){
                    refreshTable();
                }
            }
        }catch(Exception ex){
            AlertPopUp.selectRowToDelete("Order Menu Item");
        }
    }
    public void searchTable(){
        OrderMenuServices orderMenuServices = new OrderMenuServices();
        //Retrieving sorted data from Main LoginController
        SortedList<OrderMenu> sortedData = orderMenuServices.searchTable(SearchTextBox);
        //binding the SortedList to TableView
        sortedData.comparatorProperty().bind(OrderMenuTable.comparatorProperty());
        //adding sorted and filtered data to the table
        OrderMenuTable.setItems(null);
        OrderMenuTable.setItems(sortedData);


    }
}
