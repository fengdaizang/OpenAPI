package com.fdzang.microservice.gateway.gateway;

import com.fdzang.microservice.common.entity.auth.AuthCode;
import com.fdzang.microservice.common.entity.auth.AuthRequest;
import com.fdzang.microservice.common.entity.auth.AuthResult;
import com.fdzang.microservice.gateway.service.AuthService;
import com.fdzang.microservice.gateway.util.Constant;
import com.fdzang.microservice.gateway.util.WhiteUrl;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 权限校验
 * @author tanghu
 * @Date: 2019/10/22 18:00
 */
@Slf4j
@Component
public class AuthFilter implements GlobalFilter, Ordered {

    @Autowired
    private AuthService authService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String requestId = exchange.getAttribute(Constant.REQUEST_TRACE_ID);
        String url = exchange.getRequest().getURI().getPath();

        ServerHttpRequest request = exchange.getRequest();

        //跳过白名单
        if(null != WhiteUrl.getWhite() && WhiteUrl.getWhite().contains(url)){
            return chain.filter(exchange);
        }

        //获取权限校验部分
        //Authorization: gateway:{AccessId}:{Signature}
        String authHeader = exchange.getRequest().getHeaders().getFirst(Constant.AUTH_HEADER);
        if(StringUtils.isBlank(authHeader)){
            log.warn("request has no authorization header, uuid:{}, request:{}",requestId, url);

            throw new IllegalArgumentException("bad request");
        }

        List<String> auths = Splitter.on(":").trimResults().omitEmptyStrings().splitToList(authHeader);
        if(CollectionUtils.isEmpty(auths) || auths.size() != 3 || !Constant.AUTH_LABLE.equals(auths.get(0))){
            log.warn("bad authorization header, uuid:{}, request:[{}], header:{}",
                    requestId, url, authHeader);

            throw new IllegalArgumentException("bad request");
        }

        //校验时间戳是否合法
        String timestamp = exchange.getRequest().getHeaders().getFirst(Constant.TIMESTAMP_HEADER);
        if (StringUtils.isBlank(timestamp) || isTimestampExpired(timestamp)) {
            log.warn("wrong timestamp:{}, uuid:{}, request:{}",
                    timestamp, requestId, url);
        }

        String accessId = auths.get(1);
        String sign = auths.get(2);

        String stringToSign = getStringToSign(request, timestamp);

        AuthRequest authRequest = new AuthRequest();
        authRequest.setAccessId(accessId);
        authRequest.setSign(sign);
        authRequest.setStringToSign(stringToSign);
        authRequest.setHttpMethod(request.getMethodValue());
        authRequest.setUri(url);

        AuthResult authResult = authService.auth(authRequest);

        if (authResult.getStatus() != AuthCode.SUCEESS.getAuthCode()) {
            log.warn("checkSign failed, uuid:{},  accessId:{}, request:[{}], error:{}",
                    requestId, accessId, url, authResult.getDescription());
            throw new RuntimeException(authResult.getDescription());
        }

        log.info("request auth finished, uuid:{}, orgCode:{}, userName:{}, accessId:{}, request:{}, serviceName:{}",
                requestId, authResult.getOrgCode(),
                authResult.getUsername(), accessId,
                url, authResult.getServiceName());

        exchange.getAttributes().put(Constant.ORG_CODE_HEADER,authResult.getOrgCode());
        exchange.getAttributes().put(Constant.ACCESSID_HEADER,accessId);
        exchange.getAttributes().put(Constant.DEV_ACCOUNT,authResult.getUsername());
        exchange.getAttributes().put(Constant.SERVICE_NAME,authResult.getServiceName());

        return chain.filter(exchange);
    }


    private String getStringToSign(ServerHttpRequest request, String timestamp){
        // headers
        TreeMap<String, String> headersInSign = new TreeMap<>();
        HttpHeaders headers = request.getHeaders();
        for (Map.Entry<String,List<String>> header:headers.entrySet()) {
            String key = header.getKey();
            if (key.startsWith(Constant.AUTH_HEADER_PREFIX)) {
                headersInSign.put(key, header.getValue().get(0));
            }
        }

        StringBuilder headerStringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : headersInSign.entrySet()) {
            headerStringBuilder.append(entry.getKey()).append(":").append(entry.getValue()).append("\n");
        }
        String headerString = null;
        if (headerStringBuilder.length() != 0) {
            headerString = headerStringBuilder.deleteCharAt(headerStringBuilder.length()-1).toString();
        }

        // Url_String
        TreeMap<String, String> paramsInSign = new TreeMap<>();
        MultiValueMap<String, String> parameterMap = request.getQueryParams();
        if (MapUtils.isNotEmpty(parameterMap)) {
            for (Map.Entry<String, List<String>> entry : parameterMap.entrySet()) {
                paramsInSign.put(entry.getKey(), entry.getValue().get(0));
            }
        }

        // 原始url
        String originalUrl = request.getURI().getPath();

        StringBuilder uriStringBuilder = new StringBuilder(originalUrl);
        if (!parameterMap.isEmpty()) {
            uriStringBuilder.append("?");
            for (Map.Entry<String, String> entry : paramsInSign.entrySet()) {
                uriStringBuilder.append(entry.getKey());
                if (StringUtils.isNotBlank(entry.getValue())) {
                    uriStringBuilder.append("=").append(entry.getValue());
                }
                uriStringBuilder.append("&");
            }
            uriStringBuilder.deleteCharAt(uriStringBuilder.length()-1);
        }

        String uriString = uriStringBuilder.toString();

        String contentType = headers.getFirst(HttpHeaders.CONTENT_TYPE);

        //这里可以对请求参数进行MD5校验，暂时不做
        String contentMd5 = headers.getFirst(Constant.CONTENTE_MD5);

        String[] parts = {
                request.getMethodValue(),
                StringUtils.isNotBlank(contentMd5) ? contentMd5 : "",
                StringUtils.isNotBlank(contentType) ? contentType : "",
                timestamp,
                headerString,
                uriString
        };

        return Joiner.on(Constant.STRING_TO_SIGN_DELIM).skipNulls().join(parts);
    }

    private boolean isTimestampExpired(String timestamp){
        long l = NumberUtils.toLong(timestamp, 0L);
        if (l == 0) {
            return true;
        }

        return Math.abs(System.currentTimeMillis() - l) > Constant.EXPIRE_TIME_SECONDS *1000;
    }

    @Override
    public int getOrder() {
        return Constant.Order.AUTH_ORDER;
    }
}
