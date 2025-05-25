package com.tub.ise.aggrigationservice;


import com.tub.ise.aggrigationservice.service.AggregationService;
import com.tub.ise.commondtos.ServiceRequest;
import com.tub.ise.commondtos.ServiceResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AggregationController {

    @Value("${slow.controller.delay}")
    private long delay;

    @Value("${flaky.controller.rate}")
    private double errorRate;
    private final AggregationService aggregationService;

    public AggregationController(AggregationService aggregationService) {
        this.aggregationService = aggregationService;
    }

    @PostMapping("/aggregate")
    public ServiceResponse aggregateData(@RequestBody ServiceRequest request) {
        return new ServiceResponse(
                aggregationService.aggregate(
                        request.getData(),
                        request.getConfig()
                )
        );
    }



    // 50% failure rate
    @PostMapping("/flaky-aggregate")
    public ServiceResponse flakyAggregate(@RequestBody ServiceRequest request) {
        if (Math.random() > errorRate) {
            throw new RuntimeException("Simulated failure");
        }
        return new ServiceResponse(
                aggregationService.aggregate(
                        request.getData(),
                        request.getConfig()
                )
        );
    }

    // 2s delay
    @PostMapping("/slow-aggregate")
    public ServiceResponse slowAggregate(@RequestBody ServiceRequest request) throws InterruptedException {
        Thread.sleep(delay);
        return new ServiceResponse(
                aggregationService.aggregate(
                        request.getData(),
                        request.getConfig()
                )
        );
    }

}