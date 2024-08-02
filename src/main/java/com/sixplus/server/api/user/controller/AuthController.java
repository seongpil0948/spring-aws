package com.sixplus.server.api.user.controller;

import com.sixplus.server.api.core.config.security.JwtTokenProvider;
import com.sixplus.server.api.user.model.AuthRequestDTO;
import com.sixplus.server.api.user.model.JwtResponseDTO;
import com.sixplus.server.api.user.service.MemberService;
import com.sixplus.server.api.user.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final MemberService memberService;
    private AuthenticationManager authenticationManager;

    public AuthController(JwtTokenProvider jwtTokenProvider, UserService userService, MemberService memberService, AuthenticationManager authenticationManager) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.memberService = memberService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public JwtResponseDTO AuthenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getLoginId(), authRequestDTO.getLoginPwd()));
        if(authentication.isAuthenticated()){

            return JwtResponseDTO.builder()
                .accessToken(jwtTokenProvider.GenerateToken(authRequestDTO)).build();
        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/ping/admin")
    public String pingOnlyRoleAdmin() {
        try {
            return "Welcome";
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
