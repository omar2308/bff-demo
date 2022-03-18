package com.jiren.kafka.service;

import com.jiren.kafka.bus.InternalBus;
import com.jiren.kafka.service.dto.CustomerDTO;
import com.jiren.kafka.service.dto.CustomerGetReqDTO;
import com.jiren.kafka.service.dto.CustomerGetResDTO;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
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
public class MockCustomerService implements CustomerService{
    private Map<String, CustomerDTO> customers = new HashMap<>();
    private final InternalBus bus;
    @PostConstruct
    public void post(){
        customers.put("001", CustomerDTO.builder()
                .id("001")
                .name("Manuel")
                .build());
        customers.put("002", CustomerDTO.builder()
                .id("002")
                .name("Milagros")
                .build());
        log.info("customers.size=" + customers.size());
    }
    @Override
    public void asyncGetReq(CustomerGetReqDTO customerGetReqDTO) {
        CustomerGetResDTO resp = CustomerGetResDTO.builder()
                .header(customerGetReqDTO.getHeader())
                .body(customers.get(customerGetReqDTO.getBody().getId()))
                .build();
        
        bus.put(customerGetReqDTO.getHeader().getTrace(), resp);
    }

    @Override
    public CustomerGetResDTO asyncGetResp(String trace) {
        return bus.waitAndRemove(trace, CustomerGetResDTO.class);
    }

    
    
}
