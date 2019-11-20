package com.fdzang.microservice.data.api.service;

import com.fdzang.microservice.data.common.dto.ApiModuleDTO;

/**
 * @author tanghu
 * @Date: 2019/11/20 16:38
 */
public interface ApiModuleService {

    ApiModuleDTO getModuleByApiId(Integer apiId);
}
