package com.rayfay.bizcloud.core.commons.autoconfig;

import com.rayfay.bizcloud.core.commons.displayed.DisplayedApplication;
import com.rayfay.bizcloud.core.commons.event.AppStartedEvent;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

/**
 * Created by stzhang on 2017/4/17.
 */
@Configuration
@ConditionalOnBean(DisplayedApplication.class)
public class AppStartedAutoConfiguration
        implements ApplicationListener<ApplicationReadyEvent>, ApplicationEventPublisherAware {

    private Logger logger = LoggerFactory.getLogger(AppStartedAutoConfiguration.class);

    @Value("${spring.application.name}")
    private String appName;

    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationStartedEvent) {
        if (applicationStartedEvent.getApplicationContext().getParent() == null ||
                applicationStartedEvent.getApplicationContext().getParent().getParent() == null) {
            String payload = "default";
            if (StringUtils.isNotEmpty(this.appName)) {
                payload = this.appName;
            }
            this.applicationEventPublisher.publishEvent(new AppStartedEvent(payload, payload));
        }
    }
}
