package util.userNotifications;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.AgencyProduct;
import model.ItemStock;
import model.Order;
import org.controlsfx.control.Notifications;
import services.AgencyProductServices;
import services.ItemStockServices;
import services.OrderServices;
import util.authenticate.AdminManagementHandler;
import util.authenticate.UserAuthentication;
import util.userAlerts.AlertPopUp;
import util.utility.UtilityMethod;

import javax.management.monitor.Monitor;
import java.time.LocalDate;

public class Notification extends Thread{
    private ActionEvent actionEvent;
    private static Timeline fiveSecondsWonder = new Timeline();
    private static ObservableList<KeyFrame> keyFrameObservableList = FXCollections.observableArrayList();
    public Notification(){
        setKeyFrames();
    }
    public void setActionEvent(ActionEvent actionEvent){
        this.actionEvent = actionEvent;
    }
    public static void stopExecution(){
            fiveSecondsWonder.stop();
            fiveSecondsWonder.getKeyFrames().clear();
            keyFrameObservableList.clear();
    }
    private void setKeyFrames(){
        if(UserAuthentication.getAuthenticatedSession().getuType().equals("Admin")){
            keyFrameObservableList.add(agencyProductDiscontinueAlert(15));
            keyFrameObservableList.add(lowStock(15));
            keyFrameObservableList.add(outOfStock(15));
            keyFrameObservableList.add(agencyProductExpireAlert(15));
            keyFrameObservableList.add(stockItemExpireAlert(15));

        }
        if(UserAuthentication.getAuthenticatedSession().getuType().equals("Supervisor")){
            keyFrameObservableList.add(newOrderNotification(15));

        }
        fiveSecondsWonder.getKeyFrames().addAll(keyFrameObservableList);
        fiveSecondsWonder.setDelay(Duration.seconds(2));
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();
    }

    private KeyFrame lowStock(Integer frameDuration)  {
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(frameDuration), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                ObservableList<ItemStock> itemStockObservableList = FXCollections.observableArrayList();
                ItemStockServices itemStockServices = new ItemStockServices();
                itemStockObservableList = itemStockServices.loadData();
                for(ItemStock itemStock : itemStockObservableList){

                    if(UtilityMethod.setItemAvailabilityStatus(itemStock.getiAvailableQuantity(), itemStock.getiMinQuantityLimit()).equals("Low Stock") && UserAuthentication.getAuthenticatedSession().getuType().equals("Admin")){
                        alertNotificationStage("Stock Items Low", "Your "+itemStock.getiName() + " is running out.\n Please order Stocks from Supplier : " + itemStock.getiSISupplierName(),event );
                    }
                }
            }
        });
        return keyFrame;
    }
    private KeyFrame outOfStock(Integer frameDuration)  {
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(frameDuration), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                ObservableList<ItemStock> itemStockObservableList = FXCollections.observableArrayList();
                ItemStockServices itemStockServices = new ItemStockServices();
                itemStockObservableList = itemStockServices.loadData();
                for(ItemStock itemStock : itemStockObservableList){
                    if(UtilityMethod.setItemAvailabilityStatus(itemStock.getiAvailableQuantity(), itemStock.getiMinQuantityLimit()).equals("Out of Stock") && UserAuthentication.getAuthenticatedSession().getuType().equals("Admin")){
                        errorNotificationStage("Out of Stock Items ", "Your "+itemStock.getiName() + " is out of Stock.\n Please order Stocks from Supplier : " + itemStock.getiSISupplierName(),event );
                    }
                }
            }
        });
        return keyFrame;
    }
    private KeyFrame agencyProductDiscontinueAlert(Integer frameDuration){

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(frameDuration), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                ObservableList<AgencyProduct> agencyProductObservableList = FXCollections.observableArrayList();
                AgencyProductServices agencyProductServices = new AgencyProductServices();
                agencyProductObservableList = agencyProductServices.loadData();
                for(AgencyProduct agencyProduct : agencyProductObservableList){
                    //getting discontinue alert date count
                    Integer[] noOfDADDates = new Integer[3];
                    noOfDADDates = UtilityMethod.getNoOfDates(String.valueOf(LocalDate.now()), agencyProduct.getaPDADate());

                    //getting days to expire
                    Integer noOfDaysToExpire = UtilityMethod.getTotalNoOfDays(String.valueOf(LocalDate.now()), agencyProduct.getaPEDate());

                    //getting difference between exire and discontinue alert date
                    Integer[] noOfDaysDifference = new Integer[3];
                    noOfDaysDifference = UtilityMethod.getNoOfDates(agencyProduct.getaPDADate(), agencyProduct.getaPEDate());

                    if((noOfDADDates[0] < 1 ) && (noOfDADDates[1] < 1 ) && (noOfDADDates[2] < 1 ) &&  (noOfDaysToExpire >= noOfDaysDifference[2]) && UserAuthentication.getAuthenticatedSession().getuType().equals("Admin")){
                        alertNotificationStage("Agency Item about to expire", "Agency Item  "+agencyProduct.getaPName()
                                + " is about to expire.\n\t Expiration Date : " + agencyProduct.getaPEDate()
                                +"\n\t Days to expire   : " +noOfDaysToExpire   ,event );
                    }
                }
            }
        });
        return keyFrame;
    }
    private KeyFrame agencyProductExpireAlert(Integer frameDuration){

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(frameDuration), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                ObservableList<AgencyProduct> agencyProductObservableList = FXCollections.observableArrayList();
                AgencyProductServices agencyProductServices = new AgencyProductServices();
                agencyProductObservableList = agencyProductServices.loadData();
                for(AgencyProduct agencyProduct : agencyProductObservableList){
                    //getting days to expire
                    Integer noOfDaysToExpire =  UtilityMethod.getTotalNoOfDays( String.valueOf(LocalDate.now()), agencyProduct.getaPEDate());

                    if((noOfDaysToExpire < 1 ) && UserAuthentication.getAuthenticatedSession().getuType().equals("Admin") ){
                        errorNotificationStage("Agency Item expired", "Agency Item  "+agencyProduct.getaPName()
                                + " was expired.\n\t Expiration Date : " + agencyProduct.getaPEDate() ,event );
                    }
                }
            }
        });
        return keyFrame;
    }
    private KeyFrame stockItemExpireAlert(Integer frameDuration){

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(frameDuration), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                ObservableList<ItemStock> itemStockObservableList = FXCollections.observableArrayList();
                ItemStockServices itemStockServices = new ItemStockServices();
                itemStockObservableList = itemStockServices.loadData();
                for(ItemStock itemStock : itemStockObservableList){
                    //getting days to expire
                    Integer noOfDaysToExpire =  UtilityMethod.getTotalNoOfDays( String.valueOf(LocalDate.now()), itemStock.getiExpireDate());

                    if((noOfDaysToExpire < 1 ) && UserAuthentication.getAuthenticatedSession().getuType().equals("Admin")){
                        errorNotificationStage("Stock Item expired", "Stock Item  "+itemStock.getiName()
                                + " was expired.\n\t Expiration Date : " + itemStock.getiExpireDate() ,event );
                    }
                }
            }
        });
        return keyFrame;
    }
    private KeyFrame newOrderNotification(Integer frameDuration){

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(frameDuration), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                ObservableList<Order> orderObservableList = FXCollections.observableArrayList();
                OrderServices orderServices = new OrderServices();
                orderObservableList = orderServices.loadData("Process Pending");
                for(Order order : orderObservableList){
                    if(UserAuthentication.getAuthenticatedSession().getuType().equals("Supervisor")){
                        informationNotificationStage("New Order : " + order.getoID() + " has been Placed for "+order.getoOMName(),
                                 "\n\t Delivering Date | Time : " + order.getoDeliveryDate() + " "  + order.getoDeliveryTime(),event );
                    }
                }
            }
        });
        return keyFrame;
    }
    //Notification Stages
    private void alertNotificationStage(String title, String text, ActionEvent action){

        Notifications notificationBuilder = Notifications.create()
                .title(title)
                .text(text)
                .graphic(null)
                .hideAfter(Duration.seconds(10))
                .position(Pos.BOTTOM_RIGHT)

                .onAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                       // AdminManagementHandler adminManagementHandler = new AdminManagementHandler();
                        //adminManagementHandler.loadItemStock(action);
                    }
                });
        notificationBuilder.showWarning();

    }
    private void errorNotificationStage(String title, String text, ActionEvent action){

        Notifications notificationBuilder = Notifications.create()
                .title(title)
                .text(text)
                .graphic(null)
                .hideAfter(Duration.seconds(10))
                .position(Pos.BOTTOM_RIGHT)

                .onAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        //AdminManagementHandler adminManagementHandler = new AdminManagementHandler();
                        //adminManagementHandler.loadItemStock(actionEvent);
                    }
                });
        notificationBuilder.showError();
    }
    private void informationNotificationStage(String title, String text, ActionEvent action) {

        Notifications notificationBuilder = Notifications.create()
                .title(title)
                .text(text)
                .graphic(null)
                .hideAfter(Duration.seconds(10))
                .position(Pos.BOTTOM_RIGHT)


                .onAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        //AdminManagementHandler adminManagementHandler = new AdminManagementHandler();
                        //adminManagementHandler.loadItemStock(actionEvent);
                    }
                });
        notificationBuilder.showInformation();


    }

}
