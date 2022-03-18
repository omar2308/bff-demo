package com.jiren.kafka.handler;

import com.jiren.kafka.KafkaProducer;
import com.jiren.kafka.dto.CustomerCreateReqDTO;
import com.jiren.kafka.dto.CustomerCreateRespDTO;
import com.jiren.kafka.dto.CustomerGetReqDTO;
import com.jiren.kafka.dto.CustomerGetResDTO;
import com.jiren.kafka.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 *
 * @author Omar
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaCustomerHandler {
    private final KafkaProducer producer;
    private final CustomerService customerService;
    
    public void getCustomerByID(CustomerGetReqDTO customerGetDTO){
        CustomerGetResDTO resp = customerService.getCustomerByID(customerGetDTO);
        log.info("resp:" + resp);
        producer.send("fMyTopicResp", resp);
    }
    
    public void getCreateCustomer(CustomerCreateReqDTO customerCreateReqDTO){
        CustomerCreateRespDTO resp = customerService.createCustomer(customerCreateReqDTO);
        log.info("resp:" + resp);
        producer.send("fMyTopicResp", resp);
    }
}
