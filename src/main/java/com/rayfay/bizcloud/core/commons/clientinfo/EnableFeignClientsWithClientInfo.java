package com.rayfay.bizcloud.core.commons.clientinfo;

import org.springframework.cloud.netflix.feign.EnableFeignClients;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by shenfu on 2017/7/13.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.TYPE})
@EnableFeignClients(defaultConfiguration = ClientInfoFeignClientConfiguration.class)
public @interface EnableFeignClientsWithClientInfo {
}
