package com.fdzang.microservice.mock;

import com.fdzang.microservice.mock.constant.Constants;
import com.fdzang.microservice.mock.constant.ContentType;
import com.fdzang.microservice.mock.constant.HttpHeader;
import com.fdzang.microservice.mock.constant.HttpSchema;
import com.fdzang.microservice.mock.enums.Method;
import com.fdzang.microservice.mock.util.MessageDigestUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 调用示例
 * 请替换APP_KEY,SECRET_KEY,BaseURL,CUSTOM_HEADERS_TO_SIGN_PREFIX为真实配置
 */
@Slf4j
public class Demo {
    //APP KEY
    private final static String ACCESS_ID = "omwcs8";
    // APP密钥
    private final static String SECRET_KEY = "WkjU9az2vDb6i9twx63EuWGIlnKS9q69";
    //API域名
    private final static String BaseURL = "127.0.0.1:7000";

    @Test
    public void testGet() throws Exception {
        //请求path
        String path = "/v1/base/truck/history_location";

        Map<String, String> headers = new HashMap<String, String>();
        headers.put(HttpHeader.HTTP_HEADER_G7_TIMESTAMP, "" + System.currentTimeMillis());


        Request request = new Request(Method.GET, HttpSchema.HTTP + BaseURL, path, ACCESS_ID, SECRET_KEY, Constants.DEFAULT_TIMEOUT);
        request.setHeaders(headers);

        for (Map.Entry<String,String> entry:headers.entrySet()) {
            System.out.println("header:key: "+entry.getKey()+"header:values: "+entry.getValue());
        }

        //请求的query
        Map<String, String> querys = new HashMap<>();
        querys.put("plate_num", "部A11110");

        querys.put("from", "2017-11-25 00:00:00");
        querys.put("to", "2017-11-30 00:00:00");
        request.setQueries(querys);

        //调用服务端
        Response response = Client.execute(request);

        log.info(response.getBody());
    }

    /**
     * HTTP POST 字符串
     *
     * @throws Exception
     */
    @Test
    public void testPostJson() throws Exception {
        //请求path
        String path = "/v1/factory/gsp/gps_status";
        //Body内容
        String body = "\n" +
                "{\"code\":\"\",\"data\":[{\"plateNum\": \"苏 D73805\",\"course\": 0,\"imei\": \"106014714244541\",\"lat\": 37.711923,\"lng\": 117.193633,\"speed\": 0,\"vin\": \"LVCB4L4D4GM003741\",\"time\": 1508999113000,\"acc\" :0,\"gps\": 1},{\"plateNum\": \"苏 D73805\",\"course\": 0,\"imei\": \"106014714244541\",\"lat\": 37.711923,\"lng\": 117.193633,\"speed\": 0,\"vin\": \"LVCB4L4D4GM003741\",\"time\": 1508999113000,\"acc\" :0,\"gps\": 1}],\"message\":\"\",\"pushTime\":1550727788048}";
        System.out.println(body);

        Map<String, String> headers = new HashMap<String, String>();

        headers.put(HttpHeader.HTTP_HEADER_CONTENT_MD5, MessageDigestUtil.base64AndMD5(body));
        headers.put(HttpHeader.HTTP_HEADER_CONTENT_TYPE, ContentType.CONTENT_TYPE_JSON);
        headers.put(HttpHeader.HTTP_HEADER_G7_TIMESTAMP, "" + System.currentTimeMillis());


        Request request = new Request(Method.POST_JSON, HttpSchema.HTTP + BaseURL, path, ACCESS_ID, SECRET_KEY, Constants.DEFAULT_TIMEOUT);
        request.setHeaders(headers);

        request.setJsonStrBody(body);

        //调用服务端
        Response response = Client.execute(request);

        log.info(response.getBody());
    }


    /**
     * HTTP DELETE
     *
     * @throws Exception
     */
    @Test
    public void testDelete() throws Exception {
        //请求path
        String path = "/v1/base/current/full_currents";

        String body = "{\n" +
                "  \"carnum\": \"川A12345\",\n" +
                "  \"longitude\": \"104.07134\",\n" +
                "  \"latitude\": \"30.54013\"\n" +
                "}";

        Map<String, String> headers = new HashMap<String, String>();
        //（POST/PUT请求必选）请求Body内容格式
        headers.put(HttpHeader.HTTP_HEADER_CONTENT_TYPE, ContentType.CONTENT_TYPE_JSON);
        headers.put("d-header1", "header1Value");
        headers.put("X-G7-Ca-a-header1", "header1Value");
        headers.put("X-G7-Ca-b-header2", "header2Value");
        headers.put(HttpHeader.HTTP_HEADER_G7_TIMESTAMP, "" + System.currentTimeMillis());
        //（必填）根据期望的Response内容类型设置
        headers.put(HttpHeader.HTTP_HEADER_ACCEPT, "application/json");
        headers.put(HttpHeader.HTTP_HEADER_G7_TIMESTAMP, "" + System.currentTimeMillis());
        Request request = new Request(Method.DELETE, HttpSchema.HTTP + BaseURL, path, ACCESS_ID, SECRET_KEY, Constants.DEFAULT_TIMEOUT);
        request.setHeaders(headers);

        //请求的query
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("a-query1", "query1Value");
        querys.put("b-query2", "query2Value");
        request.setQueries(querys);
        request.setJsonStrBody(body);

        //调用服务端
        Response response = Client.execute(request);

        log.info(response.getBody());
    }


}
