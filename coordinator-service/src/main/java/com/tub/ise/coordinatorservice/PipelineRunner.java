package com.tub.ise.coordinatorservice;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tub.ise.coordinatorservice.config.PipelineConfig;
import com.tub.ise.coordinatorservice.service.PipelineOrchestrator;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class PipelineRunner {

    private final PipelineOrchestrator orchestrator;
    private final ObjectMapper objectMapper;

    public PipelineRunner(PipelineOrchestrator orchestrator, ObjectMapper objectMapper) {
        this.orchestrator = orchestrator;
        this.objectMapper = objectMapper;
    }

    public void runPipelinesFromDirectory(String directoryPath) throws IOException {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources(directoryPath + "/*.json");

        Arrays.stream(resources)
                .map(this::readPipelineConfig)
                .forEach(this::executePipelineInputFile);
    }

    public void runPipelineFromPath(String resourcePath) {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        try {
            Resource resource = resolver.getResource(resourcePath);
            PipelineConfig pipelineConfig = readPipelineConfig(resource);
            executePipelineInputFile(pipelineConfig);
        } catch (Exception e) {
            throw new RuntimeException("Failed to process pipeline from: " + resourcePath, e);
        }
    }

    public void runPipelineFromPathUrl(String resourcePath) {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        try {
            String fullPath = "classpath:" + resourcePath;
            System.out.println("Looking for resource at: " + fullPath);
            Resource resource = resolver.getResource(fullPath);
            System.out.println("Resource exists: " + resource.exists());
            System.out.println("Resource description: " + resource.getDescription());
            PipelineConfig pipelineConfig = readPipelineConfig(resource);
            executePipelineInputUrl(pipelineConfig);
        } catch (Exception e) {
            throw new RuntimeException("Failed to process pipeline from: " + resourcePath, e);
        }
    }

    private PipelineConfig readPipelineConfig(Resource resource) {
        try {
            return objectMapper.readValue(resource.getInputStream(), PipelineConfig.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read pipeline config: " + resource.getFilename(), e);
        }
    }

    public void executePipelineInputFile(PipelineConfig config) {
        System.out.println("Executing pipeline: " + config.getName());
        orchestrator.executePipelineInputFile(config);
    }

    public void executePipelineInputUrl(PipelineConfig config) {
        System.out.println("Executing pipeline: " + config.getName());
        orchestrator.executePipelineInputUrl(config);
    }
}
