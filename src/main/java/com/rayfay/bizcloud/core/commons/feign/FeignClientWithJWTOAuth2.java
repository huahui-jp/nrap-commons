//package com.nrap.apps.commons.feign;
//
//import org.springframework.cloud.netflix.feign.FeignClient;
//import org.springframework.core.annotation.AliasFor;
//
//import java.lang.annotation.Documented;
//import java.lang.annotation.Retention;
//import java.lang.annotation.RetentionPolicy;
//import java.lang.annotation.Target;
//
///**
// * Created by stzhang on 2017/4/14.
// */
//@Target({java.lang.annotation.ElementType.TYPE})
//@Retention(RetentionPolicy.RUNTIME)
//@Documented
//@FeignClient("default")
//public @interface FeignClientWithJWTOAuth2 {
//
//    @AliasFor(annotation = FeignClient.class, attribute = "value")
//    String value() default "";
//
//    @AliasFor(annotation = FeignClient.class, attribute = "name")
//    String name() default "";
//
//    @AliasFor(annotation = FeignClient.class, attribute = "url")
//    String url() default "";
//
//    @AliasFor(annotation = FeignClient.class, attribute = "decode404")
//    boolean decode404() default false;
//
//    @AliasFor(annotation = FeignClient.class, attribute = "configuration")
//    Class<?>[] configuration() default {OAuthJwtFeignClientConfiguration.class};
//
//    @AliasFor(annotation = FeignClient.class, attribute = "fallback")
//    Class<?> fallback() default void.class;
//
//    @AliasFor(annotation = FeignClient.class, attribute = "path")
//    String path() default "";
//}
