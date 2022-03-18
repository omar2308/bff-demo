package com.jiren.kafka;

import com.jiren.kafka.dto.CustomerCreateReqDTO;
import com.jiren.kafka.dto.CustomerGetReqDTO;
import com.jiren.kafka.dto.GeneralMessageDTO;
import com.jiren.kafka.handler.KafkaCustomerHandler;
import com.jiren.kafka.support.JsonParser;
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
public class KafkaFrontController {
    private final JsonParser parser;
    private final KafkaCustomerHandler kafkaCustomerHandler;
    
    public void route(String messsage){
        GeneralMessageDTO gen = parser.parseGeneralMessageDTO(messsage);
        switch(gen.getHeader().getOperation()){
            case "GET":
                CustomerGetReqDTO customerGetDTO = parser.toObj(messsage, CustomerGetReqDTO.class);
                kafkaCustomerHandler.getCustomerByID(customerGetDTO);
                break;
            case "CREATE":
                CustomerCreateReqDTO createDto  = parser.toObj(messsage, CustomerCreateReqDTO.class);
                kafkaCustomerHandler.getCreateCustomer(createDto);
                break;
            default:
                log.warn("operation:" + gen.getHeader().getOperation() + " sin servicio asociado");
        }
    }
}
