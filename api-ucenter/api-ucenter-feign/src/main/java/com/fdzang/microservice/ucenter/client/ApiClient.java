package com.fdzang.microservice.ucenter.client;

import com.fdzang.microservice.common.entity.ApiResult;
import com.fdzang.microservice.ucenter.client.impl.ApiClientHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author tanghu
 * @Date: 2019/11/19 18:34
 */

@FeignClient(value = "altair-ucenter-v1",fallbackFactory = ApiClientHystrix.class)
public interface ApiClient {
    @GetMapping("/getApiByUrlAndMethod")
    ApiResult getApiByUrlAndMethod(@RequestParam String url, @RequestParam String httpMethod);
}
