package com.hostfully.bookingservice.configs;


import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

@Component
public class RedisPublisher {


    private final Jedis jedis = new Jedis("localhost",6379);

    public void publish(String channel, String message) {
        jedis.publish(channel, message);
    }
    @PreDestroy
    public void close() {
        jedis.close();
    }
}