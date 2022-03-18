package com.jiren.kafka.dao;

import com.jiren.kafka.dto.CustomerDTO;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 *
 * @author Omar
 */
@Component
@Slf4j
public class CustomerDAO {
    private Map<String, CustomerDTO> customers = new HashMap<>();
    
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
        log.info("customers=" + customers);
    }
    
    public CustomerDTO find(String id){
        return customers.get(id);
    }
    
    public void create(CustomerDTO customerDTO){
        customers.put(customerDTO.getId(), customerDTO);
    }
}
