package com.rayfay.bizcloud.core.commons.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by STZHANG on 2017/5/18.
 */
public class DefaultUaaUserAuthenticationConverter implements UserAuthenticationConverter {
    private final String MEMBEROF = "rfMemberOf";
    private final String USERCNAME = "cn";
    private Collection<GrantedAuthority> defaultAuthorities;
    private UserDetailsService userDetailsService;

    public DefaultUaaUserAuthenticationConverter() {
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public void setDefaultAuthorities(String[] defaultAuthorities) {
        this.defaultAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils.arrayToCommaDelimitedString(defaultAuthorities));
    }

    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
        Map<String, Object> response = new LinkedHashMap();
        response.put(USERNAME, authentication.getName());
        if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
            response.put(AUTHORITIES, AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
        }

        return response;
    }

    public Authentication extractAuthentication(Map<String, ?> map) {
        if (map.containsKey(USERNAME)) {
            Object principal = map.get(USERNAME);
            Collection<GrantedAuthority> authorities = this.getAuthorities(map);
            if (this.userDetailsService != null) {
                UserDetails user = this.userDetailsService.loadUserByUsername((String) map.get(USERNAME));
                if(user instanceof DefaultUserDetails){
                    DefaultUserDetails defaultUserDetails = (DefaultUserDetails) user;
                    defaultUserDetails.setUserChineseName((String)map.getOrDefault(USERCNAME, null));
                    defaultUserDetails.setDetailsAsMap(map);
                    //Default Authorities
                    if(defaultUserDetails.getAuthorities() == null){
                        defaultUserDetails.setAuthorities(authorities);
                    }else if(authorities != null){
                        Collection<? extends GrantedAuthority> ua = defaultUserDetails.getAuthorities();
                        authorities.addAll(ua);
                        defaultUserDetails.setAuthorities(authorities);
                    }
                }
                principal = user;
            }
            return new UsernamePasswordAuthenticationToken(principal, "N/A", authorities);
        } else {
            return null;
        }
    }

    private Collection<GrantedAuthority> getAuthorities(Map<String, ?> map) {
        Object authorities = map.get(AUTHORITIES); //先取得AUTHORITIES

        if(authorities == null){
            authorities = map.get(MEMBEROF); //再取得MEMBREOF
        }
        if(authorities == null){
            return this.defaultAuthorities;
        }
        if (authorities instanceof String) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList((String) authorities);
        } else if (authorities instanceof Collection) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils.collectionToCommaDelimitedString((Collection) authorities));
        } else {
            throw new IllegalArgumentException("Authorities must be either a String or a Collection");
        }


    }

}
