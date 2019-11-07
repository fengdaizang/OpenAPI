package com.fdzang.microservice.gateway.gateway;

import com.fdzang.microservice.gateway.util.GatewayConstant;
import com.fdzang.microservice.gateway.util.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 生成一个请求的特定id
 * @author tanghu
 * @Date: 2019/11/5 18:42
 */
@Slf4j
@Component
public class SerialNoFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        String requestId= request.getHeaders().getFirst(GatewayConstant.REQUEST_TRACE_ID);
        if (StringUtils.isEmpty(requestId)) {
            Object attribute = exchange.getAttribute(GatewayConstant.REQUEST_TRACE_ID);
            if (attribute == null) {
                requestId = String.valueOf(IdWorker.getWorkerId());
                exchange.getAttributes().put(GatewayConstant.REQUEST_TRACE_ID,requestId);
            }
        }else{
            exchange.getAttributes().put(GatewayConstant.REQUEST_TRACE_ID,requestId);
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return GatewayConstant.Order.SERIAL_NO_ORDER;
    }
}
