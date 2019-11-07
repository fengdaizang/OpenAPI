package com.fdzang.microservice.gateway.entity;

public enum GatewayResultEnums {
    SUCC(0L, "succ"),
    FAIL(100000L, "fail"),
    SERVICE_UNAVAILABLE(100002L, "service unavailable"),
    ILLEGAL_PARAMETER(100041L, "invalid parameter");


    private Long code;
    private String msg;

    GatewayResultEnums(Long code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Long getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
