package com.carturo.eventhub.infrastructure.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ProfileLogger {

    private static final Logger logger = LoggerFactory.getLogger(ProfileLogger.class);

    @Value("${spring.profiles.active:default}")
    private String activeProfile;

    @EventListener(ApplicationReadyEvent.class)
    public void logActiveProfile() {
        logger.info("Application started with active profile: {}", activeProfile.toUpperCase());
    }
}