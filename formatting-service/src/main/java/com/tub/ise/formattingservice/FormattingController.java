package com.tub.ise.formattingservice;

import com.tub.ise.commondtos.ServiceRequest;
import com.tub.ise.commondtos.ServiceResponse;
import com.tub.ise.formattingservice.service.FormattingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class FormattingController {

    @Value("${slow.controller.delay}")
    private long delay;

    @Value("${flaky.controller.rate}")
    private double errorRate;
    private final FormattingService formattingService;

    public FormattingController(FormattingService formattingService) {
        this.formattingService = formattingService;
    }

    @PostMapping("/format")
    public ServiceResponse formatData(@RequestBody ServiceRequest request) {
       return formattingService.castAndFormat(request);
    }


    // 50% failure rate
    @PostMapping("/flaky-format")
    public ServiceResponse flakyFormat(@RequestBody ServiceRequest request) {
        if (Math.random() > errorRate) {
            throw new RuntimeException("Simulated failure");
        }
        return formattingService.castAndFormat(request);
    }

    // 2s delay
    @PostMapping("/slow-format")
    public ServiceResponse slowFormat(@RequestBody ServiceRequest request) throws InterruptedException {
        Thread.sleep(delay);
        return formattingService.castAndFormat(request);
    }
}