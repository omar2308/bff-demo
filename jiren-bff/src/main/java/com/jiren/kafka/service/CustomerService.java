package com.jiren.kafka.service;

import com.jiren.kafka.service.dto.CustomerGetReqDTO;
import com.jiren.kafka.service.dto.CustomerGetResDTO;

/**
 *
 * @author Omar
 */
public interface CustomerService {
    public void asyncGetReq(CustomerGetReqDTO customerGetDTO);
    public CustomerGetResDTO asyncGetResp(String trace);
}
