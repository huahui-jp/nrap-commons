package com.rayfay.bizcloud.core.commons.autoconfig;

import com.rayfay.bizcloud.core.commons.security.DefaultJwtAccessTokenConverterConfigurer;
import com.rayfay.bizcloud.core.commons.security.DefaultUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.oauth2.resource.JwtAccessTokenConverterConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by STZHANG on 2017/5/18.
 */
@Configuration
@ConditionalOnClass(UserDetailsService.class)
public class DefaultJwtResourceAutoConfiguration {

    private Logger logger = LoggerFactory.getLogger(DefaultJwtResourceAutoConfiguration.class);

    @Bean
    @ConditionalOnMissingBean({JwtAccessTokenConverterConfigurer.class})
    public JwtAccessTokenConverterConfigurer jwtAccessTokenConverterConfigurer() {
         return new DefaultJwtAccessTokenConverterConfigurer();
    }

    @Bean
    @ConditionalOnMissingBean({UserDetailsService.class})
    public UserDetailsService userDetailsService() {
       logger.info("not UserDetailService, using null as UserDetailsService");
       return new DefaultUserDetailsService();
    }
}
