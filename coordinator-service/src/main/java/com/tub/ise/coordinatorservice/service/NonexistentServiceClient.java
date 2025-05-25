package com.tub.ise.coordinatorservice.service;


import com.tub.ise.commondtos.ServiceRequest;
import com.tub.ise.commondtos.ServiceResponse;
import com.tub.ise.coordinatorservice.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

//for non valid url test scenario
@FeignClient(
        name = "nonexistent-service",
        url = "${service.nonexistent.url}",
        configuration = FeignConfig.class
)
public interface NonexistentServiceClient {

    @PostMapping("/blablabla")
    ServiceResponse process(ServiceRequest request);
}
