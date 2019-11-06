package com.fdzang.microservice.gateway.gateway;

import com.fdzang.microservice.gateway.util.Constant;
import com.fdzang.microservice.gateway.util.WhiteUrl;
import com.google.common.base.Splitter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author tanghu
 * @Date: 2019/11/6 15:39
 */
@Slf4j
@Component
public class ModifyRequestFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String requestId = exchange.getAttribute(Constant.REQUEST_TRACE_ID);
        String url = exchange.getRequest().getURI().getPath();

        ServerHttpRequest request = exchange.getRequest();

        //跳过白名单
        if(null != WhiteUrl.getWhite() && WhiteUrl.getWhite().contains(url)){
            return chain.filter(exchange);
        }

        String orgCode = exchange.getAttribute(Constant.ORG_CODE_HEADER);
        String accessID = exchange.getAttribute(Constant.ACCESSID_HEADER);
        String username = exchange.getAttribute(Constant.DEV_ACCOUNT);
        String serviceName = exchange.getAttribute(Constant.SERVICE_NAME);

        List<String> strings = Splitter.on("/").omitEmptyStrings().trimResults().limit(3).splitToList(url);
        String newServletPath = "http://" + serviceName + "/" + strings.get(2);

        //修改请求体
        Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        Route newRoute = Route.async()
                .asyncPredicate(route.getPredicate())
                .filters(route.getFilters())
                .id(route.getId())
                .order(route.getOrder())
                .uri(newServletPath).build();

        exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR,newRoute);

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Constant.Order.MODIFY_REQUEST_ORDER;
    }
}
