package com.sixplus.server.api.user.model;

import com.sixplus.server.api.core.annotation.ShopRequired;
import lombok.*;

@ToString
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginVO {
    @ShopRequired(field="로그인 아이디")
    private String loginId;
    @ShopRequired(field="로그인 패스워드")
    private String loginPwd;
    private String roles;
    private String loginIp;
    private String loginStatus;
    private String loginDomainId = "www";
    private String loginType;
}
