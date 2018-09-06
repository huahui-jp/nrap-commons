package com.rayfay.bizcloud.core.commons.exception;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by shenfu on 2017/4/11.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Message {
    @AliasFor("value")
    String msg() default "";

    @AliasFor("msg")
    String value() default "";
}
