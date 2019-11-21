package com.fdzang.microservice.data.client.impl;

import com.fdzang.microservice.common.entity.ApiResult;
import com.fdzang.microservice.data.client.ApiClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author tanghu
 * @Date: 2019/11/20 17:43
 */
@Slf4j
@Component
public class ApiClientHystrix implements FallbackFactory<ApiClient> {

    @Override
    public ApiClient create(Throwable throwable) {
        return new ApiClient() {
            @Override
            public ApiResult getApiByUrlAndMethod(String url, String method) {
                log.info("call getApiByUrlAndMethod fail!");
                return null;
            }
        };
    }
}