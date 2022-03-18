package com.jiren.kafka.service;

import com.jiren.kafka.KafkaProducer;
import com.jiren.kafka.dto.CustomerDTO;
import com.jiren.kafka.dto.CustomerGetDTO;
import com.jiren.kafka.support.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *
 * @author Omar
 */
@Service
@RequiredArgsConstructor
public class CustomerService {
    private final KafkaProducer producer;
    private final JsonParser parser;
    
    public void getCustomerByID(CustomerGetDTO customerGetDTO){
        CustomerDTO customer = CustomerDTO.builder()
                .id(customerGetDTO.getBody().getId())
                .name("Manuel")
                .build();
        producer.send("fMyTopicResp", parser.toJson(customer));
    }
    
}
