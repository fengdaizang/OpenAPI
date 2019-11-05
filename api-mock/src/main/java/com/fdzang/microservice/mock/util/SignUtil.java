package com.fdzang.microservice.mock.util;

import com.fdzang.microservice.mock.constant.Constants;
import com.fdzang.microservice.mock.constant.HttpHeader;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 签名工具
 */
public class SignUtil {
    private static final Logger LOG = LoggerFactory
            .getLogger(SignUtil.class);
    /**
     * 计算签名
     *
     * @param secret APP密钥
     * @param method HttpMethod
     * @param path
     * @param headers
     * @param querys
     * @param bodys
     * @return 签名后的字符串
     */
    public static String sign(String secret, String method, String path, 
    							Map<String, String> headers, 
    							Map<String, String> querys, 
    							Map<String, String> bodys) {
        try {
            Mac hmacSha256 = Mac.getInstance(Constants.HMAC_SHA256);
            byte[] keyBytes = secret.getBytes(Constants.ENCODING);
            hmacSha256.init(new SecretKeySpec(keyBytes, 0, keyBytes.length, Constants.HMAC_SHA256));

            String stringToSign = buildStringToSign(method, path, headers, querys, bodys);

            LOG.info("StringToSign:\n{}",stringToSign);
            return new String(Base64.encodeBase64(
                    hmacSha256.doFinal(stringToSign.getBytes(Constants.ENCODING))),
                    Constants.ENCODING);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




    private static String buildStringToSign(String method, String path,
                                            Map<String, String> headers,
                                            Map<String, String> querys,
                                            Map<String, String> bodys) {
        StringBuilder sb = new StringBuilder();

        sb.append(method.toUpperCase()).append(Constants.LF);
        if (null != headers) {

            if (null != headers.get(HttpHeader.HTTP_HEADER_CONTENT_MD5)) {
                sb.append(headers.get(HttpHeader.HTTP_HEADER_CONTENT_MD5));
            }
            sb.append(Constants.LF);
            if (null != headers.get(HttpHeader.HTTP_HEADER_CONTENT_TYPE)) {
                sb.append(headers.get(HttpHeader.HTTP_HEADER_CONTENT_TYPE));
            }
            sb.append(Constants.LF);
            if (null != headers.get(HttpHeader.HTTP_HEADER_G7_TIMESTAMP)) {
                sb.append(headers.get(HttpHeader.HTTP_HEADER_G7_TIMESTAMP));
            }
        }

        sb.append(Constants.LF);
        sb.append(buildHeaders(headers));
        sb.append(buildResource(path, querys, bodys));

        return sb.toString();
    }
    /**
     * 构建待签名Path+Query+BODY
     *
     * @param path
     * @param querys
     * @param bodys
     * @return 待签名
     */
    private static String buildResource(String path, Map<String, String> querys, Map<String, String> bodys) {
    	StringBuilder sb = new StringBuilder();
    	
    	if (!StringUtils.isBlank(path)) {
    		sb.append(path);
        }
        Map<String, String> sortMap = new TreeMap<String, String>();
        if (null != querys) {
        	for (Map.Entry<String, String> query : querys.entrySet()) {
        		if (!StringUtils.isBlank(query.getKey())) {
        			sortMap.put(query.getKey(), query.getValue());
                }
        	}
        }
        
        if (null != bodys) {
        	for (Map.Entry<String, String> body : bodys.entrySet()) {
        		if (!StringUtils.isBlank(body.getKey())) {
        			sortMap.put(body.getKey(), body.getValue());
                }
        	}
        }
        
        StringBuilder sbParam = new StringBuilder();
        for (Map.Entry<String, String> item : sortMap.entrySet()) {
    		if (!StringUtils.isBlank(item.getKey())) {
    			if (0 < sbParam.length()) {
    				sbParam.append(Constants.SPE3_CONNECT);
    			}
    			sbParam.append(item.getKey());
    			if (!StringUtils.isBlank(item.getValue())) {
    				sbParam.append(Constants.SPE4_EQUAL).append(item.getValue());
    			}
            }
    	}
        if (0 < sbParam.length()) {
        	sb.append(Constants.SPE5_QUESTION);
        	sb.append(sbParam);
        }
        
        return sb.toString();
    }

    /**
     * 构建待签名Http头
     *
     * @param headers 请求中所有的Http头
     * @return 待签名Http头
     */
    private static String buildHeaders(Map<String, String> headers) {
        StringBuilder sb = new StringBuilder();

        if (null != headers) {

            if (null != headers) {
                Map<String, String> sortMap = new TreeMap<String, String>();
                //对于header头值，转成小写了之后再排序
                for (Map.Entry<String, String> header:headers.entrySet()){
                    sortMap.put(header.getKey().toLowerCase(),header.getValue());
                }


                for (Map.Entry<String, String> header : sortMap.entrySet()) {
                    if (isHeaderToSignByPrefix(header.getKey())) {
                        sb.append(header.getKey());
                        sb.append(Constants.SPE2_COLON);
                        if (!StringUtils.isBlank(header.getValue())) {
                            sb.append(header.getValue());
                        }
                        sb.append(Constants.LF);

                    }
                }

            }
        }

        return sb.toString();
    }

    /**
     * Http头是否参与签名 return
     */
    private static boolean isHeaderToSign(String headerName, List<String> signHeaderPrefixList) {
        if (StringUtils.isBlank(headerName)) {
            return false;
        }

        if (headerName.startsWith(HttpHeader.CA_HEADER_TO_SIGN_PREFIX_SYSTEM)) {
            return true;
        }

        if (null != signHeaderPrefixList) {
            for (String signHeaderPrefix : signHeaderPrefixList) {
                if (headerName.equalsIgnoreCase(signHeaderPrefix)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Http头是否参与签名 return
     */
    private static boolean isHeaderToSignByPrefix(String headerName) {
        if (StringUtils.isBlank(headerName)) {
            return false;
        }

        if (headerName.toLowerCase().startsWith(HttpHeader.CA_HEADER_TO_SIGN_PREFIX_SYSTEM.toLowerCase())) {
            return true;
        }

        return false;
    }
}
