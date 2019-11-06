package com.fdzang.microservice.gateway.config;

import com.fdzang.microservice.gateway.filter.AuthFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author tanghu
 * @Date: 2019/10/22 17:47
 */
@Slf4j
@Configuration
public class GatewayConfig {

//    @Bean
//    public FilterRegistrationBean modifyRequestFilterRegistration() {
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        AuthFilter filter = new AuthFilter();
//        registration.setFilter(filter);
//        registration.addUrlPatterns("/*");
//        registration.setName("modifyRequestFilter");
//        registration.setOrder(FilterOrder.MODIFY_REQUEST_FILTER_ORDER);
//        return registration;
//    }
}
