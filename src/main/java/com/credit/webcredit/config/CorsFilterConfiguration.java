package com.credit.webcredit.config;

import java.util.Arrays;
import java.util.stream.Stream;

import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.filter.CorsFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class CorsFilterConfiguration {

    @Bean
    public FilterRegistrationBean<Filter> corsFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<Filter>();
        filterRegistrationBean.setFilter(new CorsFilter(request -> {
            String origin = request.getHeader(HttpHeaders.ORIGIN);

            if (!StringUtils.hasText(origin)) {
                return null;
            }

            log.info("origin = {}", origin);

            CorsConfiguration configuration = new CorsConfiguration();

            configuration.addAllowedOrigin(origin);

            String accessControlRequestHeaders = request.getHeader(HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS);
            if (StringUtils.hasText(accessControlRequestHeaders)) {
                Stream.of(accessControlRequestHeaders.split(",")).map(String::trim).distinct()
                        .forEach(configuration::addAllowedHeader);
            }

            configuration.addExposedHeader("*");

            configuration.setAllowCredentials(true);

            configuration
                    .setAllowedMethods(Arrays.asList("GET", "HEAD", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "TRACE"));

            return configuration;
        }));
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setOrder(Integer.MIN_VALUE);
        return filterRegistrationBean;
    }
}