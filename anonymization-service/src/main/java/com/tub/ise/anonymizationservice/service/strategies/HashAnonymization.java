package com.tub.ise.anonymizationservice.service.strategies;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component("hash")
public class HashAnonymization implements AnonymizationStrategy {

    @Override
    public String anonymize(String value) {
        return BCrypt.hashpw(value, BCrypt.gensalt());
    }
}
