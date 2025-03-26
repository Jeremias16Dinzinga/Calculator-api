package org.jeremiasDinzinga.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CalculatorProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public CalculatorProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendCalculationRequest(String operation, String a, String b, String requestId) {
        String message = requestId + ";" + operation + ";" + a + ";" + b;
        kafkaTemplate.send("calc_requests", requestId, message);
    }
}
