package com.tub.ise.coordinatorservice.service;


import com.tub.ise.commondtos.ServiceRequest;
import com.tub.ise.commondtos.ServiceResponse;

public interface ServiceClient {
    ServiceResponse processNormal(ServiceRequest request);
    ServiceResponse processFlaky(ServiceRequest request);
    ServiceResponse processSlow(ServiceRequest request);
    ServiceResponse processBlock(ServiceRequest request);
}