package com.rayfay.bizcloud.core.commons.security;

import com.rayfay.bizcloud.core.commons.concurrence.Executor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.JwtAccessTokenConverterConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * Created by STZHANG on 2017/5/18.
 */
public class DefaultJwtAccessTokenConverterConfigurer implements JwtAccessTokenConverterConfigurer{

    private static Logger logger = LoggerFactory.getLogger(DefaultJwtAccessTokenConverterConfigurer.class);
    @Autowired(required = false)
    private UserDetailsService userDetailsService;

    @Override
    public void configure(JwtAccessTokenConverter jwtAccessTokenConverter) {
        DefaultAccessTokenConverter defaultAccessTokenConverter = new DefaultAccessTokenConverter();
        DefaultUaaUserAuthenticationConverter userAuthenticationConverter =  new DefaultUaaUserAuthenticationConverter();
        if(userDetailsService != null){
            logger.info("found UserDetailsService {}", userDetailsService.getClass().getName());
        }
        userAuthenticationConverter.setUserDetailsService(this.userDetailsService);
        defaultAccessTokenConverter.setUserTokenConverter(userAuthenticationConverter);
        jwtAccessTokenConverter.setAccessTokenConverter(defaultAccessTokenConverter);
    }

    public DefaultJwtAccessTokenConverterConfigurer(){
        super();
    }
}
