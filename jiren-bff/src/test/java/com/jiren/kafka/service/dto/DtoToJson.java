package com.jiren.kafka.service.dto;

import com.jiren.kafka.support.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Omar
 */
@Slf4j
public class DtoToJson {
    @Test
    public void testCustomerGetDtoToJson() {
        CustomerGetReqDTO customerGetDTO = CustomerGetReqDTO.builder()
                .header(HeaderDTO.builder()
                        .operation("GET")
                        .trace("1234")
                        .build())
                .body(CustomerBodyGetReqDTO.builder()
                        .id("0001")
                        .build())
                .build();
        
        JsonParser parser = new JsonParser();
        String json = parser.toJson(customerGetDTO);
        log.info("customerGetDTO:\n" + json);
    }
}
