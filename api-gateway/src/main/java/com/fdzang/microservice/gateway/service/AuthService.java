package com.fdzang.microservice.gateway.service;

import com.fdzang.microservice.common.entity.auth.AuthRequest;
import com.fdzang.microservice.common.entity.auth.AuthResult;

/**
 * @author tanghu
 * @Date: 2019/11/6 13:37
 */
public interface AuthService {
    AuthResult auth(AuthRequest request);
}
