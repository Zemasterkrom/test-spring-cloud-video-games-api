package com.zemasterkrom.videogameslibraryapplication.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.h2")
public class CustomProperties { }
