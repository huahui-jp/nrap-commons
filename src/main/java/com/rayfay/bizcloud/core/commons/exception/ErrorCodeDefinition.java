package com.rayfay.bizcloud.core.commons.exception;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Created by stzhang on 2017/4/13.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
@Inherited
public @interface ErrorCodeDefinition {
    @AliasFor("thousands")
    int value() default 1;
    @AliasFor("value")
    int thousands() default 1;
}
