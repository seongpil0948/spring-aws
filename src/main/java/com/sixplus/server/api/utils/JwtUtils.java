package com.sixplus.server.api.utils;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JwtUtils
 * Json Web Token Utility
 *
 * @author : belio79
 */
@Component
public class JwtUtils {
	@Value("${jwt.secret-key:defaultSecretKey}")
	private String SECRET_KEY;
	@Value("${jwt.algorithm:defaultAGRM}")
	private String ALGORITHM;
	@Value("${jwt.expired-time:3600000}")
	private long EXPIRED_TIME;

	private final String TOKEN_TYPE = "tokenType";

	/**
	 * buildAccessJwt
	 * Json Web Token 생성
	 *
	 * @author belio79
	 * @param tokenType 토큰 유형
	 * @param expiredTime 만료시간(밀리 초)
	 * @return String 토큰 값
	 */
	public String buildAccessJwt(String tokenType, long expiredTime) {
		JwtBuilder jwtBuilder = Jwts.builder();
		Map<String, Object> paramMap = new HashMap();
		paramMap.put(TOKEN_TYPE, tokenType);

		jwtBuilder.setHeaderParam("alg", ALGORITHM);
		jwtBuilder.setClaims(paramMap);
		jwtBuilder.setExpiration(new Date(new Date().getTime() + expiredTime)); // 만료일
		jwtBuilder.signWith(SignatureAlgorithm.forName(ALGORITHM), SECRET_KEY.getBytes());

		return jwtBuilder.compact();
	}


	/**
	 * buildAccessJwt
	 * Json Web Token 생성
	 *
	 * @author belio79
	 * @param tokenType 토큰 유형
	 * @return String 토큰 값
	 */
	public String buildAccessJwt(String tokenType) {
		return buildAccessJwt(tokenType, EXPIRED_TIME);
	}

	/**
	 * isValidJwt
	 * Json Web Token 유효성 검사
	 *
	 * @author belio79
	 * @param token 토큰 값
	 * @return String 유효성 검사 결과 : VALID(유효), EXPIRED(만료), TAMPER(위변조), ERROR(오류)
	 */
	public String isValidJwt(String token) {
		try {
			Jwts.parser().setSigningKey(SECRET_KEY.getBytes()).parseClaimsJws(token);
			return "VALID";   // 유효
		} catch (ExpiredJwtException exception) {
			return "EXPIRED";   // 만료
		} catch (JwtException exception) {
			return "TAMPER";   // 위변조
		} catch (Exception e) {
			return "ERROR";   // 그 외 오류
		}
	}


	/**
	 * getTokenType
	 * Json Web Token 유형 가져오기
	 *
	 * @author belio79
	 * @param token 토큰 값
	 * @return String 유효성 유형
	 */
	public String getTokenType(String token) {
		String tokenType = (String) Jwts.parser().setSigningKey(SECRET_KEY.getBytes()).parseClaimsJws(token).getBody().get(TOKEN_TYPE);
		return tokenType;
	}
}
