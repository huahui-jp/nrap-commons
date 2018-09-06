package com.rayfay.bizcloud.core.commons.feign;
import feign.codec.Encoder;
import feign.RequestInterceptor;
import feign.form.spring.SpringFormEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.cloud.netflix.feign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * Created by stzhang on 2017/4/14.
 */
@Configuration
public class OAuthJwtFeignClientConfiguration {
    private Logger logger = LoggerFactory.getLogger(OAuthJwtFeignClientConfiguration.class);
    @Bean
    public RequestInterceptor requestTokenBearerInterceptor(OAuth2ClientContext oauth2ClientContext) {
        if(oauth2ClientContext == null){
            throw new OAuth2Exception("未启用OAuth2 认证，请检查系统配置");
        }
        return new OAuth2JwtFeignRequestInterceptor(oauth2ClientContext);
    }

    @Bean
    @Primary
    @Scope("prototype")
    public Encoder multipartFormEncoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        logger.info("Found SpringFormEncoder, using it. messageConverters:{}", messageConverters);
        SpringEncoder defaultSpringEncoder = new SpringEncoder(messageConverters);
        return new SpringFormEncoder(defaultSpringEncoder);
    }


}
