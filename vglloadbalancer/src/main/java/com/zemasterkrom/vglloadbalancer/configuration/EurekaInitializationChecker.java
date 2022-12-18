package com.zemasterkrom.vglloadbalancer.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.retry.support.RetryTemplate;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Allows to check that the Eureka Server is up before starting the Spring Boot application
 */
@Configuration
public class EurekaInitializationChecker implements ApplicationContextAware {

    private final Logger logger = LoggerFactory.getLogger(EurekaInitializationChecker.class);

    /**
     * Eureka Server URL
     */
    @Value("${eureka.client.service-url.defaultZone:}")
    private String eurekaServerUrl;

    /**
     * Max number of retries on the Eureka Server
     */
    @Value("${eureka.client.eureka-server-connect-max-retries:10}")
    private int eurekaServerMaxRetries;

    /**
     * Retry delay (ms) between each connection to the Eureka Server
     */
    @Value("${eureka.client.eureka-server-connect-retry-delay:12000}")
    private int eurekaServerRetryDelay;

    /**
     * Flag to check as the checker is enabled if the Eureka client is enabled
     */
    @Value("${eureka.client.enabled:true}")
    private boolean eurekaClientEnabled;

    /**
     * Eureka initialization checker constructor. Allows to redefine the Eureka server URL to get its base URL.
     */
    public EurekaInitializationChecker() {
        try {
            URL eurekaServerUrlParts = new URL(this.eurekaServerUrl);
            this.eurekaServerUrl = (eurekaServerUrlParts.getProtocol().length() > 0 ? (eurekaServerUrlParts.getProtocol() + "://") : "") + eurekaServerUrlParts.getHost() + (eurekaServerUrlParts.getPort() > 0 ? ":" + eurekaServerUrlParts.getPort() : "");
        } catch (MalformedURLException e) {
            this.eurekaServerUrl = "";
        }
    }

    /**
     * Try to establish a connection to the Eureka server before fully creating the context
     *
     * @param applicationContext Application context
     */
    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) {
        if (this.eurekaClientEnabled) {
            try {
                this.logger.info("Trying to establish a connection to the Eureka server ...");

                RetryTemplate.builder()
                        .maxAttempts(this.eurekaServerMaxRetries >= 0 ? this.eurekaServerMaxRetries + 1 : 1)
                        .fixedBackoff(this.eurekaServerRetryDelay)
                        .retryOn(IOException.class)
                        .build().execute(ctx -> {
                            URLConnection eurekaServerHttpConnection = new URL(this.eurekaServerUrl).openConnection();
                            eurekaServerHttpConnection.connect();

                            return true;
                        });
            } catch (MalformedURLException e) {
                this.logger.info("Can't establish a connection to the Eureka server since the URL is malformed");
                this.logger.info(e.getMessage());

                System.exit(SpringApplication.exit(applicationContext, () -> 126));
            } catch (IOException e) {
                this.logger.info("Can't establish a connection to the Eureka server : max timeout reached");
                this.logger.info(e.getMessage());

                System.exit(SpringApplication.exit(applicationContext, () -> 126));
            }
        }
    }
}
