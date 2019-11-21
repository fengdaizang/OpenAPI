package com.fdzang.microservice.ucenter.client;

import com.fdzang.microservice.common.entity.ApiResult;
import com.fdzang.microservice.ucenter.client.impl.UserClientHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author tanghu
 * @Date: 2019/11/21 10:10
 */
@FeignClient(value = "api-ucenter-v1",fallbackFactory = UserClientHystrix.class)
public interface UserClient {
    @GetMapping("/user/{id}")
    ApiResult getById(@RequestParam("id") Integer id);
}
