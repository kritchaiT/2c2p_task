package com.example;

public class Report {
    private String reportId;
    private String reportName;
    private String reportType;
    private String reportDate;

    public Report(String reportId, String reportName, String reportType, String reportDate) {
        this.reportId = reportId;
        this.reportName = reportName;
        this.reportType = reportType;
        this.reportDate = reportDate;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }
}