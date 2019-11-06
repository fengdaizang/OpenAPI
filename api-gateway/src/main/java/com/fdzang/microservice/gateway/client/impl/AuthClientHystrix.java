package com.fdzang.microservice.gateway.client.impl;

import com.fdzang.microservice.common.entity.auth.AuthRequest;
import com.fdzang.microservice.common.entity.auth.AuthResult;
import com.fdzang.microservice.gateway.client.AuthClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author tanghu
 * @Date: 2019/11/6 13:30
 */
@Slf4j
@Component
public class AuthClientHystrix implements FallbackFactory<AuthClient> {

    @Override
    public AuthClient create(Throwable cause) {
        return new AuthClient() {
            @Override
            public AuthResult auth(AuthRequest authRequest) {
                return null;
            }
        };
    }
}
