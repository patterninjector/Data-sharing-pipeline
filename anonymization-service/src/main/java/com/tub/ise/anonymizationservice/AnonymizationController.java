package com.tub.ise.anonymizationservice;



import com.tub.ise.anonymizationservice.service.AnonymizationService;
import com.tub.ise.commondtos.ServiceRequest;
import com.tub.ise.commondtos.ServiceResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnonymizationController {

    @Value("${slow.controller.delay}")
    private long delay;

    @Value("${flaky.controller.rate}")
    private double errorRate;

    private final AnonymizationService anonymizationService;

    public AnonymizationController(AnonymizationService anonymizationService) {
        this.anonymizationService = anonymizationService;
    }

    @PostMapping("/anonymize")
    public ServiceResponse anonymizeData(@RequestBody ServiceRequest request) {
        return new ServiceResponse(
                anonymizationService.anonymize(
                        request.getData(),
                        request.getConfig()
                )
        );
    }


    // 50% failure rate
    @PostMapping("/flaky-anonymize")
    public ServiceResponse flakyAnonymize(@RequestBody ServiceRequest request) {
        if (Math.random() > errorRate) {
            throw new RuntimeException("Simulated failure");
        }
        return new ServiceResponse(
                anonymizationService.anonymize(
                        request.getData(),
                        request.getConfig()
                )
        );
    }

    // 2s delay
    @PostMapping("/slow-anonymize")
    public ServiceResponse slowAnonymize(@RequestBody ServiceRequest request) throws InterruptedException {
        Thread.sleep(delay);
        return new ServiceResponse(
                anonymizationService.anonymize(
                        request.getData(),
                        request.getConfig()
                )
        );
    }
}
