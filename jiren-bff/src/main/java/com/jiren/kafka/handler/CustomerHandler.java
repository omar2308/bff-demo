package com.jiren.kafka.handler;

import com.jiren.kafka.service.CustomerService;
import com.jiren.kafka.service.dto.CustomerBodyGetReqDTO;
import com.jiren.kafka.service.dto.CustomerDTO;
import com.jiren.kafka.service.dto.CustomerGetReqDTO;
import com.jiren.kafka.service.dto.CustomerGetResDTO;
import com.jiren.kafka.service.dto.HeaderDTO;
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
public class CustomerHandler {
    private final CustomerService customerService;
    
    public CustomerDTO getCustomer(String id){
        String trace = "0001";
        CustomerGetReqDTO req = CustomerGetReqDTO.builder()
                .header(HeaderDTO.builder()
                        .trace(trace)
                        .operation("GET")
                        .build())
                .body(CustomerBodyGetReqDTO.builder()
                        .id(id)
                        .build())
                .build();
        
        
        customerService.asyncGetReq(req);
        //ASYN req Prod
        //ASYNC REQ Neneficios
        
        CustomerGetResDTO resp = customerService.asyncGetResp(trace);
        
        //consulto el producto:
        //REQ
        //RES
        
        //ORDERS
        //ASYN REQ DetallePEdido
        //ASYN RESP DetallePedido
        //ASYN Req Producto
        //ASYN Resp Producto
        
        
        return resp.getBody();
    }
    
    
    public void createCustomer(){
        
    }
}
