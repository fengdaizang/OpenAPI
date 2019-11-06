package com.fdzang.microservice.gateway.util;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author tanghu
 * @Date: 2019/11/6 16:06
 */
@ConfigurationProperties("auth.gateway")
public class WhiteUrl {
    private static List<String> white;

    public static List<String> getWhite() {
        return white;
    }

    public void setWhite(List<String> white) {
        this.white = white;
    }
}
