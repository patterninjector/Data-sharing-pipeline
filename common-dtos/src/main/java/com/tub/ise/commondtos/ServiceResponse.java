package com.tub.ise.commondtos;

import java.util.List;
import java.util.Map;

public class ServiceResponse {
    private List<Map<String, Object>> processedData;

    // Constructors
    public ServiceResponse() {}

    public ServiceResponse(List<Map<String, Object>> processedData) {
        this.processedData = processedData;
    }

    // Getters and setters
    public List<Map<String, Object>> getProcessedData() {
        return processedData;
    }

    public void setProcessedData(List<Map<String, Object>> processedData) {
        this.processedData = processedData;
    }
}