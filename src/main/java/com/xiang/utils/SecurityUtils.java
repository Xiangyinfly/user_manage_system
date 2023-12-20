package com.xiang.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
    public static Long getUserId() {
        return ((Long) getAuthentication().getPrincipal());
    }
}
