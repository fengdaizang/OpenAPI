package com.fdzang.microservice.ucenter.client.impl;

import com.fdzang.microservice.common.entity.ApiResult;
import com.fdzang.microservice.ucenter.client.UserModuleClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author tanghu
 * @Date: 2019/11/21 10:07
 */
@Slf4j
@Component
public class UserModuleClientHystrix implements FallbackFactory<UserModuleClient> {
    @Override
    public UserModuleClient create(Throwable throwable) {
        return new UserModuleClient() {
            @Override
            public ApiResult getByModuleIdAndAccessId(Integer moduleId, String accessId) {
                log.info("call getByModuleIdAndAccessId fail!");
                return null;
            }
        };
    }
}
