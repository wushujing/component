package com.github.wushujing.light.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "light.web")
@Data
public class LightWebConfigurationProperties {

    private Cors cors = new Cors();

    @Data
    public static class Cors {
        private boolean enabled;
        private boolean allowCredentials = true;
        private List<String> allowOrigins = new ArrayList<>();
        private List<String> allowMethods = new ArrayList<>();
        private List<String> allowHeaders = new ArrayList<>();
        private long maxAge = 3_600;
        private List<String> exposedHeaders = new ArrayList<>();
    }
}
