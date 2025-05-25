package com.tub.ise.coordinatorservice.config;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PipelineConfig {
    private String name;
    private String description;
    private List<PipelineStepConfig> steps;
    private String inputFile;

    private String processingMode; // NORMAL, FLAKY, SLOW, BROKEN_URL

    // Getters and Setters
    @JsonProperty("name")
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @JsonProperty("description")
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @JsonProperty("steps")
    public List<PipelineStepConfig> getSteps() { return steps; }
    public void setSteps(List<PipelineStepConfig> steps) { this.steps = steps; }

    @JsonProperty("inputFile")
    public String getInputFile() { return inputFile; }
    public void setInputFile(String inputFile) { this.inputFile = inputFile; }

    @JsonProperty("processingMode")
    public String getProcessingMode() {
        return processingMode;
    }

    public void setProcessingMode(String processingMode) {
        this.processingMode = processingMode;
    }
}