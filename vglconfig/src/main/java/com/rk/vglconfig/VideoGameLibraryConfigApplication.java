package com.rk.vglconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * Serveur de configuration de l'API
 */
@EnableConfigServer
@SpringBootApplication
public class VideoGameLibraryConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(VideoGameLibraryConfigApplication.class, args);
    }

}
