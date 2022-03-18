package com.jiren.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 *
 * @author Omar
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {
    
    private final KafkaFrontController frontController;
    
    @KafkaListener(topics = "${spring.kafka.consumer.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void receive(String message) {
        log.info("received payload='{}'", message);
        frontController.route(message);
    }
}
