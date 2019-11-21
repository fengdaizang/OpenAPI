package com.fdzang.microservice.data.client;

import com.fdzang.microservice.common.entity.ApiResult;
import com.fdzang.microservice.data.client.impl.ApiModuleClientHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author tanghu
 * @Date: 2019/11/21 9:42
 */
@FeignClient(value = "api-data-v1",fallbackFactory = ApiModuleClientHystrix.class)
public interface ApiModuleClient {

    @GetMapping("/api/module/getByApiId")
    ApiResult getByApiId(@RequestParam Integer apiId);
}
