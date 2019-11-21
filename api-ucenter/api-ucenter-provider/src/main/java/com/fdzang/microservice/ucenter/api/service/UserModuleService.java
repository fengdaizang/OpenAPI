package com.fdzang.microservice.ucenter.api.service;

import com.fdzang.microservice.ucenter.common.dto.UserModuleDTO;

/**
 * @author tanghu
 * @Date: 2019/11/20 16:36
 */
public interface UserModuleService {
    UserModuleDTO getByModuleIdAndAccessId(Integer moduleId,String accessId);
}
