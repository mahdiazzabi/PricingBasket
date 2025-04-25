package com.pricing.basket.adapter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private String locale;

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Locale getParsedLocale() {
        String[] parts = locale.split("_");
        return new Locale(parts[0], parts[1]);
    }
}
