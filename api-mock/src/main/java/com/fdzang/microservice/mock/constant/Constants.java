package com.fdzang.microservice.mock.constant;

/**
 * 通用常量
 */
public class Constants {
    //签名算法HmacSha256
    public static final String HMAC_SHA256 = "HmacSHA256";
    //编码UTF-8
    public static final String ENCODING = "UTF-8";
    //UserAgent
    public static final String USER_AGENT = "altair.TH.java";

    public static final String AUTH_PREFIX = "gateway";
    //换行符
    public static final String LF = "\n";
    //串联符
    public static final String SPE1_COMMA = ",";
    //示意符
    public static final String SPE2_COLON = ":";
    //连接符
    public static final String SPE3_CONNECT = "&";
    //赋值符
    public static final String SPE4_EQUAL = "=";
    //问号符
    public static final String SPE5_QUESTION = "?";

    //默认请求超时时间,单位毫秒
    public static final int DEFAULT_TIMEOUT = 5000;

}
