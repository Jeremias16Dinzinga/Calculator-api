package org.jeremiasDinzinga.controller;

import org.jeremiasDinzinga.kafka.CalculatorProducer;
import org.jeremiasDinzinga.kafka.CalculatorResponseConsumer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class CalculatorController {
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

        if ("divide".equalsIgnoreCase(operation) && BigDecimal.ZERO.compareTo(b) == 0) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Division by zero is not allowed"));
        }

        calculatorProducer.sendCalculationRequest(operation, a.toString(), b.toString(), requestId);

        try {
            String result = responseConsumer.getResult(requestId);
            if (result == null) {
                return new ResponseEntity<>(
                        Map.of("requestId", requestId, "error", "No response received"),
                        HttpStatus.EXPECTATION_FAILED
                );
            }

            return ResponseEntity.ok(Map.of("requestId", requestId, "result", result));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return new ResponseEntity<>(
                    Map.of("requestId", requestId, "error", "Timeout while waiting for response"),
                    HttpStatus.EXPECTATION_FAILED
            );
        }
    }
}
