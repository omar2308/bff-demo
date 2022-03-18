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
public class HeaderDTO {
   private String operation;
   private String trace;
   
}