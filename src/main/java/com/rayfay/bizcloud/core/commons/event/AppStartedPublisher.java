//package com.nrap.apps.commons.event;
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.ApplicationEventPublisher;
//import org.springframework.context.ApplicationEventPublisherAware;
//import org.springframework.context.ApplicationListener;
//import org.springframework.stereotype.Component;
//
//
///**
// * Created by stzhang on 2017/3/13.
// */
//@Component
//public class AppStartedPublisher implements ApplicationListener<ApplicationReadyEvent> , ApplicationEventPublisherAware {
//
//    private Logger logger = LoggerFactory.getLogger(AppStartedPublisher.class);
//
//    @Value("${spring.application.name}")
//    private String appName;
//
//    private  ApplicationEventPublisher applicationEventPublisher ;
//
//    @Override
//    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
//        this.applicationEventPublisher = applicationEventPublisher;
//    }
//
//    @Override
//    public void onApplicationEvent(ApplicationReadyEvent applicationStartedEvent) {
//         String payload = "default";
//         if(StringUtils.isNotEmpty(this.appName)){
//              payload = this.appName;
//         }
//         this.applicationEventPublisher.publishEvent(new AppStartedEvent(payload, payload));
//    }
//}
