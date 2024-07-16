package com.sixplus.server.api.core.constant;

/**
 *
 * @programName : ComConstant
 * @author : cwcho
 * @description : 공통 상수
 */
public class ComConstant {
	/**
	 * 페이징 처리시, 페이지 요청이 빈값일 경우 기본값
	 */
	public final static int DEFAULT_PAGE = 1;
	/*
	 * 페이징 처리시, 페이지당 조회수량이 빈값일 경우 기본값
	 */
	public final static int DEFAULT_LIMIT = 10;
	/**
	 * YN 문자열 Y
	 */
	public final static String YN_Y = "Y";

	/**
	 * YN 문자열 N
	 */
	public final static String YN_N = "N";
	/**
	 * 사용자 세션 정보
	 */
	public final static String USER_INFO = "loginUserInfo";
	/**
	 * API 메소드 코드 정보
	 */
	public final static String GET_METHOD = "GET";
	public final static String POST_METHOD = "POST";
	public final static String PUT_METHOD = "PUT";
	public final static String DEL_METHOD = "DELETE";

	/**
	 * 사용여부 정보
	 */
	public final static String USE_YN_DEF = "Y";
}
