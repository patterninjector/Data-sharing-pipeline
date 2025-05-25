package com.tub.ise.commondtos;



import java.util.List;

public class PipelineResponse {
    private Object result;
    private List<StepResult> stepResults;

    // Getters and setters
    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public List<StepResult> getStepResults() {
        return stepResults;
    }

    public void setStepResults(List<StepResult> stepResults) {
        this.stepResults = stepResults;
    }

    public static class StepResult {
        private String serviceName;
        private String status;
        private String message;

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}