package util.utility;


import javafx.collections.ObservableList;
import model.ChartData;
import model.PaySheet;
import model.Purchase;
import model.SalesItem;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JRViewer;
import util.dbConnect.DBConnection;
import util.userAlerts.AlertPopUp;
import view.FinanceManagement.IncomeStatemetAdminController;

import javax.swing.*;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;


public class PrintReport extends JFrame{
    public void printPaySheet(PaySheet paySheet) {
        DBConnection SqlConnection = new DBConnection();
        Connection conn = SqlConnection.Connect();// Database Connection
        try{
            HashMap parameter = new HashMap();
            parameter.put("pSID", paySheet.getpSID());
            parameter.put("pSEID", UtilityMethod.seperateID(paySheet.getpSEID()));
            parameter.put("pSEName", paySheet.getpSEName());
            parameter.put("pSNIC", paySheet.getpSNIC());
            parameter.put("pSBSSTitle", paySheet.getpSBSSTitle());
            parameter.put("pSBSSAmount", paySheet.getpSBSSAmount());
            parameter.put("pSBankName", paySheet.getpSBankName());
            parameter.put("pSAccountNo", paySheet.getpSAccountNo());
            parameter.put("pYear", UtilityMethod.getYear(paySheet.getpSDate()));
            parameter.put("pMonth", UtilityMethod.getMonth(paySheet.getpSDate()));

            JasperDesign jd = JRXmlLoader.load(new File("").getAbsolutePath()+"/src/view/JRXMLReports/Paysheet.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(jd);
            JasperPrint JasperPrint = JasperFillManager.fillReport(jasperReport, parameter, conn);
            JRViewer viewer = new JRViewer(JasperPrint);

            //viewer.setOpaque(true);
            viewer.setVisible(true);

            add(viewer);
            this.setSize(850,800); // JFrame size
            this.setVisible(true);
            try {


            } catch (Exception ex) {
                AlertPopUp.generalError(ex);
            }finally {
                try{
                    conn.close();
                }catch(SQLException ex){
                    AlertPopUp.generalError(ex);
                }
            }
        }catch (JRException e) {
            e.printStackTrace();
        }
    }
    public void printPaySheetList() {
        DBConnection SqlConnection = new DBConnection();
        Connection conn = SqlConnection.Connect();// Database Connection
        String period = null;
        if(String.valueOf(LocalDate.now().getMonthValue()).length() < 2){
            period = LocalDate.now().getYear() + "-0" + LocalDate.now().getMonthValue();
        }else{
            period = LocalDate.now().getYear() + "-" + LocalDate.now().getMonthValue();
        }
        try {
            HashMap parameter = new HashMap();
            parameter.put("pPeriod", period);
            parameter.put("pYear", UtilityMethod.getYear(String.valueOf(LocalDate.now())));
            parameter.put("pMonth", UtilityMethod.getMonth(String.valueOf(LocalDate.now())));

            JasperDesign jd = JRXmlLoader.load(new File("").getAbsolutePath()+"/src/view/JRXMLReports/PaysheetList.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(jd);
            JasperPrint JasperPrint = JasperFillManager.fillReport(jasperReport, parameter, conn);
            JRViewer viewer = new JRViewer(JasperPrint);
            //viewer.setOpaque(true);
            viewer.setOpaque(true);
            viewer.setVisible(true);

            this.add(viewer);
            this.setSize(1300,800); // Frame size
            this.setVisible(true);

        } catch (Exception e) {
            AlertPopUp.generalError(e);
        }finally {
            try{
                conn.close();
            }catch(SQLException ex){
                AlertPopUp.connectionError(ex);
            }
        }

    }
    public void printNewOrderInfo(String oID) {
        DBConnection SqlConnection = new DBConnection();
        Connection conn = SqlConnection.Connect();// Database Connection

        try {
            HashMap parameter = new HashMap();
            System.out.println("ID : " +oID);
            parameter.put("pOID", UtilityMethod.seperateID(oID));
            parameter.put("pStatus", "Process Pending");

            JasperDesign jd = JRXmlLoader.load(new File("").getAbsolutePath()+"/src/view/JRXMLReports/OrderInfo.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(jd);
            JasperPrint JasperPrint = JasperFillManager.fillReport(jasperReport, parameter, conn);
            JRViewer viewer = new JRViewer(JasperPrint);
            //viewer.setOpaque(true);
            viewer.setOpaque(true);
            viewer.setVisible(true);

            this.add(viewer);
            this.setSize(1300,800); // Frame size
            this.setVisible(true);

        } catch (Exception e) {
            System.out.println(e);
            //AlertPopUp.generalError(e);
        }finally {
            try{
                conn.close();
            }catch(SQLException ex){
                AlertPopUp.connectionError(ex);
            }
        }

    }
    public void printBill(Integer billNo) {
        DBConnection SqlConnection = new DBConnection();
        Connection conn = SqlConnection.Connect();// Database Connection
        try {
            //HashMap parameter = new HashMap();
            HashMap parameter = new HashMap();
            parameter.put("billNo",billNo);

            JasperDesign jd = JRXmlLoader.load(new File("").getAbsolutePath()+"/src/view/JRXMLReports/Bill.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(jd);
            JasperPrint JasperPrint = JasperFillManager.fillReport(jasperReport, parameter, conn);
            JRViewer viewer = new JRViewer(JasperPrint);
            //viewer.setOpaque(true);
            viewer.setOpaque(true);
            viewer.setVisible(true);

            this.add(viewer);
            this.setSize(600,800); // Frame size
            this.setVisible(true);

        } catch (Exception e) {
            AlertPopUp.generalError(e);
        }finally {
            try{
                conn.close();
            }catch(SQLException ex){
                AlertPopUp.connectionError(ex);
            }
        }

    }
    public void printSalesReport(int year, String month) {
        DBConnection SqlConnection = new DBConnection();
        Connection conn = SqlConnection.Connect();// Database Connection
        String period = null;
        if(String.valueOf(UtilityMethod.getMonthNumber(month)).length() < 2){
            period = year + "-0" + UtilityMethod.getMonthNumber(month);
        }else{
            period = year + "-" + UtilityMethod.getMonthNumber(month);
        }
        try {
            HashMap parameter = new HashMap();
            parameter.put("sPeriod", period);
            parameter.put("sYear", year);
            parameter.put("sMonth", month);

            JasperDesign jd = JRXmlLoader.load(new File("").getAbsolutePath()+"/src/view/JRXMLReports/SalesReport.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(jd);
            JasperPrint JasperPrint = JasperFillManager.fillReport(jasperReport, parameter, conn);
            JRViewer viewer = new JRViewer(JasperPrint);
            //viewer.setOpaque(true);
            viewer.setOpaque(true);
            viewer.setVisible(true);

            this.add(viewer);
            this.setSize(1300,800); // Frame size
            this.setVisible(true);

        } catch (Exception e) {
            AlertPopUp.generalError(e);
        }finally {
            try{
                conn.close();
            }catch(SQLException ex){
                AlertPopUp.connectionError(ex);
            }
        }

    }
    public void printPurchaseReport(int year, String month, String category, String status, ObservableList<Purchase> purchaseLinkedList){


        try {
            HashMap parameter = new HashMap();
            JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(purchaseLinkedList);

            parameter.put("pPeriod", year + " " + month);
            parameter.put("pCategory", category);
            parameter.put("pStatus", status);
            parameter.put("pPurchaseLinkedList", jrBeanCollectionDataSource);

            JasperDesign jd = JRXmlLoader.load(new File("").getAbsolutePath()+"/src/view/JRXMLReports/PurchasePay.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(jd);
            JasperPrint JasperPrint = JasperFillManager.fillReport(jasperReport, parameter, new JREmptyDataSource());
            JRViewer viewer = new JRViewer(JasperPrint);
            //viewer.setOpaque(true);
            viewer.setOpaque(true);
            viewer.setVisible(true);

            this.add(viewer);
            this.setSize(1300,800); // Frame size
            this.setVisible(true);

        } catch (Exception e) {
            AlertPopUp.generalError(e);
        }

    }
    public void printIncomeStatement(String period){
        HashMap parameter = new HashMap();
        DBConnection SqlConnection = new DBConnection();
        Connection conn = SqlConnection.Connect();// Database Connection

        parameter.put("iSPeriod", period);

        try {
            JasperDesign jd = JRXmlLoader.load(new File("").getAbsolutePath()+"/src/view/JRXMLReports/IncomeStatement.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(jd);
            JasperPrint JasperPrint = JasperFillManager.fillReport(jasperReport, parameter, conn);
            JRViewer viewer = new JRViewer(JasperPrint);
            //viewer.setOpaque(true);
            viewer.setOpaque(true);
            viewer.setVisible(true);

            this.add(viewer);
            this.setSize(1300,800); // Frame size
            this.setVisible(true);

        } catch (Exception e) {
            AlertPopUp.generalError(e);
        }

    }
}
