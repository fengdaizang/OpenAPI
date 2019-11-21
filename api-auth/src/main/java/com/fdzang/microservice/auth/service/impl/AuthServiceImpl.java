package com.fdzang.microservice.auth.service.impl;

import com.fdzang.microservice.auth.service.AuthService;
import com.fdzang.microservice.common.entity.ApiResult;
import com.fdzang.microservice.common.entity.auth.AuthResult;
import com.fdzang.microservice.common.exception.ErrorCode;
import com.fdzang.microservice.common.util.Constant;
import com.fdzang.microservice.common.util.SignUtil;
import com.fdzang.microservice.data.client.ApiClient;
import com.fdzang.microservice.data.client.ApiModuleClient;
import com.fdzang.microservice.data.common.dto.ApiDTO;
import com.fdzang.microservice.data.common.dto.ApiModuleDTO;
import com.fdzang.microservice.ucenter.client.UserClient;
import com.fdzang.microservice.ucenter.client.UserModuleClient;
import com.fdzang.microservice.ucenter.common.dto.UserDTO;
import com.fdzang.microservice.ucenter.common.dto.UserModuleDTO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tanghu
 * @Date: 2019/11/18 16:00
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private ApiClient apiClient;

    @Autowired
    private UserClient userClient;

    @Autowired
    private ApiModuleClient apiModuleClient;

    @Autowired
    private UserModuleClient userModuleClient;

    @Override
    public AuthResult auth(String accessId, String sign, String stringToSign, String url, String httpMethod) {
        AuthResult result = new AuthResult();

        ApiResult apiResult = apiClient.getApiByUrlAndMethod(url, httpMethod);

        if(apiResult == null){
            result.setStatus(ErrorCode.DATA_NULL);
            result.setDescription("请求失败");

            return result;
        }

        ApiDTO apiDTO = (ApiDTO)apiResult.getData();
        if(apiDTO == null){
            result.setStatus(ErrorCode.DATA_NULL);
            result.setDescription("API不存在");

            return result;
        }

        if(apiDTO.getStatus() != Constant.Status.ABLE){
            result.setStatus(ErrorCode.DATA_NULL);
            result.setDescription("API暂时不开放");

            return result;
        }

        apiResult = apiModuleClient.getByApiId(apiDTO.getId());

        if(apiResult == null && apiResult.getData() == null){
            result.setStatus(ErrorCode.DATA_NULL);
            result.setDescription("通讯异常");

            return result;
        }

        ApiModuleDTO apiModuleDTO = (ApiModuleDTO)apiResult.getData();

        apiResult = userModuleClient.getByModuleIdAndAccessId(apiModuleDTO.getId(),accessId);

        if(apiResult == null && apiResult.getData() == null){
            result.setStatus(ErrorCode.DATA_NULL);
            result.setDescription("通讯异常");

            return result;
        }

        UserModuleDTO userModuleDTO = (UserModuleDTO)apiResult.getData();
        if (userModuleDTO.getStatus() != Constant.Status.ABLE){
            result.setStatus(ErrorCode.DATA_NULL);
            result.setDescription("通讯异常");

            return result;
        }

        //FIXME:判断该module是否超期

        String compareSign = SignUtil.generateSignature(userModuleDTO.getSecret(), stringToSign);
        if (!StringUtils.equals(sign, compareSign)) {
            result.setStatus(ErrorCode.DATA_NULL);
            result.setDescription("签名异常");

            return result;
        }

        apiResult = userClient.getById(userModuleDTO.getUserId());
        if(apiResult == null && apiResult.getData() == null){
            result.setStatus(ErrorCode.DATA_NULL);
            result.setDescription("通讯异常");

            return result;
        }

        UserDTO userDTO = (UserDTO)apiResult.getData();
        if(userDTO.getStatus() != Constant.Status.ABLE){
            result.setStatus(ErrorCode.DATA_NULL);
            result.setDescription("账户已冻结");

            return result;
        }

        result.setUserCode(userDTO.getUsercode());
        result.setUsername(userDTO.getUsername());
        result.setServiceName(apiDTO.getServiceName());
        result.setStatus(ErrorCode.SUCCESS);

        return result;
    }
}
