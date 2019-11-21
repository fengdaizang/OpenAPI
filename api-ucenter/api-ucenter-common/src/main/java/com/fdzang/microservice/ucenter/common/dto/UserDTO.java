package com.fdzang.microservice.ucenter.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author tanghu
 * @Date: 2019/11/21 9:55
 */
@Data
public class UserDTO {
    private Integer id;

    private String username;

    private String password;

    private String usercode;

    private String nickname;

    private String phone;

    private String email;

    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;
}
