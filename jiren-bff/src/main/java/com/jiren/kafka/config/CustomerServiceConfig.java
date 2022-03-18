package com.jiren.kafka.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.jiren.kafka.service.CustomerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;

/**
 *
 * @author Omar
 */
@Configuration
@Slf4j
@RequiredArgsConstructor
public class CustomerServiceConfig {
    private final Map<String, CustomerService> clientsCache = new HashMap<>();
    private final List<CustomerService> clients;
    @Value("${services.customer}")
    private String customerImplementation;
    
    @PostConstruct
    public void mapClientImplementations(){
        clients.forEach(c -> {
            clientsCache.put(c.getClass().getSimpleName(), c);
        });
        
        log.info("customer.size:" + clientsCache.size());
        log.info("clientsCache:" + clientsCache);
    }
    
    @Bean
    @Primary
    public CustomerService getCustomerService(){
        
        CustomerService clientService = clientsCache.get(customerImplementation);
        log.info("Impl:" + customerImplementation + ",ClientService.class=" + clientService);
        return clientService;
    }
}
