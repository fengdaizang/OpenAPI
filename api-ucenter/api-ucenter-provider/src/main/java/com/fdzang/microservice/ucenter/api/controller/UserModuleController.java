package com.fdzang.microservice.ucenter.api.controller;

import com.fdzang.microservice.common.entity.ApiResult;
import com.fdzang.microservice.common.framework.BaseController;
import com.fdzang.microservice.ucenter.api.service.UserModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tanghu
 * @Date: 2019/11/21 9:50
 */
@RestController
@RequestMapping("/user/module")
public class UserModuleController extends BaseController {

    @Autowired
    private UserModuleService userModuleService;

    @GetMapping("/getByModuleIdAndAccessId")
    public ApiResult getByModuleIdAndAccessId(@RequestParam Integer moduleId,
            @RequestParam String accessId){


        return ok(userModuleService.getByModuleIdAndAccessId(moduleId, accessId));
    }

}
