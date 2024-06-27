package com.sixplus.server.api.server;

import lombok.extern.slf4j.Slf4j;

import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Slf4j
public class UserIdInterceptor implements HandlerInterceptor {


    
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) {

        String userId = request.getHeader("user-id");
        UserIdHolder.setUserId(userId);

        return true;
    }

    @Override
    public void afterCompletion(@NonNull  HttpServletRequest request,
                                @NonNull  HttpServletResponse response,
                                @NonNull  Object handler,   @SuppressWarnings("null") Exception ex) {
        UserIdHolder.unset();
    }
}
