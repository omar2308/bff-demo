package com.jiren.kafka.service;

import com.jiren.kafka.dao.CustomerDAO;
import com.jiren.kafka.dto.CustomerCreateReqDTO;
import com.jiren.kafka.dto.CustomerCreateRespDTO;
import com.jiren.kafka.dto.CustomerDTO;
import com.jiren.kafka.dto.CustomerGetReqDTO;
import com.jiren.kafka.dto.CustomerGetResDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 *
 * @author Omar
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {
    private final CustomerDAO customerDAO;
    
    public CustomerGetResDTO getCustomerByID(CustomerGetReqDTO customerGetDTO){
        CustomerDTO customer = customerDAO.find(customerGetDTO.getBody().getId());
        return CustomerGetResDTO.builder()
                .header(customerGetDTO.getHeader())
                .body(customer)
                .build();
    }
    
    public CustomerCreateRespDTO createCustomer(CustomerCreateReqDTO customerCreateReqDTO){
        customerDAO.create(customerCreateReqDTO.getBody());
        
        return CustomerCreateRespDTO.builder()
                .header(customerCreateReqDTO.getHeader())
                .body(customerCreateReqDTO.getBody())
                .build();
    }
    
}
