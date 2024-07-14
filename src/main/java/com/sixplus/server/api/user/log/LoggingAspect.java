package com.sixplus.server.api.user.log;

import com.sixplus.server.api.billing.controller.ApiResponse;
import com.sixplus.server.api.core.config.security.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Before;

public class LoggingAspect {
    private final UserActionLogRepository userActionLogRepository;

    public LoggingAspect(UserActionLogRepository userActionLogRepository) {
        this.userActionLogRepository = userActionLogRepository;
    }

    @Before("execution(* com.sixplus.server.api..*.*(..)) && @annotation(com.sixplus.server.api.log.annotation.Loggable)")
    public void logUserAction(JoinPoint joinPoint) {
        String action = joinPoint.getSignature().getName();
        String description = "Method " + action + " called";
        String userId = SecurityUtils.getCurrentUserId();
        UserActionLog userActionLog = new UserActionLog(action, description, userId);
        userActionLogRepository.save(userActionLog);
    }

    //    @AfterReturning(pointcut = "execution(* com.sixplus.server.api.user.controller.UserController.*(..))", returning = "result")
    @AfterReturning(pointcut = "execution(* com.sixplus.server.api..*.*(..)) && @annotation(com.sixplus.server.api.log.annotation.Loggable)", returning = "result")
    // public void logUserAction(JoinPoint joinPoint, ApiResponse result) {
    public void logUserAction(JoinPoint joinPoint, ApiResponse result) {
        String action = joinPoint.getSignature().getName();
        String description = "Method " + action + " finished with result: " + result;
        String userId = SecurityUtils.getCurrentUserId();
        UserActionLog userActionLog = new UserActionLog(action, description, userId);
        userActionLogRepository.save(userActionLog);
    }
}
