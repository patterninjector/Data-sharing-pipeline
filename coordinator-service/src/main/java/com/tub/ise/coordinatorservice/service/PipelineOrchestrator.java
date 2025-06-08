package com.tub.ise.coordinatorservice.service;




import com.fasterxml.jackson.databind.ObjectMapper;
import com.tub.ise.commondtos.ServiceRequest;
import com.tub.ise.commondtos.ServiceResponse;
import com.tub.ise.coordinatorservice.config.PipelineConfig;
import com.tub.ise.coordinatorservice.config.PipelineStepConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@Service
public class PipelineOrchestrator {

//    private final ServiceClientFactory serviceClientFactory;
//
//    @Autowired
//    public PipelineOrchestrator(ServiceClientFactory serviceClientFactory) {
//        this.serviceClientFactory = serviceClientFactory;
//    }

//    public PipelineResponse executePipeline(PipelineRequest request) {
//        PipelineResponse response = new PipelineResponse();
//        List<PipelineResponse.StepResult> stepResults = new ArrayList<>();
//        Object currentData = request.getInputData();
//
//        for (PipelineStep step : request.getSteps()) {
//            PipelineResponse.StepResult stepResult = new PipelineResponse.StepResult();
//            stepResult.setServiceName(step.getServiceName());
//
//            try {
//                ServiceClient client = serviceClientFactory.getClient(step.getServiceName());
//                ServiceRequest serviceRequest = new ServiceRequest();
//                serviceRequest.setData((List<Map<String, Object>>) currentData);
//                serviceRequest.setConfig(step.getConfig());
//
//                ServiceResponse serviceResponse = client.process(serviceRequest);
//                currentData = serviceResponse.getProcessedData();
//
//                stepResult.setStatus("SUCCESS");
//            } catch (Exception e) {
//                stepResult.setStatus("FAILED");
//                stepResult.setMessage(e.getMessage());
//                break;
//            } finally {
//                stepResults.add(stepResult);
//            }
//        }
//
//        response.setResult(currentData);
//        response.setStepResults(stepResults);
//        return response;
//    }
   private final ServiceClientFactory clientFactory;
    private final ResourceLoader resourceLoader;
    private final ObjectMapper objectMapper;

    @Autowired
    private  RestTemplate restTemplate;

    private final NonexistentServiceClient nonexistentServiceClient;

    public PipelineOrchestrator(ServiceClientFactory clientFactory,
                                ResourceLoader resourceLoader,
                                ObjectMapper objectMapper, NonexistentServiceClient nonexistentServiceClient) {
        this.clientFactory = clientFactory;
        this.resourceLoader = resourceLoader;
        this.objectMapper = objectMapper;
        this.nonexistentServiceClient = nonexistentServiceClient;
    }

    public void executePipelineInputFile(PipelineConfig pipelineConfig) {
        try {
            // Read input data
            Resource inputResource = resourceLoader.getResource(pipelineConfig.getInputFile());
            List<Map<String, Object>> inputData = objectMapper.readValue(
                    inputResource.getInputStream(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Map.class)
            );

            // Process each step
            Object currentData = inputData;
            for (PipelineStepConfig step : pipelineConfig.getSteps()) {
                ServiceRequest request = new ServiceRequest((List<Map<String, Object>>) currentData, step.getConfig());
                ServiceResponse response = processStepWithMode(step, request, pipelineConfig.getProcessingMode());
                currentData = response.getProcessedData();
            }


            savePipelineOutput(pipelineConfig,currentData);

        } catch (IOException e) {
            throw new RuntimeException("Pipeline execution failed", e);
        }
    }

    public void executePipelineInputUrl(PipelineConfig pipelineConfig) {
        try {
            Thread.sleep(3000);
            String url = pipelineConfig.getInputFile(); // e.g. "…/all.json"
            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "Mozilla/5.0");
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> jsonData = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            // Read the top-level map
            Map<String, Object> jsonMap = objectMapper.readValue(jsonData.getBody(), Map.class);

            // Get the "index" array inside the map
            Object indexObj = jsonMap.get("index");
            System.out.print("The Response From URL");

            if (!(indexObj instanceof List)) {
                throw new RuntimeException("Expected 'index' to be a list");
            }

            @SuppressWarnings("unchecked")
            List<Map<String, Object>> inputData = (List<Map<String, Object>>) indexObj;
            System.out.print("The InputData From URL");

            // Process each step
            Object currentData = inputData;
            for (PipelineStepConfig step : pipelineConfig.getSteps()) {
                System.out.print("Executing the Step:" + step.getService());
                ServiceRequest request = new ServiceRequest((List<Map<String, Object>>) currentData, step.getConfig());
                ServiceResponse response = processStepWithMode(step, request, pipelineConfig.getProcessingMode());
                if(step.getService().equalsIgnoreCase("formatting")) {
                    currentData = response.getProcessedDataString();
                    }else{
                currentData = response.getProcessedData();
                }
            }

           savePipelineOutput(pipelineConfig,currentData);
        } catch (IOException e) {
            throw new RuntimeException("Pipeline execution failed", e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    private ServiceResponse processStepWithMode(PipelineStepConfig step,
                                                ServiceRequest request,
                                                String processingMode) {
        return switch (processingMode != null ? processingMode : "NORMAL") {
            case "FLAKY" -> callFlakyEndpoint(step.getService(), request);
            case "SLOW" -> callSlowEndpoint(step.getService(), request);
            case "BROKEN_URL" -> callBrokenUrl(step.getService(), request);
            default -> callNormalEndpoint(step.getService(), request);
        };
    }


    private ServiceResponse callNormalEndpoint(String serviceName, ServiceRequest request) {
        return clientFactory.getClient(serviceName).processNormal(request);
    }

    private ServiceResponse callFlakyEndpoint(String serviceName, ServiceRequest request) {
        return clientFactory.getClient(serviceName).processFlaky(request);
    }

    private ServiceResponse callSlowEndpoint(String serviceName, ServiceRequest request) {
        return clientFactory.getClient(serviceName).processSlow(request);
    }
    private ServiceResponse callBrokenUrl(String serviceName, ServiceRequest request) {
            return nonexistentServiceClient.process(request);
            //for non-valid path test scenario
            //return clientFactory.getClient(serviceName).processBlock(request);
    }


    private void savePipelineOutput(PipelineConfig config, Object data) throws IOException {
        String outputDir = "output";
        Files.createDirectories(Paths.get(outputDir));
        String outputFileName = outputDir + "/" + config.getName() +"_"+config.getProcessingMode()+"_"+String.valueOf(Math.random())+ "_output.json";
        objectMapper.writerWithDefaultPrettyPrinter()
                .writeValue(new File(outputFileName), data);
        System.out.println("Pipeline completed: " + config.getName() +
                ", output saved to " + outputFileName);
    }
}
