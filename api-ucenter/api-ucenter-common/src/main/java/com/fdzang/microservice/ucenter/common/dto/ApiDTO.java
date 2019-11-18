package com.fdzang.microservice.ucenter.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author tanghu
 * @Date: 2019/11/18 18:54
 */
@Data
public class ApiDTO {
    private Integer id;

    private String name;

    private String description;

    private String uri;

    private String method;

    private String serviceName;

    private Integer status;

    private Integer writer;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;
}
