package com.keepcoding.limitsservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("limits")
public class LimitsProperties {

    private int maximum;
    private int minimum;

    public LimitsProperties() {
    }

    public LimitsProperties(int maximum, int minimum) {
        this.maximum = maximum;
        this.minimum = minimum;
    }

    public int getMaximum() {
        return maximum;
    }

    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }

    public int getMinimum() {
        return minimum;
    }

    public void setMinimum(int minimum) {
        this.minimum = minimum;
    }
}
