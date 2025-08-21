package com.oracle.coffee.util;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtil {
    private SecurityUtil() {}

    //사원으로 로그인시 세션 들고옴  
    public static Integer currentEmpCode() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) return null;
        Object p = auth.getPrincipal();
        if (p instanceof com.oracle.coffee.dto.AccountDto dto) return dto.getEmp_code();
        return null;
    }
}
