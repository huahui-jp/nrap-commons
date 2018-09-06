package com.rayfay.bizcloud.core.commons;

import com.rayfay.bizcloud.core.commons.clientinfo.EnableFeignClientsWithClientInfo;
import com.rayfay.bizcloud.core.commons.displayed.DisplayApplicationConfiguration;
import com.rayfay.bizcloud.core.commons.feign.EnableFeignClientsWithJWTOAuth2;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by stzhang on 2017/4/17.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.TYPE})
@Import(DisplayApplicationConfiguration.class)
@EnableFeignClientsWithJWTOAuth2
@EnableFeignClientsWithClientInfo
@EnableDiscoveryClient
public @interface EnablePltWithMicroService {

}
