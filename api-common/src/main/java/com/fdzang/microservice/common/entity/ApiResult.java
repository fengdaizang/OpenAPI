package com.fdzang.microservice.common.entity;

import lombok.Data;

/**
 * @author tanghu
 * @Date: 2019/9/19 18:13
 */
@Data
public class ApiResult<T> {
    private Long code;
    private String msg;
    private T data;
}
