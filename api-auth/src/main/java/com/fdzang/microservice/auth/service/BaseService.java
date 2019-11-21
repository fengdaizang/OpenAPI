package com.fdzang.microservice.auth.service;

import com.fdzang.microservice.common.entity.ApiResult;
import com.fdzang.microservice.data.client.ApiClient;
import com.fdzang.microservice.data.client.ApiModuleClient;
import com.fdzang.microservice.data.common.dto.ApiDTO;
import com.fdzang.microservice.data.common.dto.ApiModuleDTO;
import com.fdzang.microservice.ucenter.client.UserClient;
import com.fdzang.microservice.ucenter.client.UserModuleClient;
import com.fdzang.microservice.ucenter.common.dto.UserDTO;
import com.fdzang.microservice.ucenter.common.dto.UserModuleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author tanghu
 * @Date: 2019/11/21 14:33
 */
@Service
public class BaseService {

    @Autowired
    private ApiClient apiClient;

    @Autowired
    private UserClient userClient;

    @Autowired
    private ApiModuleClient apiModuleClient;

    @Autowired
    private UserModuleClient userModuleClient;

    @Cacheable(cacheNames = "api-url-method", key = "'api-url-method:' + #p0 + '-' + #p1", unless = "#result==null")
    public ApiDTO getApiByUrlAndMethod(String url,String method){
        ApiResult apiResult = apiClient.getApiByUrlAndMethod(url, method);

        if(apiResult == null || apiResult.getData() == null){

            return null;
        }

        ApiDTO apiDTO = (ApiDTO)apiResult.getData();

        return apiDTO;
    }

    @Cacheable(cacheNames = "api-module-id",key = "'api-module-id:' + #p0", unless = "#result==null")
    public ApiModuleDTO getModuleByApiId(Integer id){
        ApiResult apiResult = apiModuleClient.getByApiId(id);

        if(apiResult == null || apiResult.getData() == null){

            return null;
        }

        ApiModuleDTO apiModuleDTO = (ApiModuleDTO)apiResult.getData();

        return apiModuleDTO;
    }

    @Cacheable(cacheNames = "user-module-id-access",key = "'user-module-id-access:' + #p0 + '-' + #p1", unless = "#result==null")
    public UserModuleDTO getByModuleIdAndAccessId(Integer moduleId,String accessId){
        ApiResult apiResult = userModuleClient.getByModuleIdAndAccessId(moduleId,accessId);
        if(apiResult == null || apiResult.getData() == null){

            return null;
        }
        UserModuleDTO userModuleDTO = (UserModuleDTO)apiResult.getData();

        return  userModuleDTO;
    }

    public UserDTO getUserById(Integer id){
        ApiResult apiResult = userClient.getById(id);

        if(apiResult == null || apiResult.getData() == null){

            return null;
        }
        UserDTO userDTO = (UserDTO)apiResult.getData();

        return  userDTO;
    }

}
