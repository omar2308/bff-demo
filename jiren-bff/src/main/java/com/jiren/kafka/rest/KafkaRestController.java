package com.jiren.kafka.rest;

import com.jiren.kafka.handler.CustomerHandler;
import com.jiren.kafka.service.dto.CustomerDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Omar
 */
@RestController
@RequestMapping(
        value = "/v1",
        produces = MimeTypeUtils.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
@Slf4j
public class KafkaRestController {

    private final CustomerHandler customerHandler;

    @PostMapping(value="/sent/{topic}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDTO> sendMessage(
            @PathVariable(value = "topic") String topic,
            @RequestBody String message) {
        
        log.info("Recibido - topic:" + topic + ", mesg:" + message);
        CustomerDTO dto = customerHandler.getCustomer(message);
        return ResponseEntity.ok(dto);
    }
    
    @GetMapping(value="/customer/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDTO> getCustomer(
            @PathVariable(value = "id") String id) {
        
        log.info("Recibido - id:" + id);
        CustomerDTO dto = customerHandler.getCustomer(id);
        return ResponseEntity.ok(dto);
    }
    
    
}
