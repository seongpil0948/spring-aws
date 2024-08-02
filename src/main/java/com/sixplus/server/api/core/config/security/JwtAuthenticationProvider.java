package com.sixplus.server.api.core.config.security;

import com.sixplus.server.api.core.exception.ShopErrorException;
import com.sixplus.server.api.user.model.MemberVo;
import com.sixplus.server.api.user.model.UserEntity;
import com.sixplus.server.api.user.repository.UserRepository;
import com.sixplus.server.api.utils.CmmCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationProvider implements AuthenticationManager {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) authentication;

        String principal = (String)authenticationToken.getPrincipal();
        String password = (String) authenticationToken.getCredentials();

        UserEntity user = Optional.ofNullable(userRepository.findByUsername(principal))
                .orElseThrow(() -> new ShopErrorException(CmmCode.SHOP_LOGIN_1000.getCode()));
        MemberVo member = new MemberVo(user);
        if (!passwordEncoder.matches(password, member.getPassword())) {
            log.warn("사용자 비밀번호 불일치 확인============================= \n{}\n{}", user.getPassword(), password);
            throw new ShopErrorException(CmmCode.SHOP_LOGIN_1001.getCode());
        }

        return new UsernamePasswordAuthenticationToken(user, null, member.getAuthorities());
    }
}