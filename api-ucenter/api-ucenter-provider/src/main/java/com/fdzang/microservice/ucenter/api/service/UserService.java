package com.fdzang.microservice.ucenter.api.service;

import com.fdzang.microservice.ucenter.common.dto.UserDTO;

/**
 * @author tanghu
 * @Date: 2019/11/21 9:59
 */
public interface UserService {

    UserDTO getById(Integer id);
}
