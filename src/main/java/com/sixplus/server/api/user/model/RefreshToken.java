package com.sixplus.server.api.user.model;

import com.sixplus.server.api.core.annotation.ShopRequired;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshToken {
    @ShopRequired(field="로그인 아이디")
    private String loginId;
    private String userType;
}
