package com.sixplus.server.api.user.model;


import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public enum SimpleGrantedAuthority implements GrantedAuthority {

    ROLE_ADMIN(10), ROLE_USER(0), ROLE_ANONYMOUS(-10);

    private static final Map<Integer, SimpleGrantedAuthority> valueMap = Arrays.stream(SimpleGrantedAuthority.values())
            .collect(Collectors.toMap(SimpleGrantedAuthority::getValue, Function.identity()));

    private Integer value;

    SimpleGrantedAuthority(Integer value) {
        this.value = value;
    }

    public static SimpleGrantedAuthority fromValue(Integer value) {
        if (value == null)
            throw new IllegalArgumentException("value is null");
        return valueMap.get(value);
    }
    public static SimpleGrantedAuthority fromValue(String value) {
        if (value == null)
            throw new IllegalArgumentException("value is null");
        return valueMap.get(Integer.parseInt(value));
    }

    @Override
    public String getAuthority() {
        return this.name();
    }

}
