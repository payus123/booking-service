package com.hostfully.bookingservice.service;

import com.hostfully.bookingservice.configs.RedisMessagePublisher;
import com.hostfully.bookingservice.models.Message;
import com.hostfully.bookingservice.models.SSEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class Jobs {
    private final BookingService bookingService;
    private final RedisMessagePublisher redisMessagePublisher;

    public void publish(){
        System.out.println("has started");
        Message message = Message.builder().title("Credit").body(UUID.randomUUID().toString().replace("-","").substring(0,5)).build();
        SSEvent event = SSEvent.builder().accountNumber("0000015938").message(message).build();
//        redisMessagePublisher.publishMessage(event);
        System.out.println("has published");


    }
}
