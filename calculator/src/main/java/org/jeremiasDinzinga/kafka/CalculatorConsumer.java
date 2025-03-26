package org.jeremiasDinzinga.kafka;

import org.jeremiasDinzinga.service.CalculatorService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CalculatorConsumer {
    private final CalculatorService calculatorService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public CalculatorConsumer(CalculatorService calculatorService, KafkaTemplate<String, String> kafkaTemplate) {
        this.calculatorService = calculatorService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "calc_requests", groupId = "calculator-group")
    public void consumeCalculationRequest(String message) {
        String[] parts = message.split(";");
        String requestId = parts[0];
        String operation = parts[1];
        BigDecimal a = new BigDecimal(parts[2]);
        BigDecimal b = new BigDecimal(parts[3]);

        BigDecimal result;
        try {
            switch (operation) {
                case "sum":
                    result = calculatorService.sum(a, b);
                    break;
                case "subtract":
                    result = calculatorService.subtract(a, b);
                    break;
                case "multiply":
                    result = calculatorService.multiply(a, b);
                    break;
                case "divide":
                    result = calculatorService.divide(a, b);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid operation: " + operation);
            }
        } catch (Exception e) {
            result = null;
        }

        kafkaTemplate.send("calc_responses", requestId, requestId + ";" + (result != null ? result.toString() : "error"));
    }
}
