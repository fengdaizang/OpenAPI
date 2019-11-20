package com.fdzang.microservice.ucenter.api.controller;

import com.fdzang.microservice.common.entity.ApiResult;
import com.fdzang.microservice.common.framework.BaseController;
import com.fdzang.microservice.ucenter.api.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

/**
 * @author tanghu
 * @Date: 2019/11/18 19:01
 */
@RestController
@RequestMapping("/api")
public class ApiController extends BaseController {

    @Autowired
    private ApiService apiService;

    @GetMapping("/getApiByUrlAndMethod")
    public ApiResult getApiByUrlAndMethod(@RequestParam String url,@RequestParam String httpMethod){

        return ok(apiService.getApiByUrlAndMethod(url, httpMethod));
    }

}
