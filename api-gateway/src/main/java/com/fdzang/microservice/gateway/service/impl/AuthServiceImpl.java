package com.fdzang.microservice.gateway.service.impl;

import com.fdzang.microservice.common.entity.auth.AuthCode;
import com.fdzang.microservice.common.entity.auth.AuthRequest;
import com.fdzang.microservice.common.entity.auth.AuthResult;
import com.fdzang.microservice.gateway.client.AuthClient;
import com.fdzang.microservice.gateway.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tanghu
 * @Date: 2019/11/6 13:37
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthClient authClient;

    @Override
    public AuthResult auth(AuthRequest request) {
        //AuthResult authResult =authClient.auth(request);
        AuthResult authResult = new AuthResult();

        authResult.setStatus(AuthCode.SUCEESS.getAuthCode());
        authResult.setDescription("test");
        authResult.setOrgCode("9900");
        authResult.setServiceName("blog-article-v1");
        authResult.setUsername("test-th");

        return authResult;
    }
}
