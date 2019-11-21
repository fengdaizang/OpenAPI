package com.fdzang.microservice.ucenter.api.controller;

import com.fdzang.microservice.common.entity.ApiResult;
import com.fdzang.microservice.common.framework.BaseController;
import com.fdzang.microservice.ucenter.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tanghu
 * @Date: 2019/11/21 10:12
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ApiResult getById(@PathVariable("id")Integer id){

        return ok(userService.getById(id));
    }
}
