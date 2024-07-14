package com.sixplus.server.api.core.config.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class JwtUserInfo {
    private String userId;
    private String email;
    private String memberPwd;
    private String memberType;	// U : 사용자(약사/의사) S : 공급사 A : 관리자 B: Batch

    public JwtUserInfo(Claims claims) throws Exception {
        ObjectMapper ob = new ObjectMapper();
        ob.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
        	JwtUserInfo userInfo = ob.convertValue(claims.get("userInfo"), new TypeReference<JwtUserInfo>() {});
            this.userId = userInfo.getUserId();
            this.email = userInfo.getEmail();
            this.memberPwd = userInfo.getMemberPwd();
            this.memberType = userInfo.getMemberType();
            
        } catch(Exception e) {
            throw new Exception("JwtUserInfo 파싱 오류");
        }
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.convertAuthorties(this.memberType);
    }

    private Collection<? extends GrantedAuthority> convertAuthorties(String role) {
        List<GrantedAuthority> authorities =  new ArrayList<>();
        String _role = "";

        switch (role) {
	        case "U":
	            _role = "USER";
	            break;
            case "A":
                _role = "ADMIN";
                break;
            case "S":
                _role = "SELLER";
                break;
            case "B":
	            _role = "BATCH";
	            break;
            default:
                break;
        }

        if (StringUtils.hasText(_role)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_"+_role));
        }

        return authorities;
    }
}
