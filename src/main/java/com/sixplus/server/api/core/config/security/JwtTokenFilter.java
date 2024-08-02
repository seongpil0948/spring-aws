package com.sixplus.server.api.core.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtTokenFilter extends OncePerRequestFilter {
	private final JwtTokenProvider jwtTokenProvider;
	private final UserDetailsService userDetailsService;
	public JwtTokenFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
		this.jwtTokenProvider = jwtTokenProvider;
		this.userDetailsService = userDetailsService;
	}

//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//		String token = jwtTokenProvider.resolveToken(request);
//		String requestURI = request.getRequestURI();
//
//		if(StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
//			Authentication authentication = jwtTokenProvider.getAuthentication(token);
//			SecurityContextHolder.getContext().setAuthentication(authentication);
//			log.info("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
//		}
//		else {
//			log.info("유효한 JWT 토큰이 없습니다., uri: {}", requestURI);
//		}
//
//		filterChain.doFilter(request, response);
//	}


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		String authHeader = request.getHeader("Authorization");
		String token = null;
		String username = null;
		if(authHeader != null && authHeader.startsWith("Bearer ")){
			token = authHeader.substring(7);
			username = jwtTokenProvider.extractUsername(token);
		}

		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			if(jwtTokenProvider.validateToken(token, userDetails)){
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}

		}

		filterChain.doFilter(request, response);
	}
}