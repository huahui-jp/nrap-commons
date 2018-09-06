package com.rayfay.bizcloud.core.commons.displayed;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by stzhang on 2017/4/17.
 */
@Configuration
public class DisplayApplicationConfiguration {

    @Value("${spring.application.name}")
    private String appName;

    @Bean
    @ConditionalOnMissingBean(DisplayedApplication.class)
    public DisplayedApplication getDisplayedApplication(){
        DisplayedApplication app = new DisplayedApplication();
        app.setRunningApplicationName(appName);
        return app;
    }

}
