package com.rayfay.bizcloud.core.commons.message;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by stzhang on 2017/3/13.
 */
@ConditionalOnProperty(value = ConditionalOnSwaggerBusEnabled.SPRING_SWAGGER_BUS_ENABLED, matchIfMissing = false)
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public  @interface ConditionalOnSwaggerBusEnabled {
    public static String SPRING_SWAGGER_BUS_ENABLED = "spring.swagger.bus.enabled";
}

