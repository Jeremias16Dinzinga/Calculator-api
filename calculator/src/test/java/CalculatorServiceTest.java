import org.jeremiasDinzinga.service.CalculatorService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculatorServiceTest {
    private final CalculatorService calculatorService = new CalculatorService();

    @Test
    void testSum() {
        BigDecimal result = calculatorService.sum(new BigDecimal("10"), new BigDecimal("5"));
        assertEquals(new BigDecimal("15"), result);
    }

    @Test
    void testSubtraction() {
        BigDecimal result = calculatorService.subtract(new BigDecimal("10"), new BigDecimal("5"));
        assertEquals(new BigDecimal("5"), result);
    }

    @Test
    void testMultiplication() {
        BigDecimal result = calculatorService.multiply(new BigDecimal("10"), new BigDecimal("5"));
        assertEquals(new BigDecimal("50"), result);
    }

    @Test
    void testDivision() throws Exception {
        BigDecimal result = calculatorService.divide(new BigDecimal("10"), new BigDecimal("5"));
        assertEquals(new BigDecimal("2"), result);
    }

    @Test
    void testDivisionByZero() {
        assertThrows(ArithmeticException.class, () ->
                calculatorService.divide(new BigDecimal("10"), BigDecimal.ZERO)
        );
    }
}
