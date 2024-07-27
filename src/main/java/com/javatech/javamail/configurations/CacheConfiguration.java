package com.javatech.javamail.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Configuration
public class CacheConfiguration {

    @Bean
    public ConcurrentMap<String, String> pinCodeCache() {
        return new ConcurrentHashMap<>();
    }
}
