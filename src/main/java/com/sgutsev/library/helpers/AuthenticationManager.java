package com.sgutsev.library.helpers;

import com.sgutsev.library.config.MyUserDetails;
import com.sgutsev.library.models.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationManager {
    public static boolean checkAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !authentication.getName().equals("anonymousUser") && authentication.isAuthenticated();
    }

    public static boolean checkAuthorityAdmin() {
        boolean authorityAdmin = false;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (checkAuthentication()) {
            MyUserDetails user = (MyUserDetails) authentication.getPrincipal();
            if (user.getUser().getUserRole().getNameRole().equals("ROLE_ADMIN")) {
                authorityAdmin = true;
            }
        }
        return authorityAdmin;
    }

    public static User getAuthenticationUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        return userDetails.getUser();
    }
}
