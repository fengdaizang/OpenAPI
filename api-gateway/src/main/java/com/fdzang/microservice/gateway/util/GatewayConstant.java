package com.fdzang.microservice.gateway.util;

import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;

/**
 * @author tanghu
 * @Date: 2019/11/5 18:44
 */
public class GatewayConstant {
    public static final String AUTH_HEADER = "Authorization";
    public static final String AUTH_LABLE = "gateway";

    public static final String REQUEST_TRACE_ID = "X-TH-Trace-Id";
    public static final String TIMESTAMP_HEADER = "X-TH-OpenAPI-Timestamp";
    public static final String AUTH_HEADER_PREFIX = "x-TH-ca-";

    public final static String CONTENTE_MD5 = "Content-MD5";
    public static final String SERVICE_NAME = "service-name";
    public final static String STRING_TO_SIGN_DELIM = "\n";

    public static final long EXPIRE_TIME_SECONDS = 40L;

    public class Order {
        public static final int SERIAL_NO_ORDER = -10;
        public static final int AUTH_ORDER = -9;
        public static final int MODIFY_REQUEST_ORDER = -8;
        public static final int MODIFY_RESPONSE_ORDER = NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 1;
    }

    public class URI {
        public static final String LOAD_BALANCE = "lb://";
        public static final String WEB_SOCKET = "ws://";
        public static final String HTTP = "http://";
        public static final String HTTPS = "https://";
    }

}
