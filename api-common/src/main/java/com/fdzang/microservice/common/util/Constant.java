package com.fdzang.microservice.common.util;

/**
 * @author tanghu
 * @Date: 2019/10/21 15:34
 */
public class Constant {

    /**
     * 网关相关配置
     */
    public class Gateway{
        public static final String AUTH_HEADER = "Authorization";
        public static final String AUTH_LABLE = "gateway";
        public static final String TIMESTAMP_HEADER = "X-TH-OpenAPI-Timestamp";
        public static final long EXPIRE_TIME_SECONDS = 40L;
        public static final String AUTH_HEADER_PREFIX = "x-TH-ca-";
        public final static String STRING_TO_SIGN_DELIM = "\n";
    }

}
