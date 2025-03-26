package org.jeremiasDinzinga.kafka;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import org.springframework.kafka.annotation.KafkaListener;
import java.util.concurrent.CountDownLatch;

@Service
public class CalculatorResponseConsumer {
    private final ConcurrentHashMap<String, String> responseCache = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, CountDownLatch> latches = new ConcurrentHashMap<>();

    @KafkaListener(topics = "calc_responses", groupId = "rest-group")
    public void consumeCalculationResponse(String message) {
        String[] parts = message.split(";");
        String requestId = parts[0];
        String result = parts[1];

        responseCache.put(requestId, result);
        CountDownLatch latch = latches.get(requestId);
        if (latch != null) {
            latch.countDown();
        }
    }

    public String getResult(String requestId) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        latches.put(requestId, latch);
        latch.await();
        return responseCache.remove(requestId);
    }
}
