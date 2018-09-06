package com.rayfay.bizcloud.core.commons.security;

import com.rayfay.bizcloud.core.commons.exception.ErrorCodeTypes;
import com.rayfay.bizcloud.core.commons.exception.NRAPException;
import org.assertj.core.util.Lists;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import java.util.Collection;
import java.util.List;

/**
 * Created by STZHANG on 2017/6/26.
 */
public class SecurityContextUtils {
    public final static String[] superAdminRoles = {"super_admin"};

    public static DefaultUserDetails getUserDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.getPrincipal() != null) {
            Object principal = authentication.getPrincipal();
            if(principal instanceof DefaultUserDetails){
                DefaultUserDetails userDetails = (DefaultUserDetails) principal;
                return userDetails;
            }
        }
        return null;
    }


    public static String getLoginUserName(){
        DefaultUserDetails userDetails =  getUserDetails();
        if(userDetails != null){
            return userDetails.getUsername();
        }
        return null;
    }

    public static List<String> getLoginUserAuthorities(){
        List<String> res = Lists.newArrayList();
        DefaultUserDetails userDetails =  getUserDetails();
        if(userDetails != null){
            Collection<? extends GrantedAuthority> grantedAuthorities = userDetails.getAuthorities();
            if(grantedAuthorities != null){
                for (GrantedAuthority grantedAuthority : grantedAuthorities) {
                      res.add(grantedAuthority.getAuthority());
                }
            }
        }
        return res;
    }

    public static boolean isSuperAdmin(){
        List<String> authorities =  getLoginUserAuthorities();
        for (String superAdminRole : superAdminRoles) {
             if(authorities.contains(superAdminRole)){
                 return true;
             };
        }
        return false;

    }

    public static String getToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.getDetails() != null) {
            return ((OAuth2AuthenticationDetails) authentication.getDetails()).getTokenValue();
        }

        return null;
    }
}
