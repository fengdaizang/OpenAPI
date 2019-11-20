package com.fdzang.microservice.ucenter.client.impl;

import com.fdzang.microservice.common.entity.ApiResult;
import com.fdzang.microservice.ucenter.client.ApiClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author tanghu
 * @Date: 2019/11/19 18:38
 */
@Slf4j
@Component
public class ApiClientHystrix implements FallbackFactory<ApiClient> {

    @Override
    public ApiClient create(Throwable throwable) {
        return new ApiClient() {
            @Override
            public ApiResult getApiByUrlAndMethod(String url, String httpMethod) {
                return null;
            }
        };
    }
}