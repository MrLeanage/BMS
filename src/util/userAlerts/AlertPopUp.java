package util.userAlerts;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertPopUp {

    public static void connectionError(Exception ex){
        Alert msg = new  Alert(Alert.AlertType.ERROR);
        msg.setTitle("Database Connection Error!..");
        msg.setHeaderText(null);
        msg.setContentText("Database Connection Failed, Exception code in:"+ ex );
        msg.showAndWait();

    }
    public static void sqlQueryError(Exception ex){
        Alert msg = new Alert(Alert.AlertType.ERROR);
        msg.setTitle("SQL Query Error!..");
        msg.setHeaderText(null);
        msg.setContentText("Query Execution Failed, Exception in :"+ ex );
        msg.showAndWait();

    }

    public static void insertSuccesfully(String text){
        Alert msg = new Alert(Alert.AlertType.INFORMATION);
        msg.setTitle("Successfull..");
        msg.setHeaderText(null);
        msg.setContentText(text + " Added Successfully.. " );
        msg.showAndWait();

    }
    public static void insertionFailed(Exception ex, String text){
        Alert msg = new Alert(Alert.AlertType.ERROR);
        msg.setTitle("Error Occured!..");
        msg.setHeaderText(null);
        msg.setContentText(text +" not Added, Try Again!..SQL Exception found in :"+ ex );
        msg.showAndWait();
    }
    public static void updateSuccesfully(String text){
        Alert msg = new Alert(Alert.AlertType.INFORMATION);
        msg.setTitle("Successfull..");
        msg.setHeaderText(null);
        msg.setContentText(text + " Updated Successfully.. " );
        msg.showAndWait();
    }
    public static void updateFailed(Exception ex, String text){
        Alert msg = new Alert(Alert.AlertType.ERROR);
        msg.setTitle("Error Occured!..");
        msg.setHeaderText(null);
        msg.setContentText(text +" not Updated, Try Again!..SQL Exception found in :"+ ex );
        msg.showAndWait();
    }
    public static void deleteSuccesfull(String text){
        Alert msg = new Alert(Alert.AlertType.INFORMATION);
        msg.setTitle("Successfull..");
        msg.setHeaderText(null);
        msg.setContentText(text + " Deleted Successfully.. " );
        msg.showAndWait();

    }
    public static void deleteFailed(Exception ex, String text){
        Alert msg = new Alert(Alert.AlertType.ERROR);
        msg.setTitle("Error Occured!..");
        msg.setHeaderText(null);
        msg.setContentText(text +" not Deleted, Try Again!..SQL Exception found in :"+ ex );
        msg.showAndWait();
    }
    public static Optional<ButtonType> deleteConfirmation(String text){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Your Request to Delete");
        alert.setHeaderText(null);
        alert.setContentText("Do you want to delete selected " +text + "??...");
        Optional<ButtonType> action = alert.showAndWait();
        return action;
    }
    public static void selectRowToDelete(String text){
        Alert successMsg = new Alert(Alert.AlertType.INFORMATION);
        successMsg.setTitle("Please Select..");
        successMsg.setHeaderText(null);
        successMsg.setContentText("Please Select a "+ text + "record to Delete..");
        successMsg.showAndWait();
    }
    public static void generalError(Exception ex){
        Alert msg = new Alert(Alert.AlertType.ERROR);
        msg.setTitle("Error Occured!..");
        msg.setHeaderText(null);
        msg.setContentText("Error Occured, Try Again!..Exception found in Mouse click :"+ ex );
        //msg.showAndWait();
    }
}
