import org.jeremiasDinzinga.kafka.CalculatorProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class KafkaProducerTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private CalculatorProducer calculatorProducer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendCalculationRequest() {
        String operation = "ADD";
        String a = "5";
        String b = "10";
        String requestId = "12345";

        String expectedMessage = requestId + ";" + operation + ";" + a + ";" + b;

        calculatorProducer.sendCalculationRequest(operation, a, b, requestId);

        verify(kafkaTemplate, times(1)).send("calc_requests", requestId, expectedMessage);
    }
}
