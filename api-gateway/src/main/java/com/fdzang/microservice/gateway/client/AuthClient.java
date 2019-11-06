package com.fdzang.microservice.gateway.client;

import com.fdzang.microservice.common.entity.auth.AuthRequest;
import com.fdzang.microservice.common.entity.auth.AuthResult;
import com.fdzang.microservice.gateway.client.impl.AuthClientHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author tanghu
 * @Date: 2019/11/6 13:16
 */
@FeignClient(value = "altair-auth", fallbackFactory = AuthClientHystrix.class)
public interface AuthClient {

    @PostMapping("/auth/auth")
    AuthResult auth(@RequestBody AuthRequest authRequest);
}
