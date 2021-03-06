package com.fdzang.microservice.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author tanghu
 * @Date: 2019/11/18 15:40
 */
@EnableCaching
@EnableFeignClients
@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.fdzang.microservice"})
public class AuthApplication {
    public static void main(String[] args){
        SpringApplication.run(AuthApplication.class);
    }
}
