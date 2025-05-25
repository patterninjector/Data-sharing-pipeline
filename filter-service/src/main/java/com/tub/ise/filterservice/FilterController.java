package com.tub.ise.filterservice;


import com.tub.ise.commondtos.ServiceRequest;
import com.tub.ise.commondtos.ServiceResponse;
import com.tub.ise.filterservice.service.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class FilterController {

    @Autowired
    private FilterService filterService;

    @Value("${slow.controller.delay}")
    private long delay;

    @Value("${flaky.controller.rate}")
    private double errorRate;

    @PostMapping("/filter")
    public ServiceResponse filterData(@RequestBody ServiceRequest request) {
          return filterService.filter(request);
    }

    // 50% failure rate
    @PostMapping("/flaky-filter")
    public ServiceResponse flakyFilter(@RequestBody ServiceRequest request) {
        if (Math.random() > errorRate) {
            throw new RuntimeException("Simulated failure");
        }
        return filterService.filter(request);
    }

    // 2s delay
    @PostMapping("/slow-filter")
    public ServiceResponse slowFilter(@RequestBody ServiceRequest request) throws InterruptedException {
        Thread.sleep(delay);
        return filterService.filter(request);
    }


}
