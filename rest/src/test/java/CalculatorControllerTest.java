import org.jeremiasDinzinga.controller.CalculatorController;
import org.jeremiasDinzinga.kafka.CalculatorProducer;
import org.jeremiasDinzinga.kafka.CalculatorResponseConsumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CalculatorControllerTest {
    @Mock
    private CalculatorProducer calculatorProducer;

    @Mock
    private CalculatorResponseConsumer responseConsumer;

    @InjectMocks
    private CalculatorController calculatorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSum() throws InterruptedException {
        ArgumentCaptor<String> requestIdCaptor = ArgumentCaptor.forClass(String.class);

        doNothing().when(calculatorProducer).sendCalculationRequest(eq("sum"), eq("10"), eq("5"), requestIdCaptor.capture());
        when(responseConsumer.getResult(anyString())).thenReturn("15");

        ResponseEntity<Map<String, Object>> response = calculatorController.calculate("sum", new BigDecimal("10"), new BigDecimal("5"));

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("15", response.getBody().get("result"));

        verify(calculatorProducer, times(1)).sendCalculationRequest(eq("sum"), eq("10"), eq("5"), anyString());
        verify(responseConsumer, times(1)).getResult(anyString());
    }

    @Test
    void testSubtraction() throws InterruptedException {
        ArgumentCaptor<String> requestIdCaptor = ArgumentCaptor.forClass(String.class);

        doNothing().when(calculatorProducer).sendCalculationRequest(eq("subtract"), eq("10"), eq("5"), requestIdCaptor.capture());
        when(responseConsumer.getResult(anyString())).thenReturn("5");

        ResponseEntity<Map<String, Object>> response = calculatorController.calculate("subtract", new BigDecimal("10"), new BigDecimal("5"));

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("5", response.getBody().get("result"));

        verify(calculatorProducer, times(1)).sendCalculationRequest(eq("subtract"), eq("10"), eq("5"), anyString());
        verify(responseConsumer, times(1)).getResult(anyString());
    }

    @Test
    void testMultiplication() throws InterruptedException {
        ArgumentCaptor<String> requestIdCaptor = ArgumentCaptor.forClass(String.class);

        doNothing().when(calculatorProducer).sendCalculationRequest(eq("multiply"), eq("10"), eq("5"), requestIdCaptor.capture());
        when(responseConsumer.getResult(anyString())).thenReturn("50");

        ResponseEntity<Map<String, Object>> response = calculatorController.calculate("multiply", new BigDecimal("10"), new BigDecimal("5"));

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("50", response.getBody().get("result"));

        verify(calculatorProducer, times(1)).sendCalculationRequest(eq("multiply"), eq("10"), eq("5"), anyString());
        verify(responseConsumer, times(1)).getResult(anyString());
    }

    @Test
    void testDivision() throws InterruptedException {
        ArgumentCaptor<String> requestIdCaptor = ArgumentCaptor.forClass(String.class);

        doNothing().when(calculatorProducer).sendCalculationRequest(eq("divide"), eq("10"), eq("5"), requestIdCaptor.capture());
        when(responseConsumer.getResult(anyString())).thenReturn("2");

        ResponseEntity<Map<String, Object>> response = calculatorController.calculate("divide", new BigDecimal("10"), new BigDecimal("5"));

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("2", response.getBody().get("result"));

        verify(calculatorProducer, times(1)).sendCalculationRequest(eq("divide"), eq("10"), eq("5"), anyString());
        verify(responseConsumer, times(1)).getResult(anyString());
    }

    @Test
    void testDivisionByZero() throws InterruptedException {
        ArgumentCaptor<String> requestIdCaptor = ArgumentCaptor.forClass(String.class);

        doNothing().when(calculatorProducer).sendCalculationRequest(eq("divide"), eq("10"), eq("0"), requestIdCaptor.capture());
        when(responseConsumer.getResult(anyString())).thenReturn("Error: Division by zero is not allowed");

        ResponseEntity<Map<String, Object>> response = calculatorController.calculate("divide", new BigDecimal("10"), new BigDecimal("0"));

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Division by zero is not allowed", response.getBody().get("error"));
    }
}
