package com.fdzang.microservice.ucenter.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author tanghu
 * @Date: 2019/11/21 9:55
 */
@Data
public class UserModuleDTO {
    private Integer id;

    private Integer userId;

    private Integer moduleId;

    private String accessId;

    private String secret;

    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date applyTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date auditTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date expireTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;
}
