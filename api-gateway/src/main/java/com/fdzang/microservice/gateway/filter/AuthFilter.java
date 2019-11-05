package com.fdzang.microservice.gateway.filter;

import com.fdzang.microservice.common.util.Constant;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Arrays;
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
@ConfigurationProperties(prefix = "auth.gateway.white")
public class AuthFilter implements GatewayFilter, Ordered {

    private String[] white;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String url = exchange.getRequest().getURI().getPath();
        ServerHttpResponse response = exchange.getResponse();
        ServerHttpRequest request = exchange.getRequest();

        //跳过白名单
        if(null != white && Arrays.asList(white).contains(url)){
            return chain.filter(exchange);
        }

        //获取权限校验部分
        //Authorization: gateway:{AccessId}:{Signature}
        String authorizationHeader = exchange.getRequest().getHeaders().getFirst(Constant.Gateway.AUTH_HEADER);
        if(StringUtils.isBlank(authorizationHeader)){
            throw new IllegalArgumentException("bad request");
        }

        List<String> headers = Splitter.on(" ").trimResults().omitEmptyStrings().splitToList(authorizationHeader);
        if(CollectionUtils.isEmpty(headers) || headers.size() != 3 || !Constant.Gateway.AUTH_LABLE.equals(headers.get(0))){
            throw new IllegalArgumentException("bad request");
        }

        //校验时间戳是否合法
        String timestamp = exchange.getRequest().getHeaders().getFirst(Constant.Gateway.TIMESTAMP_HEADER);
        if (StringUtils.isBlank(timestamp) || isTimestampExpired(timestamp)) {

        }

        String accessId = headers.get(1);
        String signature = headers.get(2);

        String stringToSign;
        try {
            stringToSign = getStringToSign(request, timestamp);
        } catch (IOException e) {
            throw new RuntimeException("failed to get response body,", e);
        }

        return null;
    }


    private String getStringToSign(ServerHttpRequest request, String timestamp) throws IOException {
        /**
         String stringToSign=
         HTTP-Verb + "\n" +
         Content-MD5 + "\n"
         Content-Type + "\n" +
         Timestamp + "\n" +
         Headers +
         Url_String
         */

        // headers
        TreeMap<String, String> headersInSign = new TreeMap<>();
        HttpHeaders headers = request.getHeaders();
        for (Map.Entry<String,List<String>> header:headers.entrySet()) {
            String key = header.getKey();
            if (key.startsWith(Constant.Gateway.AUTH_HEADER_PREFIX)) {
                headersInSign.put(key, request.getHeaders().getFirst(key));

                log.info(request.getHeaders().getFirst(key));
                log.info(header.getValue().toString());
            }
        }

        StringBuilder headerStringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : headersInSign.entrySet()) {
            headerStringBuilder.append(entry.getKey()).append(":").append(entry.getValue()).append("\n");
        }
        String headerString = null;
        if (headerStringBuilder.length() != 0) {
            headerString = headerStringBuilder.deleteCharAt(headerStringBuilder.length()-1).toString();

            log.info(headerString);
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
        String originalUrl = request.getURI().getPath().replace("//", "/");
        log.info(originalUrl);

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

        // Content-MD5
//        byte[] bodyBytes = IOUtils.toByteArray(request.getReader(), "UTF-8");
//        String contentMd5 = null;
//        if (bodyBytes != null && bodyBytes.length != 0) {
//        }
        String uriString = uriStringBuilder.toString();

        String[] parts = {
                request.getMethod().toString(),
//                StringUtils.isNotBlank(contentMd5) ? contentMd5 : "",
//                StringUtils.isNotBlank(request.getContentType()) ? request.getContentType() : "",
                timestamp,
                headerString,
                uriString
        };

        log.info(parts.toString());
        return Joiner.on(Constant.Gateway.STRING_TO_SIGN_DELIM).skipNulls().join(parts);
    }

    private boolean isTimestampExpired(String timestamp){
        long l = NumberUtils.toLong(timestamp, 0L);
        if (l == 0) {
            return true;
        }

        return Math.abs(System.currentTimeMillis() - l) > Constant.Gateway.EXPIRE_TIME_SECONDS *1000;
    }

    @Override
    public int getOrder() {
        return -2;
    }
}
