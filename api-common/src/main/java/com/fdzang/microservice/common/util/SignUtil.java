package com.fdzang.microservice.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.text.MessageFormat;

@Slf4j
public class SignUtil {
	private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

	public static String generateSignature(String secretKey, String dataToSign) {
		String sign = null;
		try {
			SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes(), HMAC_SHA256_ALGORITHM);
			Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
			mac.init(signingKey);
			byte[] rawHmac = mac.doFinal(dataToSign.getBytes("UTF-8"));
			sign = Base64.encodeBase64String(rawHmac);
		} catch (Exception ex) {
			log.error(MessageFormat.format("generate signature error! secretkey:{},dataToSign:{}", secretKey,dataToSign));
		} finally {
			return sign;
		}
	}
}
