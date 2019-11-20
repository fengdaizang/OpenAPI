package com.fdzang.microservice.data.api.service.impl;

import com.fdzang.microservice.data.api.service.ApiModuleService;
import com.fdzang.microservice.data.common.dto.ApiModuleDTO;
import com.fdzang.microservice.data.dao.domain.ApiModuleDO;
import com.fdzang.microservice.data.dao.domain.ApiModuleRelDO;
import com.fdzang.microservice.data.dao.mapper.ApiModuleMapper;
import com.fdzang.microservice.data.dao.mapper.ApiModuleRelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tanghu
 * @Date: 2019/11/20 16:40
 */
@Service
public class ApiModuleServiceImpl implements ApiModuleService {

    @Autowired
    private ApiModuleMapper apiModuleMapper;

    @Autowired
    private ApiModuleRelMapper apiModuleRelMapper;

    @Override
    public ApiModuleDTO getModuleByApiId(Integer apiId) {
        ApiModuleRelDO relDO = apiModuleRelMapper.selectByPrimaryKey(apiId);

        if(relDO!=null){
            ApiModuleDO apiModuleDO = apiModuleMapper.selectByPrimaryKey(relDO.getModuleId());

            if(apiModuleDO!=null){
                ApiModuleDTO apiModuleDTO = new ApiModuleDTO();
                BeanUtils.copyProperties(apiModuleDO,apiModuleDTO);

                return apiModuleDTO;
            }
        }

        return null;
    }
}
