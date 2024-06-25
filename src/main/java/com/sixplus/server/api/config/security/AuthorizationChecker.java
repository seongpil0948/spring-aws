package com.sixplus.server.api.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthorizationChecker {
	@Value("${spring.application.name}")
	private String applicationName;

	public boolean check(String authRwd, String urlPattern) {
		log.debug("########## url 접근 권한 체크 START ##########");
		log.debug("authRwd => {}, urlPattern => {}", authRwd, urlPattern);
		boolean access = true;
		log.debug("########## url 접근 권한 체크 END ##########");
		return access;
	}
}
