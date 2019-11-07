package com.fdzang.microservice.gateway.gateway;

import com.alibaba.fastjson.JSON;
import com.fdzang.microservice.common.entity.ApiResult;
import com.fdzang.microservice.gateway.entity.GatewayError;
import com.fdzang.microservice.gateway.entity.GatewayResult;
import com.fdzang.microservice.gateway.entity.GatewayResultEnums;
import com.fdzang.microservice.gateway.util.GatewayConstant;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;

/**
 * @author tanghu
 * @Date: 2019/11/7 8:58
 */
@Slf4j
@Component
public class ModifyResponseFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String requestId = exchange.getAttribute(GatewayConstant.REQUEST_TRACE_ID);
        ServerHttpResponse originalResponse = exchange.getResponse();
        DataBufferFactory bufferFactory = originalResponse.bufferFactory();
        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                if (body instanceof Flux) {
                    Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
                    return super.writeWith(fluxBody.map(dataBuffer -> {
                        byte[] content = new byte[dataBuffer.readableByteCount()];
                        dataBuffer.read(content);
                        //释放掉内存
                        DataBufferUtils.release(dataBuffer);

                        String originalbody = new String(content, Charset.forName("UTF-8"));
                        String finalBody = originalbody;

                        ApiResult apiResult = JSON.parseObject(originalbody,ApiResult.class);

                        GatewayResult result = new GatewayResult();
                        result.setCode(GatewayResultEnums.SUCC.getCode());
                        result.setMsg(GatewayResultEnums.SUCC.getMsg());
                        result.setReq_id(requestId);
                        if (apiResult.getCode() == null && apiResult.getMsg() == null) {
                            // 尝试解析body为网关的错误信息
                            GatewayError gatewayError = JSON.parseObject(originalbody,GatewayError.class);
                            result.setSub_code(gatewayError.getStatus());
                            result.setSub_msg(gatewayError.getMessage());
                        } else {
                            result.setSub_code(apiResult.getCode());
                            result.setSub_msg(apiResult.getMsg());
                        }

                        result.setData(apiResult.getData());

                        finalBody = JSON.toJSONString(result);

                        return bufferFactory.wrap(finalBody.getBytes());
                    }));
                }

                return super.writeWith(body);
            }
        };
        return chain.filter(exchange.mutate().response(decoratedResponse).build());
    }

    @Override
    public int getOrder() {
        return GatewayConstant.Order.MODIFY_RESPONSE_ORDER;
    }
}
