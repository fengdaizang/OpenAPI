package com.fdzang.microservice.gateway.entity;

import lombok.Data;

/**
 * @author tanghu
 * @Date: 2019/11/7 10:03
 */
@Data
public class GatewayError {
    private Long timestamp;
    private Long status;
    private String error;
    private String exception;
    private String message;
}
