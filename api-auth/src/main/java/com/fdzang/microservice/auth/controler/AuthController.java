package com.fdzang.microservice.auth.controler;

import com.fdzang.microservice.auth.service.AuthService;
import com.fdzang.microservice.common.entity.auth.AuthCode;
import com.fdzang.microservice.common.entity.auth.AuthRequest;
import com.fdzang.microservice.common.entity.auth.AuthResult;
import com.google.common.base.Splitter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author tanghu
 * @Date: 2019/11/18 15:39
 */
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/auth")
    public AuthResult auth(@RequestBody AuthRequest auth){
        String accessId = auth.getAccessId();
        String frontSign = auth.getSign();

        AuthResult authResult = authService.auth(
                accessId,
                frontSign,
                auth.getStringToSign(),
                auth.getUri(),
                auth.getHttpMethod());

        log.info("auth, accessId:{}, sign:{}, uri:{},result:{}, stringToSign:{}",
                auth.getAccessId(),auth.getSign(), auth.getUri(), authResult,
                auth.getStringToSign().replace("\n", "\\n"));

        return authResult;
    }
}
