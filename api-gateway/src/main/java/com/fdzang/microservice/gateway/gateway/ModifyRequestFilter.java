package com.fdzang.microservice.gateway.gateway;

import com.fdzang.microservice.gateway.util.GatewayConstant;
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
        String url = exchange.getRequest().getURI().getPath();
        ServerHttpRequest request = exchange.getRequest();

        //跳过白名单
        if(null != WhiteUrl.getWhite() && WhiteUrl.getWhite().contains(url)){
            return chain.filter(exchange);
        }

        String serviceName = exchange.getAttribute(GatewayConstant.SERVICE_NAME);

        //修改路由
        Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        Route newRoute = Route.async()
                .asyncPredicate(route.getPredicate())
                .filters(route.getFilters())
                .id(route.getId())
                .order(route.getOrder())
                .uri(GatewayConstant.URI.LOAD_BALANCE+serviceName).build();

        exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR,newRoute);

        //修改请求路径
        List<String> strings = Splitter.on("/").omitEmptyStrings().trimResults().limit(3).splitToList(url);
        String newServletPath = "/" + strings.get(2);
        ServerHttpRequest newRequest = request.mutate().path(newServletPath).build();

        return chain.filter(exchange.mutate().request(newRequest).build());
    }

    @Override
    public int getOrder() {
        return GatewayConstant.Order.MODIFY_REQUEST_ORDER;
    }
}
