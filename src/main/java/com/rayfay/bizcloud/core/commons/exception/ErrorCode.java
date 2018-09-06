package com.rayfay.bizcloud.core.commons.exception;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by stzhang on 2017/4/13.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ErrorCode {
    @AliasFor("value")
    int code() default 1;
    @AliasFor("code")
    int value() default 1;
    String msg();
}
