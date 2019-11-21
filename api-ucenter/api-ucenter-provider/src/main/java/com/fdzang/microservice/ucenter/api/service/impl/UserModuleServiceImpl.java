package com.fdzang.microservice.ucenter.api.service.impl;

import com.fdzang.microservice.ucenter.api.service.UserModuleService;
import com.fdzang.microservice.ucenter.common.dto.UserModuleDTO;
import com.fdzang.microservice.ucenter.dao.domain.UserModuleDO;
import com.fdzang.microservice.ucenter.dao.domain.UserModuleDOExample;
import com.fdzang.microservice.ucenter.dao.mapper.UserModuleMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tanghu
 * @Date: 2019/11/20 16:37
 */
@Service
public class UserModuleServiceImpl implements UserModuleService {

    @Autowired
    private UserModuleMapper userModuleMapper;

    @Override
    public UserModuleDTO getByModuleIdAndAccessId(Integer moduleId, String accessId) {
        UserModuleDOExample example = new UserModuleDOExample();
        example.createCriteria().andModuleIdEqualTo(moduleId)
                .andAccessIdEqualTo(accessId);

        List<UserModuleDO> userModuleDOS = userModuleMapper.selectByExample(example);
        if(CollectionUtils.isNotEmpty(userModuleDOS)){
            UserModuleDO userModuleDO = userModuleDOS.get(0);
            UserModuleDTO userModuleDTO = new UserModuleDTO();
            BeanUtils.copyProperties(userModuleDO,userModuleDTO);

            return userModuleDTO;
        }

        return null;
    }
}
