package com.tub.ise.commondtos;


import java.util.Map;

public class PipelineStep {
    private String serviceName;
    private Map<String, Object> config;

    // Getters and setters
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Map<String, Object> getConfig() {
        return config;
    }

    public void setConfig(Map<String, Object> config) {
        this.config = config;
    }
}