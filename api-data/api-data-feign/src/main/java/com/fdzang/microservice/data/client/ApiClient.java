package com.fdzang.microservice.data.client;

import com.fdzang.microservice.common.entity.ApiResult;
import com.fdzang.microservice.data.client.impl.ApiClientHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author tanghu
 * @Date: 2019/11/20 17:29
 */
@FeignClient(value = "api-data-v1",fallbackFactory = ApiClientHystrix.class)
public interface ApiClient {

    @GetMapping("/api/getApiByUrlAndMethod")
    ApiResult getApiByUrlAndMethod(@RequestParam String url, @RequestParam String method);
}
