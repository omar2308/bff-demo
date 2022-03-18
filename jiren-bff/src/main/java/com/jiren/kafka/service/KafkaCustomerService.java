package com.jiren.kafka.service;

import com.jiren.kafka.KafkaProducer;
import com.jiren.kafka.bus.InternalBus;
import com.jiren.kafka.service.dto.CustomerGetReqDTO;
import com.jiren.kafka.service.dto.CustomerGetResDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 *
 * @author Omar
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaCustomerService implements CustomerService{
    private final KafkaProducer kafkaProducer;
    private final InternalBus bus;
    private static final String TOPIC_GET_REQ = "fMyTopicRequ";
    
    @Override
    public void asyncGetReq(CustomerGetReqDTO customerGetDTO) {
        kafkaProducer.send(TOPIC_GET_REQ, customerGetDTO);
    }

    @Override
    public CustomerGetResDTO asyncGetResp(String trace) {
        return bus.waitAndRemove(trace, CustomerGetResDTO.class);
    }

    
    
}
