package com.jiren.kafka;

import com.jiren.kafka.dto.CustomerBodyGetReqDTO;
import com.jiren.kafka.dto.CustomerBodyGetResDTO;
import com.jiren.kafka.service.CustomerService;
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
    private final CustomerService customerService;
    private final JsonParser jsonParse;
    private final KafkaProducer producer;
    
    @KafkaListener(topics = "fTopicCustomerGETRequ", groupId = "${spring.kafka.consumer.group-id}")
    public void receive(String message) {
        log.info("received payload='{}'", message);
        CustomerBodyGetReqDTO bodyGet = jsonParse.toObj(message, CustomerBodyGetReqDTO.class);
        CustomerBodyGetResDTO resp = CustomerBodyGetResDTO.builder()
                .id(bodyGet.getId())
                .name("Carlos")
                .build();
        
        String msgResp = jsonParse.toJson(resp);
        producer.send("fTopicCustomerGETResp", msgResp);
    }
}
