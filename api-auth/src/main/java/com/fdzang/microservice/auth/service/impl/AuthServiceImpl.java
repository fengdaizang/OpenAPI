package com.fdzang.microservice.auth.service.impl;

import com.fdzang.microservice.auth.service.AuthService;
import com.fdzang.microservice.common.entity.auth.AuthCode;
import com.fdzang.microservice.common.entity.auth.AuthResult;
import com.fdzang.microservice.common.util.SignUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

/**
 * @author tanghu
 * @Date: 2019/11/18 16:00
 */
public class AuthServiceImpl implements AuthService {
    @Override
    public AuthResult auth(String accessId, String sign, String stringToSign, String url, String httpMethod) {
//        Api api = configurationService.getByUri(uri,httpMethod);
//        if (api == null) {
//            AuthResult authResult = new AuthResult(OpenApiAuthCode.API_NOTEXIST.getCode(),OpenApiAuthCode.API_NOTEXIST.getDescription());
//            return authResult;
//        }
//        if (api.getEnable() == NormalStatusEnum.Disabled.getCode()) {
//            AuthResult authResult = new AuthResult(OpenApiAuthCode.API_NOT_ACCESS.getCode(),OpenApiAuthCode.API_NOT_ACCESS.getDescription());
//            return authResult;
//        }
//
//
//        //2、获取api对应的modules
//        List<UserModule> userModules = configurationService.getUserModulebyApiIdAccessId(api.getId(),accessId);
//        if (CollectionUtils.isEmpty(userModules)) {
//            AuthResult authResult = new AuthResult(OpenApiAuthCode.API_NOT_ACCESS.getCode(),OpenApiAuthCode.API_NOT_ACCESS.getDescription());
//            return authResult;
//        }
//        for(UserModule userModule : userModules) {
//
//            //验证usermodule是否审核通过
//            if (userModule.getStatus() != ApiStatusEnum.Approved.getCode()) {
//                continue;
//            }
//            // 验证签名
//            String compareSign = SignUtil.generateSignature(userModule.getSecret(), stringToSign);
//            if (!StringUtils.equals(frontSign, compareSign)) {
//                continue;
//            }
//
//            User user = configurationService.getUserById(userModule.getUserId().intValue());
//            //用户是否OK
//            if (!user.getEnabled()) {
//                continue;
//            }
//
//
//            AuthResult result = new AuthResult(AuthCode.SUCEESS);
//            result.setOrgCode(user.getOrgcode());
//            result.setUsername(user.getUsername());
//            result.setServiceName(api.getServiceName());
//
//            return result;
//        }
//        //fixme:各种异常没有正确输出
//        String description = String.format("%s,accessId : %s",
//                OpenApiAuthCode.FAILD.getDescription(), stringToSign);
//        return new AuthResult(OpenApiAuthCode.FAILD.getCode(), description);

        return null;
    }
}
