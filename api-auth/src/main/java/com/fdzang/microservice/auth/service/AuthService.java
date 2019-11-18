package com.fdzang.microservice.auth.service;

import com.fdzang.microservice.common.entity.auth.AuthResult;

/**
 * @author tanghu
 * @Date: 2019/11/18 15:56
 */
public interface AuthService {
    AuthResult auth(String accessId,String sign,String stringToSign,String url,String httpMethod);

}
