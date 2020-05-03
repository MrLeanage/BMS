package model;

public class ChartData {
    private Integer dataYear = null;
    private String dataMonth = null;
    private Double dataValue = null;
    private String dataType = null;
    private String dataYearMonth = null;

    public ChartData(Integer dataYear, String dataMonth, Double dataValue, String dataType) {
        this.dataYear = dataYear;
        this.dataMonth = dataMonth;
        this.dataValue = dataValue;
        this.dataType = dataType;
        this.dataYearMonth =  dataMonth + " - " +  dataYear;
    }

    public ChartData() {
    }

    public Integer getDataYear() {
        return dataYear;
    }

    public void setDataYear(Integer dataYear) {
        this.dataYear = dataYear;
    }

    public String getDataMonth() {
        return dataMonth;
    }

    public void setDataMonth(String dataMonth) {
        this.dataMonth = dataMonth;
    }

    public Double getDataValue() {
        return dataValue;
    }

    public void setDataValue(Double dataValue) {
        this.dataValue = dataValue;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataYearMonth() {
        return dataYearMonth;
    }

    public void setDataYearMonth(String dataYearMonth) {
        this.dataYearMonth = dataYearMonth;
    }
}
