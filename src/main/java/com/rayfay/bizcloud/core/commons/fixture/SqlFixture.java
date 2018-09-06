package com.rayfay.bizcloud.core.commons.fixture;


import org.apache.commons.lang.StringUtils;

/**
 * Created by STZHANG on 2017/6/8.
 */
public interface SqlFixture extends Fixture{
    default boolean accept(String item){
       return StringUtils.isNotBlank(item) && item.endsWith(".sql");
    }

}
