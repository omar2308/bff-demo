package com.jiren.kafka;

import com.jiren.kafka.bus.InternalBus;
import com.jiren.kafka.service.dto.GeneralMessageDTO;
import com.jiren.kafka.support.JsonParser;
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
    private final JsonParser jsonParse;
    private final InternalBus bus;
    
    
    @KafkaListener(topics = "fMyTopicResp", groupId = "${spring.kafka.consumer.group-id}")
    public void receive(String message) {
        log.info("received payload='{}'", message);
        
        GeneralMessageDTO resp = jsonParse.toObj(message, GeneralMessageDTO.class);
        bus.put(resp.getHeader().getTrace(), message);
    }
}
