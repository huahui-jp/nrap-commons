package com.rayfay.bizcloud.core.commons.feign;

import org.springframework.cloud.netflix.feign.EnableFeignClients;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by stzhang on 2017/4/14.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.TYPE})
@EnableFeignClients(defaultConfiguration = OAuthJwtFeignClientConfiguration.class)
public @interface EnableFeignClientsWithJWTOAuth2 {
}
