package com.tub.ise.coordinatorservice.config;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class PipelineStepConfig {
    private String service;
    private Map<String, Object> config;

    // Getters and Setters
    @JsonProperty("service")
    public String getService() { return service; }
    public void setService(String service) { this.service = service; }

    @JsonProperty("config")
    public Map<String, Object> getConfig() { return config; }
    public void setConfig(Map<String, Object> config) { this.config = config; }
}
