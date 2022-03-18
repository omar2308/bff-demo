package com.jiren.kafka.rest;

import com.jiren.kafka.dto.CustomerBodyGetReqDTO;
import com.jiren.kafka.dto.CustomerGetReqDTO;
import com.jiren.kafka.dto.CustomerGetResDTO;
import com.jiren.kafka.dto.HeaderDTO;
import com.jiren.kafka.handler.RestCustomerHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Omar
 */
@org.springframework.web.bind.annotation.RestController
@RequestMapping(
        value = "/v1",
        produces = MimeTypeUtils.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
@Slf4j
public class RestController {
    private final RestCustomerHandler restCustomerHandler;
    
    @GetMapping(value="/customer/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerGetResDTO> getCustomer(
            @PathVariable(value = "id") String id) {
        log.info("GET for id:" + id);
        CustomerGetReqDTO req = CustomerGetReqDTO.builder()
                .header(HeaderDTO.builder()
                        .operation("GET")
                        .trace("abcd")
                        .build())
                .body(CustomerBodyGetReqDTO.builder()
                        .id(id)
                        .build())
                .build();
        CustomerGetResDTO dto = restCustomerHandler.getCustomerByID(req);
        log.info("Response:" + dto);
        return ResponseEntity.ok(dto);
    }
}
