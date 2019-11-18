package com.fdzang.microservice.common.entity.auth;

import lombok.Data;

/**
 * @author tanghu
 * @Date: 2019/11/6 13:13
 */
@Data
public class AuthResult {
    private long status;
    private String description;
    private String orgCode;
    private String serviceName;
    private String username;
}
