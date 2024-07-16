package com.sixplus.server.api.utils;

import org.springframework.http.server.PathContainer;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class StringUtils extends org.apache.commons.lang3.StringUtils {

	public static String encodeURIComponent(String s) {
		String result;
		try {
			result = URLEncoder.encode(s, "UTF-8")
					.replaceAll("\\+", "%20")
					.replaceAll("%21", "!")
					.replaceAll("%27", "'")
					.replaceAll("%28", "(")
					.replaceAll("%29", ")")
					.replaceAll("%7E", "~");
		} catch (UnsupportedEncodingException e) {
			result = s;
		}
		return result;
	}

	public static String getRuleFileKeyName(URI uri) {
		String keyName = "";
		String temp = uri.toString().substring(uri.toString().lastIndexOf("/"));
		String replace1 = uri.toString().replaceFirst(temp, "." + temp.substring(1));
		keyName = replace1.substring(replace1.lastIndexOf("/") + 1);
		return keyName;
	}

	public static String getGUID32() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static String getRandomStr(int nSize) {
		StringBuffer temp = new StringBuffer();
		SecureRandom rnd = new SecureRandom();
		for (int i = 0; i < nSize; i++) {
			int rIndex = rnd.nextInt(3);
			switch (rIndex) {
				case 0:
					// a-z
					temp.append((char) ((rnd.nextInt(26)) + 97));
					break;
				case 1:
					// A-Z
					temp.append((char) ((rnd.nextInt(26)) + 65));
					break;
				case 2:
					// 0-9
					temp.append((rnd.nextInt(10)));
					break;
			}
		}
		return temp.toString();
	}

	public static String ifNullToEmpty(String obj) {
		String result = "";
		if (obj != null) {
			return obj;
		}
		return result;
	}

	public static String ifNullToEmpty(Object obj) {
		String result = "";
		if (obj != null) {
			result = String.valueOf(obj);
			if ("null".equals(result)) {
				result = "";
			}
			return result;
		}
		return result;
	}

	public static String objectIfNullToEmpty(Object obj) {
		String result = "";
		if (obj != null) {
			result = String.valueOf(obj);
			if ("null".equals(result)) {
				result = "";
			}
			return result;
		}
		return result;
	}

	public static String formEncode(Map<String, Object> m) {
		String s = "";
		for (String key : m.keySet()) {
			if (s.length() > 0) {
				s += "&";
			}
			s += key + "=" + m.get(key);
		}
		return s;
	}

	public static boolean uriPatternMatches(String pattern, String input) {
		PathPattern pp = uriPathParse(pattern);
		return pp.matches(PathContainer.parsePath(input));
	}

	private static PathPattern uriPathParse(String path) {
		PathPatternParser pp = new PathPatternParser();
		pp.setMatchOptionalTrailingSeparator(true);
		return pp.parse(path);
	}

	public static String generateUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}


	public static String getCurrentTimeStamp() {
		return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
	}

	public static String getCurrentTimeSec() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}

	public static String getSeqId() {
		String seqId = StringUtils.getCurrentTimeStamp();
		String randomNumber = StringUtils.numberGen(4, 2) + StringUtils.numberGen(4, 2);
		seqId = seqId + randomNumber;
		return seqId;
	}

	/**
	 * 전달된 파라미터에 맞게 난수를 생성한다
	 *
	 * @param len   : 생성할 난수의 길이
	 * @param dupCd : 중복 허용 여부 (1: 중복허용, 2:중복제거)
	 */
	public static String numberGen(int len, int dupCd) {
		Random rand = new Random();
		String numStr = ""; //난수가 저장될 변수

		for (int i = 0; i < len; i++) {

			//0~9 까지 난수 생성
			String ran = Integer.toString(rand.nextInt(10));

			if (dupCd == 1) {
				//중복 허용시 numStr에 append
				numStr += ran;
			} else if (dupCd == 2) {
				//중복을 허용하지 않을시 중복된 값이 있는지 검사한다
				if (!numStr.contains(ran)) {
					//중복된 값이 없으면 numStr에 append
					numStr += ran;
				} else {
					//생성된 난수가 중복되면 루틴을 다시 실행한다
					i -= 1;
				}
			}
		}
		return numStr;
	}

}
