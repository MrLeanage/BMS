package util.authenticate;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import util.userAlerts.AlertPopUp;

import java.io.IOException;

public class FinanceSessionHandler {
    public FinanceSessionHandler() {
    }
    public void loadSalesReport(AnchorPane rootpane){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/FinanceManagement/SalesReportAdmin.fxml"));
            rootpane.getChildren().setAll(pane);
        }catch(IOException ex){
            AlertPopUp.generalError(ex);
        }
    }
    public void loadPurchasesReport(AnchorPane rootpane){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/FinanceManagement/PurchasesReportAdmin.fxml"));
            rootpane.getChildren().setAll(pane);
        }catch(IOException ex){
            AlertPopUp.generalError(ex);
        }
    }
    public void loadPaySheet(AnchorPane rootpane){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/FinanceManagement/PaySheetAdmin.fxml"));
            rootpane.getChildren().setAll(pane);
        }catch(IOException ex){
            AlertPopUp.generalError(ex);
        }
    }
    public void loadPayRoll(AnchorPane rootpane){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/FinanceManagement/PayRollAdmin.fxml"));
            rootpane.getChildren().setAll(pane);
        }catch(IOException ex){
            AlertPopUp.generalError(ex);
        }
    }
    public void loadOtherExpenses(AnchorPane rootpane){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/FinanceManagement/OtherExpensesAdmin.fxml"));
            rootpane.getChildren().setAll(pane);
        }catch(IOException ex){
            AlertPopUp.generalError(ex);
        }
    }
    public void loadIncomeStatement(AnchorPane rootpane){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/FinanceManagement/IncomeStatementAdmin.fxml"));
            rootpane.getChildren().setAll(pane);
        }catch(IOException ex){
            AlertPopUp.generalError(ex);
        }
    }
}
