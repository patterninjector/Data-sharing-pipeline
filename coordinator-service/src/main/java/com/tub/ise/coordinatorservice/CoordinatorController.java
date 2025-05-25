package com.tub.ise.coordinatorservice;


//import com.tub.ise.commondtos.PipelineRequest;
//import com.tub.ise.commondtos.PipelineResponse;
import com.tub.ise.coordinatorservice.config.PipelineConfig;
//import com.tub.ise.coordinatorservice.service.PipelineOrchestrator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class CoordinatorController {

//    private final PipelineOrchestrator pipelineOrchestrator;
//
//    public CoordinatorController(PipelineOrchestrator pipelineOrchestrator) {
//        this.pipelineOrchestrator = pipelineOrchestrator;
//    }
//
//    @PostMapping("/execute-pipeline")
//    public PipelineResponse executePipeline(@RequestBody PipelineRequest request) {
//        return pipelineOrchestrator.executePipeline(request);
//    }


    private final PipelineRunner pipelineRunner;

    public CoordinatorController(PipelineRunner pipelineRunner) {
        this.pipelineRunner = pipelineRunner;
    }


    @PostMapping("/run-pipeline-dir")
    public void runPipelineDir() {
        try {
            pipelineRunner.runPipelinesFromDirectory("classpath:pipelines");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/run-pipeline-path")
    public void runPipelinePath(@RequestBody PipelinePathRequest request) {
        pipelineRunner.runPipelineFromPath(request.getPath());
    }

    @PostMapping("/run-pipeline-path-url")
    public void runPipelinePathUrl(@RequestBody PipelinePathRequest request) {
        pipelineRunner.runPipelineFromPathUrl(request.getPath());
    }

    @GetMapping("/check")
    public String check() {
        return "up and running......";
    }
}
