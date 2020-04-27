package util.authenticate;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import util.userAlerts.AlertPopUp;

import java.io.IOException;

public class EmployeeSessionHandler {
    public EmployeeSessionHandler() {
    }
    public void loadSalarySchemes(AnchorPane rootpane){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/EmployeeManagement/SalarySchemes.fxml"));
            rootpane.getChildren().setAll(pane);
        }catch(IOException ex){
            AlertPopUp.generalError(ex);
        }
    }
    public void loadAllowances(AnchorPane rootpane){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/EmployeeManagement/Allowances.fxml"));
            rootpane.getChildren().setAll(pane);
        }catch(IOException ex){
            AlertPopUp.generalError(ex);
        }
    }
    public void loadSystemUsers(AnchorPane rootpane){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/EmployeeManagement/SystemUsers.fxml"));
            rootpane.getChildren().setAll(pane);
        }catch(IOException ex){
            AlertPopUp.generalError(ex);
        }
    }
}
