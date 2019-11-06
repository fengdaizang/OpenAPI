package com.fdzang.microservice.common.entity.auth;

public enum AuthCode {
    SUCEESS(0, "authentication success"),
    ACCESSID_NOTEXIST(100, "access id not exists"),
    SIGNATURE_ERROR(101, "signature error"),
    ORGCODE_ERROR(102, "orgcode error"),
    APPLICATION_STATUS_ERROR(103, "application status error"),
    USER_STATUS_ERROR(104, "user status error"),
    API_NOTEXIST(105, "api not exists"),
    API_NOT_ACCESS(106, "no permission, api can't access"),
    WRONG_G7AC_HEADER(107, "wrong g7ac header"),
    SCOPE_NOT_FOUND_IN_APPLICATION(108, "scope not found in application"),
    CUSTOM_INTERFACE_STATUS_ERROR(109, "custom interface status error"),
    CUSTOM_INTERFACE_NOT_ACCESS(110, "no permission, custom interface can't access"),
    FAILD(10000, "authentication faild");

    private int code;
    private String description;

    private AuthCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getAuthCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }
}
