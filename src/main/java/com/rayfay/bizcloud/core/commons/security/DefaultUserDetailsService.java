package com.rayfay.bizcloud.core.commons.security;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by STZHANG on 2017/5/18.
 */
public class DefaultUserDetailsService implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(DefaultUserDetailsService.class);
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        DefaultUserDetails userDetails = new DefaultUserDetails();
        userDetails.setUserName(userName);
        userDetails.setAuthorities(findUserAuthorities(userName));
        return userDetails;
    }
    /**
     * @param userName
     * @return
     */
    protected Collection<? extends GrantedAuthority> findUserAuthorities(String userName){
        List<GrantedAuthority> authorityList = Lists.newArrayList();
        logger.debug("用户登陆：userName: {}, Authority: {} ", userName, authorityList);
        return authorityList;

    }
}
