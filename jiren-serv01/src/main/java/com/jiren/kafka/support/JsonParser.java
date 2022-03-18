package com.jiren.kafka.support;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jiren.kafka.dto.GeneralMessageDTO;
import com.jiren.kafka.dto.HeaderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 *
 * @author Omar
 */
@Component
@Slf4j
public class JsonParser {
    public GeneralMessageDTO parseGeneralMessageDTO(String body){
        try {
            ObjectMapper objm = new ObjectMapper();
            GeneralMessageDTO c = objm.readValue(body, GeneralMessageDTO.class);
            return c;
        } catch (JsonProcessingException e) {
            log.warn("No se pudo parsear el objeto json: " + e);
            return GeneralMessageDTO.builder()
                    .header(HeaderDTO.builder()
                            .operation("NONE")
                            .build())
                    .build();
        }
    }
    
    public String toJson(Object obj){
        try {
            ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            String result = objectMapper.writeValueAsString(obj);
            return result;
        } catch (JsonProcessingException ex) {
            throw new IllegalArgumentException("No fue posible convertir el objeto como JSON:" + obj, ex);
        }
    }
    
    public <C> C toObj(String body, Class claz){
        try { 
            ObjectMapper objm = new ObjectMapper();
            C c = (C)objm.readValue(body, claz);
            return c;
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("No se pudo parsear el objeto json: " + e);
        }
    }
}
