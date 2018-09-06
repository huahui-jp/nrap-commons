//package com.rayfay.bizcloud.core.commons.config;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.config.BeanDefinition;
//import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
//import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
//import org.springframework.beans.factory.support.DefaultListableBeanFactory;
//import org.springframework.stereotype.Component;
//import springfox.documentation.spring.web.plugins.DocumentationPluginsBootstrapper;
//
//import java.util.Iterator;
//
///**
// * Created by STZHANG on 2017/9/27.
// */
//@Component
//public class ReloadDocumentBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
//    private static final Logger log = LoggerFactory.getLogger(ReloadDocumentBeanFactoryPostProcessor.class);
//    @Override
//    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
//        log.info("start to reload DocumentationPluginsBootstrapper");
//        Iterator<String> beanNames = beanFactory.getBeanNamesIterator();
//        if(beanFactory instanceof DefaultListableBeanFactory){
//            log.info("start to remove DocumentationPluginsBootstrapper");
//            ((DefaultListableBeanFactory) beanFactory).removeBeanDefinition("documentationPluginsBootstrapper");
//        }
//        log.info("start to list all beanNames");
////        while (beanNames.hasNext()){
////             String n = beanNames.next();
////             log.info("beanNames------- {}", n);
////        }
//    }
//}
