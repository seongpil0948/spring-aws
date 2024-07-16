package com.sixplus.server.api.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : 이성진
 * @systemName : abaus-common-web
 * @programName : IpUtil
 * @createDate : 2021. 9. 13.
 * @description :
 */
public class IpUtil {
	public static String getClientIp(HttpServletRequest req) {
		String ip = req.getHeader("X-Forwarded-For");
		if (ip == null)
			ip = req.getRemoteAddr();
		return ip;
	}
}
