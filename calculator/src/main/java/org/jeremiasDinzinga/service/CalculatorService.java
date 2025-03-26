package org.jeremiasDinzinga.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;

@Service
public class CalculatorService {
    public BigDecimal sum(BigDecimal a, BigDecimal b) {
        return a.add(b);
    }

    public BigDecimal subtract(BigDecimal a, BigDecimal b) {
        return a.subtract(b);
    }

    public BigDecimal multiply(BigDecimal a, BigDecimal b) {
        return a.multiply(b);
    }

    public BigDecimal divide(BigDecimal a, BigDecimal b) throws Exception {
        if (b.compareTo(BigDecimal.ZERO) == 0) {
            throw new ArithmeticException("Can not Divide by zero");
        }
        return a.divide(b, MathContext.DECIMAL128);
    }
}
