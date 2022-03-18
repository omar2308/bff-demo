package com.jiren.kafka.service.dto;

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
public class CustomerGetResDTO {
    private HeaderDTO header;
    private CustomerDTO body;
}
