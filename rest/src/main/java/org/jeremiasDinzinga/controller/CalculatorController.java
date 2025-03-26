package org.jeremiasDinzinga.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

public class CalculatorController {

    @GetMapping("/{operation}")
    public ResponseEntity<String> calculate(
            @PathVariable String operation,
            @RequestParam BigDecimal a,
            @RequestParam BigDecimal b
    ) {

            return new ResponseEntity<>("Result=0", HttpStatusCode.valueOf(200));

    }
}
