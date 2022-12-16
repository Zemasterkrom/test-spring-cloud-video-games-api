package com.zemasterkrom.vglloadbalancer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.retry.annotation.EnableRetry;

@EnableEurekaClient
@EnableRetry
@SpringBootApplication
public class VGLLoadBalancerApplication {

    public static void main(String[] args) {
        SpringApplication.run(VGLLoadBalancerApplication.class, args);
    }
}

