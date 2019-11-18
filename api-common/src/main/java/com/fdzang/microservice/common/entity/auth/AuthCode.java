package com.fdzang.microservice.common.entity.auth;

public enum AuthCode {
    SUCEESS(0L, "authentication success"),
    ACCESSID_NOTEXIST(100L, "access id not exists"),
    SIGNATURE_ERROR(101L, "signature error"),
    ORGCODE_ERROR(102L, "orgcode error"),
    APPLICATION_STATUS_ERROR(103L, "application status error"),
    USER_STATUS_ERROR(104L, "user status error"),
    API_NOTEXIST(105L, "api not exists"),
    API_NOT_ACCESS(106L, "no permission, api can't access"),
    WRONG_G7AC_HEADER(107L, "wrong g7ac header"),
    SCOPE_NOT_FOUND_IN_APPLICATION(108L, "scope not found in application"),
    CUSTOM_INTERFACE_STATUS_ERROR(109L, "custom interface status error"),
    CUSTOM_INTERFACE_NOT_ACCESS(110L, "no permission, custom interface can't access"),
    FAILD(10000L, "authentication faild");

    private long code;
    private String description;

    private AuthCode(long code, String description) {
        this.code = code;
        this.description = description;
    }

    public long getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }
}
