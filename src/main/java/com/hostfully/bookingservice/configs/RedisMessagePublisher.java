package com.hostfully.bookingservice.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisMessagePublisher {

    private final RedisTemplate redisTemplate;

    public void publishMessage(String channel,Object message) {
        redisTemplate.convertAndSend(channel, message);
    }
}