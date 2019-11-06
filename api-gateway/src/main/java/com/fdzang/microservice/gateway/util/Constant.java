package com.fdzang.microservice.gateway.util;

/**
 * @author tanghu
 * @Date: 2019/11/5 18:44
 */
public class Constant {

    public static final String REQUEST_TRACE_ID = "X-TH-Trace-Id";
    public static final String AUTH_HEADER = "Authorization";
    public static final String AUTH_LABLE = "gateway";
    public static final String TIMESTAMP_HEADER = "X-TH-OpenAPI-Timestamp";
    public static final long EXPIRE_TIME_SECONDS = 40L;
    public static final String AUTH_HEADER_PREFIX = "x-TH-ca-";
    public final static String STRING_TO_SIGN_DELIM = "\n";
    public final static String CONTENTE_MD5 = "Content-MD5";
    public static final String SERVICE_NAME = "service-name";


    public static final String ORG_CODE_HEADER = "X-TH-OpenAPI-OrgCode";
    public static final String ACCESSID_HEADER = "X-TH-OpenAPI-AccessId";
    public static final String DEV_ACCOUNT = "X-TH-OpenAPI-DevAccount";

    public class Order {
        public static final int SERIAL_NO_ORDER = -10;
        public static final int AUTH_ORDER = -9;
        public static final int MODIFY_REQUEST_ORDER = -8;
        public static final int MODIFY_RESPONSE_ORDER = -10;
    }

}
