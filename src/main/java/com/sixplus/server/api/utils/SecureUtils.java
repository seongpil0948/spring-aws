package com.sixplus.server.api.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class SecureUtils {

	public static String hmacSha512(String data, String secret) {
		try {
			byte[] hash = secret.getBytes(StandardCharsets.UTF_8);
			Mac sha512Hmac = Mac.getInstance("HmacSHA512");
			SecretKeySpec secretKey = new SecretKeySpec(hash, "HmacSHA512");
			sha512Hmac.init(secretKey);

			byte[] signedBytes = sha512Hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));

			return Hex.encodeHexString(signedBytes);
		} catch (NoSuchAlgorithmException | InvalidKeyException ex) {
			ex.printStackTrace();
			return null;
		}
	}

}
