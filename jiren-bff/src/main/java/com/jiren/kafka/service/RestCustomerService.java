package com.jiren.kafka.service;

import com.jiren.kafka.bus.InternalBus;
import com.jiren.kafka.service.dto.CustomerGetReqDTO;
import com.jiren.kafka.service.dto.CustomerGetResDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Omar
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class RestCustomerService implements CustomerService{
    private final InternalBus bus;
    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseURL = "http://localhost:9002/v1";
    @Override
    public void asyncGetReq(CustomerGetReqDTO customerGetDTO) {
        Thread reqThread = new Thread(() -> {
            String url = baseURL + "/customer/" + customerGetDTO.getBody().getId();
            CustomerGetResDTO resp = restTemplate.getForEntity(
                    baseURL + "/customer/" + customerGetDTO.getBody().getId(), 
                    CustomerGetResDTO.class).getBody();
            log.info("Request for url:" + url);
            log.info("Trace:" + customerGetDTO.getHeader().getTrace() + ",Response:" + resp);
            bus.put(customerGetDTO.getHeader().getTrace(), resp);
        }, "REST-Thread");
        
        reqThread.start();
        
    }

    @Override
    public CustomerGetResDTO asyncGetResp(String trace) {
        log.info("Obteniendo respuesta para el trace:" + trace);
        return bus.waitAndRemove(trace, CustomerGetResDTO.class);
    }

    
    
}
