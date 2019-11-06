package com.fdzang.microservice.gateway.filter;

import com.fdzang.microservice.gateway.util.Constant;
import com.fdzang.microservice.gateway.util.IdWorker;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 生成一个请求的特定id
 * @author tanghu
 * @Date: 2019/11/5 18:42
 */
public class SerialNoFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        String requestId= request.getHeaders().getFirst(Constant.REQUEST_TRACE_ID);
        if (StringUtils.isEmpty(requestId)) {
            requestId = String.valueOf(IdWorker.getWorkerId());
            request.getHeaders().add(Constant.REQUEST_TRACE_ID, requestId);
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Constant.Order.SERIAL_NO_ORDER;
    }
}
