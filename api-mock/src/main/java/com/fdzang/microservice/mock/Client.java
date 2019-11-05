package com.fdzang.microservice.mock;


import com.fdzang.microservice.mock.util.HttpUtil;

/**
 * Client
 */
public class Client {
    /**
     * 发送请求
     *
     * @param request request对象
     * @return Response
     * @throws Exception
     */
    public static Response execute(Request request) throws Exception {
        switch (request.getMethod()) {
            case GET:
                return HttpUtil.httpGet(request.getHost(), request.getPath(),
                		request.getTimeout(), 
                		request.getHeaders(), 
                		request.getQueries(),
                		request.getAccessId(), request.getSecretKey());

			case POST_JSON:
				return HttpUtil.httpPost(request.getHost(), request.getPath(),
						request.getTimeout(),
						request.getHeaders(),
						request.getQueries(),
						request.getJsonStrBody(),
						request.getAccessId(), request.getSecretKey());

            case PUT_JSON:
                return HttpUtil.httpPut(request.getHost(), request.getPath(), 
                		request.getTimeout(), 
                		request.getHeaders(), 
                		request.getQueries(),
                		request.getJsonStrBody(),
                		request.getAccessId(), request.getSecretKey());
            case DELETE:
                return HttpUtil.httpDelete(request.getHost(), request.getPath(), 
                		request.getTimeout(), 
                		request.getHeaders(), 
                		request.getQueries(),
                		request.getAccessId(), request.getSecretKey());
            default:
                throw new IllegalArgumentException(String.format("unsupported method:%s", request.getMethod()));
        }
    }
}
