package org.jeremiasDinzinga.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic calcRequestsTopic() {
        return new NewTopic("calc_requests", 1, (short) 1);
    }
    @Bean
    public NewTopic calcResponsesTopic() {
        return new NewTopic("calc_responses", 1, (short) 1);
    }
}
