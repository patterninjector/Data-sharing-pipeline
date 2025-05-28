package com.tub.ise.commondtos;

import java.util.List;
import java.util.Map;

public class ServiceResponse {
    private List<Map<String, Object>> processedData;
    private String processedDataString;


    // Constructors
    public ServiceResponse() {}

    public ServiceResponse(List<Map<String, Object>> processedData) {
        this.processedData = processedData;
    }

    public ServiceResponse(String processedCsvData) {
        this.processedDataString = processedCsvData;
    }

    // Getters and setters
    public List<Map<String, Object>> getProcessedData() {
        return processedData;
    }

    public void setProcessedData(List<Map<String, Object>> processedData) {
        this.processedData = processedData;
    }

    public String getProcessedDataString() {
        return processedDataString;
    }

    public void setProcessedDataString(String processedDataString) {
        this.processedDataString = processedDataString;
    }
}