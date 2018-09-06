package com.rayfay.bizcloud.core.commons.feign;

public class FeignClientContext {
    private static ThreadLocal<String> authenticationJwtToken= new ThreadLocal<>();

    public static void put(String token){
        authenticationJwtToken.set(token);
    }

    public static String getToken(){
       return authenticationJwtToken.get();
    }

}
