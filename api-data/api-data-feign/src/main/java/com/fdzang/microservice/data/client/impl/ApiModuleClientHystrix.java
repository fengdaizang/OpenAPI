package com.fdzang.microservice.data.client.impl;

import com.fdzang.microservice.common.entity.ApiResult;
import com.fdzang.microservice.data.client.ApiModuleClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author tanghu
 * @Date: 2019/11/21 9:43
 */
@Slf4j
@Component
public class ApiModuleClientHystrix  implements FallbackFactory<ApiModuleClient> {

    @Override
    public ApiModuleClient create(Throwable throwable) {
        return new ApiModuleClient() {
            @Override
            public ApiResult getByApiId(Integer apiId) {
                log.info("call getByApiId fail!");
                return null;
            }
        };
    }
}