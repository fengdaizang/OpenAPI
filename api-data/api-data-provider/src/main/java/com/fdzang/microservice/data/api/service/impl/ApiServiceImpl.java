package com.fdzang.microservice.data.api.service.impl;

import com.fdzang.microservice.data.api.service.ApiService;
import com.fdzang.microservice.data.common.dto.ApiDTO;
import com.fdzang.microservice.data.dao.domain.ApiDO;
import com.fdzang.microservice.data.dao.domain.ApiDOExample;
import com.fdzang.microservice.data.dao.mapper.ApiMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tanghu
 * @Date: 2019/11/19 18:08
 */
@Service
public class ApiServiceImpl implements ApiService {

    @Autowired
    private ApiMapper apiMapper;

    @Override
    public ApiDTO getApiByUrlAndMethod(String url, String method) {
        ApiDOExample example = new ApiDOExample();
        example.createCriteria().andUrlEqualTo(url).andMethodEqualTo(method);

        List<ApiDO> apiDOS = apiMapper.selectByExample(example);
        if(CollectionUtils.isNotEmpty(apiDOS)){
            ApiDTO apiDTO = new ApiDTO();
            ApiDO apiDO = apiDOS.get(0);
            BeanUtils.copyProperties(apiDO,apiDTO);

            return apiDTO;
        }

        return null;
    }
}
