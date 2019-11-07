package com.fdzang.microservice.gateway.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tanghu
 * @Date: 2019/11/7 9:55
 */
@Data
@NoArgsConstructor
public class GatewayResult {
    //网关错误码
    private Long code;
    // 网关错误描述
    private String msg;
    // 业务方错误码
    private Long sub_code;
    // 业务方错误描述
    private String sub_msg;
    //请求的唯一标识, 当前只是base64(ip+timestamp)
    private String req_id;
    // 业务方在没有error的情况下返回的数据
    private Object data;

}
