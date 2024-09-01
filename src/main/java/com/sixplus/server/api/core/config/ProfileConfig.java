package com.sixplus.server.api.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class ProfileConfig {

    @Value("${spring.profiles.active:default}")
    private String activeProfile;

    @PostConstruct
    public void logActiveProfile() {
        System.out.println("Currently active profile: " + activeProfile);
    }
}

