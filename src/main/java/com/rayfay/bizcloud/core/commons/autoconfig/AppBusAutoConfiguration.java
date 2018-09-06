package com.rayfay.bizcloud.core.commons.autoconfig;

import com.rayfay.bizcloud.core.commons.displayed.DisplayedApplication;
import com.rayfay.bizcloud.core.commons.event.AppStartedEvent;
import com.rayfay.bizcloud.core.commons.message.AppBusClient;
import com.rayfay.bizcloud.core.commons.message.ConditionalOnSwaggerBusEnabled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;

/**
 * Created by stzhang on 2017/3/13.
 */
@Configuration
@ConditionalOnSwaggerBusEnabled
@ConditionalOnBean(DisplayedApplication.class)
@EnableBinding(AppBusClient.class)
public class AppBusAutoConfiguration {
    private Logger logger = LoggerFactory.getLogger(AppBusAutoConfiguration.class);
    @Autowired
    @Output(AppBusClient.OUTPUT)
    private MessageChannel appBusOutputChannel;

    @EventListener(classes = AppStartedEvent.class)
    public void acceptLocal(AppStartedEvent event) {
        if (event instanceof AppStartedEvent) {
            String appName = event.getAppName();
            String message = "startup:"+ appName;
            try {
                logger.info("send message: {}", message);
                this.appBusOutputChannel.send(MessageBuilder.withPayload(message).build());
                logger.info("sent _applicationStared message successful.");
            }catch (Exception e){
                logger.warn("sent _applicationStared message error: {}", e.getMessage());
            }
        }
    }




}
