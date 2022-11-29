package com.zemasterkrom.vgldiscovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class VideoGameLibraryDiscoveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(VideoGameLibraryDiscoveryApplication.class, args);
    }

}
