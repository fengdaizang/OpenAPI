package com.fdzang.microservice.common.util;

import com.chinawayltd.altair.common.util.digest.HMACSHA256Encoder;
import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;

@Slf4j
public class SignUtil {
	public static String generateSignature(String secretKey, String dataToSign) {
		String sign = null;
		try {
			sign = HMACSHA256Encoder.calculateRFC2104HMAC(secretKey, dataToSign);
		} catch (Exception ex) {
			log.error(MessageFormat.format("generate signature error! secretkey:{0},dataToSign:{1}", secretKey,
					dataToSign));
		} finally {
			return sign;
		}
	}
	
	public static void main(String[] sg) {
		System.out.println(generateSignature("sec", "sign"));
	}
}
