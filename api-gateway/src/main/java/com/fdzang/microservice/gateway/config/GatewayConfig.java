package com.fdzang.microservice.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author tanghu
 * @Date: 2019/10/22 17:47
 */
@Slf4j
@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator addRoute(RouteLocatorBuilder builder){
        return builder.routes()
                .route("test1",
                        r->r.path("/v2/**").uri("https://www.baidu.com"))
                .route("test2",
                        r->r.path("/api-test-v1/**").uri("https://www.sina.com"))
                .build();
    }

}
