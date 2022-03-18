package com.jiren.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Omar
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCreateReqDTO {
    private HeaderDTO header;
    private CustomerDTO body;
    
}
