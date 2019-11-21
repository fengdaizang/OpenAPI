package com.fdzang.microservice.ucenter.client.impl;

import com.fdzang.microservice.common.entity.ApiResult;
import com.fdzang.microservice.ucenter.client.UserClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author tanghu
 * @Date: 2019/11/21 10:11
 */
@Slf4j
@Component
public class UserClientHystrix implements FallbackFactory<UserClient> {

    @Override
    public UserClient create(Throwable throwable) {
        return new UserClient() {
            @Override
            public ApiResult getById(Integer id) {
                log.info("call getById fail!");
                return null;
            }
        };
    }
}
