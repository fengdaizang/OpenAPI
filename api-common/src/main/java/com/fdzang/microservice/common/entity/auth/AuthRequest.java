package com.fdzang.microservice.common.entity.auth;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * @author tanghu
 * @Date: 2019/11/6 13:14
 */
@Data
@ToString
public class AuthRequest {
    @NotBlank
    private String accessId;

    @NotBlank
    private String sign;

    @NotBlank
    private String stringToSign;

    @NotBlank
    private String uri;

    @NotBlank
    private String httpMethod;
}
