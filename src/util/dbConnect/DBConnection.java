/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.dbConnect;

import util.userAlerts.AlertPopUp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    
    public static Connection Connect(){
        AlertPopUp popUpMsg = new AlertPopUp();

        try {
            String url = "jdbc:mysql://localhost:3306/bms?useSSL=false";
            String user = "root";
            String password = "uthpala";
            
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            return conn;

        } catch (ClassNotFoundException | SQLException ex) {
            popUpMsg.connectionError(ex);
        }
        return null;
    }
    
    
}
