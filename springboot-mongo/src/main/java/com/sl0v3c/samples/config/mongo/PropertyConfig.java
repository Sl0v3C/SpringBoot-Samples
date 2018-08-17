package com.sl0v3c.samples.config.mongo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix="mongodb.account")
public class PropertyConfig {
    private List<String> urls = new ArrayList<>();

    public List<String> getUrls() {
        return this.urls;
    }
}