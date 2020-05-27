package com.github.wushujing.light.web.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;

@Configuration
@EnableConfigurationProperties(LightWebConfigurationProperties.class)
public class LightWebAutoConfiguration {

    @Bean
    public FilterRegistrationBean webCorsFilter(LightWebConfigurationProperties webConfigurationProperties) {
        final LightWebConfigurationProperties.Cors cors = webConfigurationProperties.getCors();
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(cors.isAllowCredentials());
        config.setAllowedOrigins(cors.getAllowOrigins() == null || cors.getAllowOrigins().isEmpty() ? Collections.singletonList("*") : cors.getAllowOrigins());
        config.setAllowedMethods(cors.getAllowMethods() == null || cors.getAllowMethods().isEmpty() ? Collections.singletonList("*") : cors.getAllowMethods());
        config.setAllowedHeaders(cors.getAllowHeaders() == null || cors.getAllowHeaders().isEmpty() ? Collections.singletonList("*") : cors.getAllowHeaders());
        config.setExposedHeaders(cors.getExposedHeaders() == null || cors.getExposedHeaders().isEmpty() ? Collections.singletonList("*") : cors.getExposedHeaders());
        config.setMaxAge(cors.getMaxAge());

        source.registerCorsConfiguration("/**", config);
        final FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new CorsFilter(source));
        bean.setName("corsFilter");
        bean.setAsyncSupported(true);
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
        return bean;
    }
}
