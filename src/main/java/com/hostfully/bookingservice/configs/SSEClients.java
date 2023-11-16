package com.hostfully.bookingservice.configs;

import com.hostfully.bookingservice.models.SSEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.FluxSink;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class SSEClients {
    @Bean
    public Map<String, FluxSink<SSEvent>> clientCache() {
        return new HashMap<>();
    }
}