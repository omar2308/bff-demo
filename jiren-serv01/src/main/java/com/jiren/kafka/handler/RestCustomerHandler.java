package com.jiren.kafka.handler;

import com.jiren.kafka.dto.CustomerCreateReqDTO;
import com.jiren.kafka.dto.CustomerCreateRespDTO;
import com.jiren.kafka.dto.CustomerGetReqDTO;
import com.jiren.kafka.dto.CustomerGetResDTO;
import com.jiren.kafka.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 *
 * @author Omar
 */
@Component
@RequiredArgsConstructor
public class RestCustomerHandler {
    private final CustomerService customerService;
    
    public CustomerGetResDTO getCustomerByID(CustomerGetReqDTO customerGetDTO){
        return customerService.getCustomerByID(customerGetDTO);
    }
    
    public CustomerCreateRespDTO getCreateCustomer(CustomerCreateReqDTO customerCreateReqDTO){
        return customerService.createCustomer(customerCreateReqDTO);
    }
}
