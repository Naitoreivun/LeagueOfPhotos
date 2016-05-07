package com.naitoreivun.lop;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.math.BigInteger;
import java.security.SecureRandom;

@ConfigurationProperties("my")
public class MyProperties {

    private String secretKey;

    public MyProperties() {
        this.secretKey = new BigInteger(130, new SecureRandom()).toString(32);
    }


    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
