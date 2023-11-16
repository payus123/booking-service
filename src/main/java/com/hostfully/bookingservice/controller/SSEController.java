package com.hostfully.bookingservice.controller;


import com.google.gson.Gson;
import com.hostfully.bookingservice.configs.RedisMessagePublisher;
import com.hostfully.bookingservice.configs.SSEClients;
import com.hostfully.bookingservice.models.Message;
import com.hostfully.bookingservice.models.SSEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping("/sse")
@RequiredArgsConstructor
public class SSEController {
    private final SSEClients sseClients;
    private final RedisMessagePublisher redisPublisher;
    Gson gson = new Gson();
    // SSE endpoint
    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @Async
    public CompletableFuture<Flux<SSEvent>> sseEvents(@RequestParam("deviceId") String clientId) {
        return CompletableFuture.completedFuture(Flux.create(sink -> {
            sseClients.clientCache().put(clientId, sink.onDispose(() -> sseClients.clientCache().remove(clientId, sink)));
        }));
    }

    @GetMapping("push")
    public String pushEvents(@RequestParam("accountNumber") String accountNumber) {
        Message message = Message.builder().title("Credit").body(UUID.randomUUID().toString().replace("-","").substring(0,5)).build();
        SSEvent event = SSEvent.builder().accountNumber(accountNumber).message(message).build();
        redisPublisher.publishMessage(accountNumber,event);
        return "success";
    }




}