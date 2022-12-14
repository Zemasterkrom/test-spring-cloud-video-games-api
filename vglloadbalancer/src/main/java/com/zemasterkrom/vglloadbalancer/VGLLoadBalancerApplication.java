package com.zemasterkrom.vglloadbalancer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class VGLLoadBalancerApplication {

    public static void main(String[] args) {
        SpringApplication.run(VGLLoadBalancerApplication.class, args);
    }
}

