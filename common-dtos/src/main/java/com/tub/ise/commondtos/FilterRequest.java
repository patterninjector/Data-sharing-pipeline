package com.tub.ise.commondtos;

import java.util.List;
import java.util.Map;

public class FilterRequest {
    private List<Map<String, Object>> data;
    private Map<String, Object> config;

    // Constructors
    public FilterRequest() {}

    public FilterRequest(List<Map<String, Object>> data, Map<String, Object> config) {
        this.data = data;
        this.config = config;
    }

    // Getters and setters
    public List<Map<String, Object>> getData() {
        return data;
    }

    public void setData(List<Map<String, Object>> data) {
        this.data = data;
    }

    public Map<String, Object> getConfig() {
        return config;
    }

    public void setConfig(Map<String, Object> config) {
        this.config = config;
    }
}