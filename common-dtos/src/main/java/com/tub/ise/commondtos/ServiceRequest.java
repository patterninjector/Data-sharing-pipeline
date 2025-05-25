package com.tub.ise.commondtos;

import java.util.List;
import java.util.Map;

public class ServiceRequest {
    private List<Map<String, Object>> data;
    private Map<String, Object> config;
    private Map<String, String> metadata;  // Optional for pipeline tracking

    // Constructors
    public ServiceRequest() {
    }

    public ServiceRequest(List<Map<String, Object>> data, Map<String, Object> config) {
        this.data = data;
        this.config = config;
    }

    public ServiceRequest(List<Map<String, Object>> data,
                          Map<String, Object> config,
                          Map<String, String> metadata) {
        this.data = data;
        this.config = config;
        this.metadata = metadata;
    }

    // Getters and Setters
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

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    // Utility Methods
    public String getConfigValue(String key) {
        return config != null ? config.get(key).toString() : null;
    }

    public String getMetadataValue(String key) {
        return metadata != null ? metadata.get(key) : null;
    }
}
