package view.FinanceManagement;

import javafx.beans.property.StringProperty;
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
import javafx.stage.Stage;
import model.Allowance;
import model.Employee;
import model.Purchase;
import services.AllowancePayServices;
import services.PurchaseServices;
import util.userAlerts.AlertPopUp;
import util.utility.UtilityMethod;
import util.validation.DataValidation;
import view.EmployeeManagement.EmployeeAllowancePopUPController;
import view.EmployeeManagement.EmployeeController;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PurchaseReportBankingInfoPayPopUPController implements Initializable {

    /**
     * Initializes the services class.
     * @param url
     * @param rb
     */



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }


    @FXML
    private Label SIDLabel;

    @FXML
    private Label SNameLabel;

    @FXML
    private TextField PIIDTextBox;

    @FXML
    private TextField PINameTextBox;

    @FXML
    private TextField PPricePerUnitTextBox;

    @FXML
    private TextField PQuantityTextBox;

    @FXML
    private TextField PTotalAmountTextBox;

    @FXML
    private TextField PPurchasedDate;

    @FXML
    private TextArea PBankSlipInfoTextArea;

    @FXML
    private TextField PPaidDateTextBox;

    @FXML
    private TextField PPaymentStatus;

    @FXML
    private TextArea UpdatablePBankInfoTextArea;

    @FXML
    private DatePicker UpdateableBankPaidDateDatePicker;

    @FXML
    private Label BankSlipInfoLabel;

    @FXML
    private Label BankPaidDateLabel;

    @FXML
    private Button PurchaseCloseButton;

    private void clearFields(){

        try{
            UpdatablePBankInfoTextArea.setText("");
            UpdateableBankPaidDateDatePicker.setValue(null);

        }catch (NullPointerException ex){
            AlertPopUp.generalError(ex);
        }
    }

    private void clearLabels(){

        try{
            BankPaidDateLabel.setText("");
            BankSlipInfoLabel.setText("");
        }catch(NullPointerException ex){

        }
    }
    //validate inputs on inserting and updating
    private boolean dataValidate(){

        boolean returnVal = false;

        if(DataValidation.TextAreaNotEmpty(UpdatablePBankInfoTextArea)
            && DataValidation.DatePickerNotEmpty(UpdateableBankPaidDateDatePicker)){
            returnVal = true;
        }
        return returnVal;
    }
    //prompting user to fix validation errors
    private void dataValidateMessage(){

        //checking for being empty
        if(!(DataValidation.TextAreaNotEmpty(UpdatablePBankInfoTextArea)
                && DataValidation.DatePickerNotEmpty(UpdateableBankPaidDateDatePicker))){
            DataValidation.TextFieldNotEmpty(UpdatablePBankInfoTextArea, BankSlipInfoLabel,"Add Bank Slip Info : Bank Name, Branch, Slip No Here");
            DataValidation.DatePickerNotEmpty(UpdateableBankPaidDateDatePicker, BankPaidDateLabel, "Please Select Deposited Date");
        }
    }

    //getting selected supplier from popup
    @FXML
    public boolean setPurchaseInfo(Purchase purchase){

        boolean resultval = false;

        try{
            // setting supplierInfo data to a static variable for later use and returning true to close stage
            SIDLabel.setText(purchase.getpSupplierID());
            SNameLabel.setText(purchase.getpSupplierName());
            PIIDTextBox.setText(purchase.getpID());
            PINameTextBox.setText(purchase.getpItemName());
            PPricePerUnitTextBox.setText(String.valueOf(purchase.getpPricePerUnit()));
            PQuantityTextBox.setText(String.valueOf(purchase.getpItemQuantity()));
            PTotalAmountTextBox.setText(String.valueOf(purchase.getpItemTotal()));
            PPurchasedDate.setText(purchase.getpPurchaseDate());
            PBankSlipInfoTextArea.setText(purchase.getpBankInfo());
            PPaidDateTextBox.setText(purchase.getpPaidDate());
            PPaymentStatus.setText(purchase.getpStatus());
            resultval = true;
        }catch(NullPointerException ex){
            AlertPopUp.generalError(ex);
        }
        return resultval;
    }
    @FXML
    private void loadSelectedPaidDate(){
        if(!PPaidDateTextBox.getText().equals("None")){
            UpdateableBankPaidDateDatePicker.setValue(LocalDate.parse(PPaidDateTextBox.getText()));
        }
    }
    @FXML
    private void loadSelectedBankInfo(){
        UpdatablePBankInfoTextArea.setText(PBankSlipInfoTextArea.getText());
    }

    private void loadSpecificData(String id){
        Purchase purchaseData = new Purchase();
        PurchaseServices purchaseServices = new PurchaseServices();

        purchaseData = purchaseServices.loadSpecificData(id);
        System.out.println("Data :"+purchaseData.getpItemName());
        SIDLabel.setText(purchaseData.getpSupplierID());
        SNameLabel.setText(purchaseData.getpSupplierName());
        PIIDTextBox.setText(purchaseData.getpID());
        PINameTextBox.setText(purchaseData.getpItemName());
        PPricePerUnitTextBox.setText(String.valueOf(purchaseData.getpPricePerUnit()));
        PQuantityTextBox.setText(String.valueOf(purchaseData.getpItemQuantity()));
        PTotalAmountTextBox.setText(String.valueOf(purchaseData.getpItemTotal()));
       // PTotalAmountTextBox.setText(String.valueOf(purchaseData.getpItemTotal()));
        PPurchasedDate.setText(purchaseData.getpPurchaseDate());
        PBankSlipInfoTextArea.setText(purchaseData.getpBankInfo());
        PPaidDateTextBox.setText(purchaseData.getpPaidDate());
        PPaymentStatus.setText(purchaseData.getpStatus());

    }
    @FXML
    private void updateData(ActionEvent event) throws Exception{

        clearLabels();
        Purchase purchaseData = new Purchase();
        PurchaseServices purchaseServices = new PurchaseServices();

        if(dataValidate()){
            purchaseData.setpID(PIIDTextBox.getText());
            purchaseData.setpBankInfo(UpdatablePBankInfoTextArea.getText());
            purchaseData.setpPaidDate(String.valueOf(UpdateableBankPaidDateDatePicker.getValue()));
            purchaseData.setpStatus("Paid");
            Boolean resultVal = purchaseServices.updateBankInfo(purchaseData);
            if(resultVal){
                clearFields();
                loadSpecificData(purchaseData.getpID());
            }
        }else{
            dataValidateMessage();
        }
    }


    void closeStage(){
        Stage stage = (Stage) PurchaseCloseButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void closeButton(){
        // get a handle to the stage
        closeStage();
    }

}
