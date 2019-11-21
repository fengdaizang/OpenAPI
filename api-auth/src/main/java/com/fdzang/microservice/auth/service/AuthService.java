package com.fdzang.microservice.auth.service;

import com.fdzang.microservice.common.entity.auth.AuthResult;
import com.fdzang.microservice.common.exception.ErrorCode;
import com.fdzang.microservice.common.util.Constant;
import com.fdzang.microservice.common.util.DateUtil;
import com.fdzang.microservice.common.util.SignUtil;
import com.fdzang.microservice.data.common.dto.ApiDTO;
import com.fdzang.microservice.data.common.dto.ApiModuleDTO;
import com.fdzang.microservice.ucenter.common.dto.UserDTO;
import com.fdzang.microservice.ucenter.common.dto.UserModuleDTO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tanghu
 * @Date: 2019/11/18 15:56
 */
@Service
public class AuthService {

    @Autowired
    private BaseService baseService;

    public AuthResult auth(String accessId,String sign,String stringToSign,String url,String httpMethod){
        AuthResult result = new AuthResult();

        ApiDTO apiDTO = baseService.getApiByUrlAndMethod(url,httpMethod);
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

        ApiModuleDTO apiModuleDTO = baseService.getModuleByApiId(apiDTO.getId());
        if(apiDTO == null){
            result.setStatus(ErrorCode.DATA_NULL);
            result.setDescription("该API不属于任何模块");

            return result;
        }

        UserModuleDTO userModuleDTO = baseService.getByModuleIdAndAccessId(apiModuleDTO.getId(),accessId);
        if(apiDTO == null){
            result.setStatus(ErrorCode.DATA_NULL);
            result.setDescription("accessId非法");

            return result;
        }
        if (userModuleDTO.getStatus() != Constant.Status.ABLE){
            result.setStatus(ErrorCode.DATA_NULL);
            result.setDescription("accessId被禁用，请联系管理员处理");

            return result;
        }

        if(!DateUtil.isExpire(userModuleDTO.getExpireTime())){
            result.setStatus(ErrorCode.DATA_NULL);
            result.setDescription("使用期限超期，请重新申请"+apiModuleDTO.getName()+"模块");

            return result;
        }

        String compareSign = SignUtil.generateSignature(userModuleDTO.getSecret(), stringToSign);
        if (!StringUtils.equals(sign, compareSign)) {
            result.setStatus(ErrorCode.DATA_NULL);
            result.setDescription("签名错误，请确认key或签名方式是否正确");

            return result;
        }

        UserDTO userDTO = baseService.getUserById(userModuleDTO.getUserId());
        if(apiDTO == null){
            result.setStatus(ErrorCode.DATA_NULL);
            result.setDescription("用户不存在");

            return result;
        }
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
