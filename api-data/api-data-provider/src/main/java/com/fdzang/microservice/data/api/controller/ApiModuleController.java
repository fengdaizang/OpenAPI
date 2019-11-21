package com.fdzang.microservice.data.api.controller;

import com.fdzang.microservice.common.entity.ApiResult;
import com.fdzang.microservice.common.framework.BaseController;
import com.fdzang.microservice.data.api.service.ApiModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tanghu
 * @Date: 2019/11/21 9:40
 */
@RestController
@RequestMapping("/api/module")
public class ApiModuleController extends BaseController {

    @Autowired
    private ApiModuleService apiModuleService;

    @GetMapping("/getByApiId")
    public ApiResult getByApiId(@RequestParam Integer apiId){

        return ok(apiModuleService.getModuleByApiId(apiId));
    }

}
