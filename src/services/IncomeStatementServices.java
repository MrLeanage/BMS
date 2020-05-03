package services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ChartData;
import org.apache.poi.ss.usermodel.Chart;
import util.dbConnect.DBConnection;
import util.query.IncomeStatementQueries;
import util.query.OrderQueries;
import util.userAlerts.AlertPopUp;
import util.utility.UtilityMethod;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IncomeStatementServices {
    public Boolean UpdateIncomeStatementInfo(ObservableList<ChartData> incomeStatementData){
        Boolean resultVal = false;
        PreparedStatement psloadIncomeStatement = null;
        PreparedStatement psInsertIncomeStatement = null;
        PreparedStatement psUpdateSpecificData = null;
        PreparedStatement psLoadSpecificData = null;

        ResultSet rsLoadData = null;
        ResultSet rsLoadSpecificData = null;
        try {
            Connection conn = DBConnection.Connect();

            psLoadSpecificData = conn.prepareStatement(IncomeStatementQueries.LOAD_SPECIFIC_DATA_QUERY);

            psInsertIncomeStatement = conn.prepareStatement(IncomeStatementQueries.INSERT_DATA_QUERY);

            psUpdateSpecificData = conn.prepareStatement(IncomeStatementQueries.UPDATE_DATA_QUERRY);

            rsLoadData = conn.createStatement().executeQuery(IncomeStatementQueries.LOAD_DATA_QUERY);


            if(!rsLoadData.isBeforeFirst()){
                for(ChartData chartData : incomeStatementData){
                    psInsertIncomeStatement.setString(1, chartData.getDataType());
                    psInsertIncomeStatement.setString(2, chartData.getDataYearMonth());
                    psInsertIncomeStatement.setDouble(3, chartData.getDataValue());
                    psInsertIncomeStatement.execute();
                }

            }else{
                for(ChartData chartData : incomeStatementData){
                    psLoadSpecificData.setString(1, chartData.getDataType());
                    psLoadSpecificData.setString(2, chartData.getDataYearMonth());
                    rsLoadSpecificData = psLoadSpecificData.executeQuery();
                    if(!rsLoadSpecificData.isBeforeFirst()){
                        psInsertIncomeStatement.setString(1, chartData.getDataType());
                        psInsertIncomeStatement.setString(2, chartData.getDataYearMonth());
                        psInsertIncomeStatement.setDouble(3, chartData.getDataValue());
                        psInsertIncomeStatement.execute();
                    }
                    while(rsLoadSpecificData.next()){
                        if(!(rsLoadSpecificData.getString(2).equals(chartData.getDataType())
                                && rsLoadSpecificData.getString(3).equals(chartData.getDataYearMonth())
                                && rsLoadSpecificData.getDouble(4) == chartData.getDataValue())){

                            psUpdateSpecificData.setString(1, chartData.getDataType());
                            psUpdateSpecificData.setString(2, chartData.getDataYearMonth());
                            psUpdateSpecificData.setDouble(3, chartData.getDataValue());
                            psUpdateSpecificData.setInt(4, rsLoadSpecificData.getInt(1));
                            psUpdateSpecificData.execute();
                        }
                    }
                }
            }

        } catch (SQLException ex) {
            AlertPopUp.sqlQueryError(ex);
        }
        finally{
            try{
                if(psInsertIncomeStatement != null){
                    psInsertIncomeStatement.close();
                }
                if(psUpdateSpecificData != null){
                    psUpdateSpecificData.close();
                }
                if(rsLoadSpecificData != null){
                    rsLoadSpecificData.close();
                }
                if(psLoadSpecificData != null){
                    psLoadSpecificData.close();
                }
                if(rsLoadData != null){
                    rsLoadData.close();
                }
            }catch(SQLException ex){
                AlertPopUp.sqlQueryError(ex);
            }
        }
        return resultVal;
    }
}
