package com.fdzang.microservice.data.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author tanghu
 * @Date: 2019/11/20 16:19
 */
@Data
public class ApiModuleDTO {
    private Integer id;

    private String code;

    private String name;

    private String description;

    private String iconUrl;

    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;
}
