package org.jeremiasDinzinga.controller;

import org.jeremiasDinzinga.kafka.CalculatorProducer;
import org.jeremiasDinzinga.kafka.CalculatorResponseConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class CalculatorController {

    private static final Logger log = LoggerFactory.getLogger(CalculatorController.class);
    private final CalculatorProducer calculatorProducer;
    private final CalculatorResponseConsumer responseConsumer;

    public CalculatorController(CalculatorProducer calculatorProducer, CalculatorResponseConsumer responseConsumer) {
        this.calculatorProducer = calculatorProducer;
        this.responseConsumer = responseConsumer;
    }

    @GetMapping("/{operation}")
    public ResponseEntity<Map<String, Object>> calculate(
            @PathVariable String operation,
            @RequestParam BigDecimal a,
            @RequestParam BigDecimal b
    ) {
        String requestId = UUID.randomUUID().toString();
        MDC.put("requestId", requestId);

        log.info("Received request: operation={}, a={}, b={}, requestId={}", operation, a, b, requestId);

        if ("divide".equalsIgnoreCase(operation) && BigDecimal.ZERO.compareTo(b) == 0) {
            log.warn("Division by zero attempted: a={}, b={}", a, b);
            MDC.clear();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Division by zero is not allowed"));
        }

        calculatorProducer.sendCalculationRequest(operation, a.toString(), b.toString(), requestId);
        log.info("Sent request to Kafka: operation={}", operation);

        try {
            String result = responseConsumer.getResult(requestId);
            if (result == null) {
                log.warn("No response received for requestId={}", requestId);
                MDC.clear();
                return new ResponseEntity<>(
                        Map.of("requestId", requestId, "error", "No response received"),
                        HttpStatus.EXPECTATION_FAILED
                );
            }

            log.info("Returning result: result={}", result);
            return ResponseEntity.ok(Map.of("requestId", requestId, "result", result));

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Timeout while waiting for response", e);
            return new ResponseEntity<>(
                    Map.of("requestId", requestId, "error", "Timeout while waiting for response"),
                    HttpStatus.EXPECTATION_FAILED
            );
        } finally {
            MDC.clear();
        }
    }
}
