package com.tub.ise.commondtos;


import java.util.List;
import java.util.Map;

public class PipelineRequest {
    private List<PipelineStep> steps;
    private List<Map<String, Object>> inputData;

    // Getters and setters
    public List<PipelineStep> getSteps() {
        return steps;
    }

    public void setSteps(List<PipelineStep> steps) {
        this.steps = steps;
    }

    public List<Map<String, Object>> getInputData() {
        return inputData;
    }

    public void setInputData(List<Map<String, Object>> inputData) {
        this.inputData = inputData;
    }
}
