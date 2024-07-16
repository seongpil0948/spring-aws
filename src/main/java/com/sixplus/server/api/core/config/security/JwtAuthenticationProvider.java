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

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationProvider implements AuthenticationManager {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) authentication;

        MemberVo userInfo = (MemberVo)authenticationToken.getPrincipal();
        String password = (String) authenticationToken.getCredentials();

        UserEntity user = userRepository.findById(userInfo.getUid())
                .orElseThrow(() -> new ShopErrorException(CmmCode.SHOP_LOGIN_1000.getCode()));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            log.warn("사용자 비밀번호 불일치 확인============================= \n{}\n{}", user.getPassword(), password);
            throw new ShopErrorException(CmmCode.SHOP_LOGIN_1001.getCode());
        }

        return new UsernamePasswordAuthenticationToken(user, null, userInfo.getAuthorities());
    }
}