package com.rayfay.bizcloud.core.commons.autoconfig;

import com.fasterxml.classmate.TypeResolver;
import com.rayfay.bizcloud.core.commons.config.ReloadedDocumentationPluginsBootstrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import springfox.documentation.spi.service.RequestHandlerProvider;
import springfox.documentation.spi.service.contexts.Defaults;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.spring.web.plugins.DocumentationPluginsBootstrapper;
import springfox.documentation.spring.web.plugins.DocumentationPluginsManager;
import springfox.documentation.spring.web.scanners.ApiDocumentationScanner;
import springfox.documentation.swagger2.configuration.Swagger2DocumentationConfiguration;

import javax.servlet.ServletContext;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by STZHANG on 2017/9/27.
 */
@Configuration
@ConditionalOnBean({DocumentationPluginsBootstrapper.class, DocumentationPluginsManager.class, RequestHandlerProvider.class
        , DocumentationCache.class, ApiDocumentationScanner.class, TypeResolver.class, Defaults.class, ServletContext.class})
@ConditionalOnClass({ReloadedDocumentationPluginsBootstrapper.class, DocumentationPluginsBootstrapper.class, Swagger2DocumentationConfiguration.class})
@AutoConfigureAfter({Swagger2DocumentationConfiguration.class})
public class SwaggerDocumentAutoConfiguration  implements BeanFactoryPostProcessor{
    private static final Logger log = LoggerFactory.getLogger(SwaggerDocumentAutoConfiguration.class);

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        log.info("start to reload DocumentationPluginsBootstrapper");
        Iterator<String> beanNames = beanFactory.getBeanNamesIterator();
        if(beanFactory instanceof DefaultListableBeanFactory){
            String targetBeanName = "documentationPluginsBootstrapper";
            log.info("start to reload class bytes of DocumentationPluginsBootstrapper");
//            String defaultBootstrapperBeanName = DocumentationPluginsBootstrapper.class.getSimpleName();
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(targetBeanName);
            if(beanDefinition instanceof ScannedGenericBeanDefinition){
                ScannedGenericBeanDefinition thisBeanDefinition = ((ScannedGenericBeanDefinition) beanDefinition);
                thisBeanDefinition.setBeanClass(ReloadedDocumentationPluginsBootstrapper.class);
                thisBeanDefinition.setBeanClassName(ReloadedDocumentationPluginsBootstrapper.class.getName());
                ((DefaultListableBeanFactory) beanFactory).registerBeanDefinition(targetBeanName, thisBeanDefinition);
            }
        }
        log.info("successfully reload class bytes of DocumentationPluginsBootstrapper");
//        while (beanNames.hasNext()){
//             String n = beanNames.next();
//             log.info("beanNames------- {}", n);
//        }
    }

//    @Autowired
//    @Bean
//    @ConditionalOnClass({ReloadedDocumentationPluginsBootstrapper.class})
//    @ConditionalOnMissingBean({ReloadedDocumentationPluginsBootstrapper.class})
//    public ReloadedDocumentationPluginsBootstrapper bootstrapper(DocumentationPluginsManager documentationPluginsManager, RequestHandlerProvider handlerProvider, DocumentationCache scanned
//            , ApiDocumentationScanner resourceListing, TypeResolver typeResolver, Defaults defaults, ServletContext servletContext) {
//        //Method content
//        return new ReloadedDocumentationPluginsBootstrapper(documentationPluginsManager, handlerProvider, scanned, resourceListing, typeResolver, defaults, servletContext);
//    }

}
