package com.rayfay.bizcloud.core.commons.security;

import com.google.common.collect.Maps;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

/**
 * Created by STZHANG on 2017/5/18.
 */
public class DefaultUserDetails implements UserDetails {
    private String userChineseName;
    private String userName;
    private String randomPassword =  UUID.randomUUID().toString();
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, ?> userDetailsAsMap = Maps.newHashMap();

    public String getUserChineseName() {
        return userChineseName;
    }

    public Map<String, ?> getUserDetailsAsMap() {
        return userDetailsAsMap;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return randomPassword;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public void setUserChineseName(String userChineseName) {
        this.userChineseName = userChineseName;
    }

    public void setDetailsAsMap(Map<String, ?> userInfoAsMap) {
        this.userDetailsAsMap = userInfoAsMap;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
