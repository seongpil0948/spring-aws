package com.sixplus.server.api.user.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sixplus.server.api.core.serializer.BCryptDeSerializer;
import io.jsonwebtoken.Claims;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;


@Getter
@Setter
public class MemberVo implements UserDetails {

    /** passwd (비밀번호) */
    @JsonDeserialize(using = BCryptDeSerializer.class)
    private String pw;
    private String uid;
    private String name;
    private String email;
    private String roles;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(getRoles().split(","))
                .map(SimpleGrantedAuthority::fromValue)
                .collect(Collectors.toList());
    }
    public MemberVo() {
    }

    public MemberVo(Claims claims) throws Exception {
        ObjectMapper ob = new ObjectMapper();
        ob.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            MemberVo userInfo = ob.convertValue(claims.get("userInfo"), new TypeReference<MemberVo>() {});
            this.uid = userInfo.getUid();
            this.email = userInfo.getEmail();
            this.name = userInfo.getName();
            this.pw = userInfo.getPw();

        } catch(Exception e) {
            throw new Exception("MemberVo 파싱 오류");
        }
    }

    @Override
    public String getPassword() {
        return getPw();
    }

    @Override
    public String getUsername() {
        return getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}