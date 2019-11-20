package com.fdzang.microservice.data.api.service;

import com.fdzang.microservice.data.common.dto.ApiDTO;

/**
 * @author tanghu
 * @Date: 2019/11/19 18:06
 */
public interface ApiService {
    ApiDTO getApiByUrlAndMethod(String url, String method);
}
