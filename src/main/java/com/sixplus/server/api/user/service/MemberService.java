package com.sixplus.server.api.user.service;


import com.sixplus.server.api.model.Response;
import com.sixplus.server.api.user.model.LoginVO;
import com.sixplus.server.api.user.model.MemberVo;

/**
* <pre>
* 1. 패키지명 : com.dw.ids.sonic.member.service
* 2. 타입명 : MemberService.java
* 3. 작성일 : 2023. 03. 22
* 4. 작성자 : Mcircle
* 5. 설명   : mariadb.MEMBER - Service Interface 
* Copyright ids.trust corp. all right reserved
* </pre>
**/
public interface MemberService {
	
	/** 사용자 로그인 **/
	Response<?> getUserInfoByUserId(LoginVO vo);


	/** 더샵 사용자 Retoken **/
	public MemberVo regenerateJwtUserInfo(LoginVO vo, MemberVo jwtUserInfo);

}
