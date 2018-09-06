//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.rayfay.bizcloud.core.commons.config;

import com.fasterxml.classmate.TypeResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.DocumentationPlugin;
import springfox.documentation.spi.service.RequestHandlerProvider;
import springfox.documentation.spi.service.contexts.Defaults;
import springfox.documentation.spi.service.contexts.DocumentationContext;
import springfox.documentation.spi.service.contexts.DocumentationContextBuilder;
import springfox.documentation.spi.service.contexts.Orderings;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.spring.web.plugins.DefaultConfiguration;
import springfox.documentation.spring.web.plugins.DocumentationPluginsManager;
import springfox.documentation.spring.web.scanners.ApiDocumentationScanner;

import javax.servlet.ServletContext;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ReloadedDocumentationPluginsBootstrapper implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger log = LoggerFactory.getLogger(ReloadedDocumentationPluginsBootstrapper.class);
    private final DocumentationPluginsManager documentationPluginsManager;
    private final RequestHandlerProvider handlerProvider;
    private final DocumentationCache scanned;
    private final ApiDocumentationScanner resourceListing;
    private final DefaultConfiguration defaultConfiguration;
    private AtomicBoolean initialized = new AtomicBoolean(false);




    @Autowired
    public ReloadedDocumentationPluginsBootstrapper(DocumentationPluginsManager documentationPluginsManager, RequestHandlerProvider handlerProvider, DocumentationCache scanned
            , ApiDocumentationScanner resourceListing, TypeResolver typeResolver, Defaults defaults, ServletContext servletContext) {
        //Method content
        this.documentationPluginsManager = documentationPluginsManager;
        this.handlerProvider = handlerProvider;
        this.scanned = scanned;
        this.resourceListing = resourceListing;
        this.defaultConfiguration = new DefaultConfiguration(defaults, typeResolver, servletContext);
    }

    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.info("Context refreshed------0");
        Object source = contextRefreshedEvent.getSource();
        if(source instanceof AnnotationConfigApplicationContext){
            String sourceId = ((AnnotationConfigApplicationContext) source).getId();
            String applicationContextIdPrefix = AnnotationConfigApplicationContext.class.getName();
            //----
            if(!sourceId.isEmpty() && sourceId.startsWith(applicationContextIdPrefix)){
                return;
            }
            //----
        }
        //initialize
        if(this.initialized.compareAndSet(false, true)) {
            log.info("Context refreshed");
            List<DocumentationPlugin> plugins = Orderings.pluginOrdering().sortedCopy(this.documentationPluginsManager.documentationPlugins());
            log.info("Found {0} custom documentation plugin(s)", Integer.valueOf(plugins.size()));
            Iterator var3 = plugins.iterator();

            while(var3.hasNext()) {
                DocumentationPlugin each = (DocumentationPlugin)var3.next();
                DocumentationType documentationType = each.getDocumentationType();
                if(each.isEnabled()) {
                    this.scanDocumentation(this.buildContext(each));
                } else {
                    log.info("Skipping initializing disabled plugin bean {} v{}", documentationType.getName(), documentationType.getVersion());
                }
            }
        }

    }

    private DocumentationContext buildContext(DocumentationPlugin each) {
        return each.configure(this.defaultContextBuilder(each));
    }

    private void scanDocumentation(DocumentationContext context) {
        this.scanned.addDocumentation(this.resourceListing.scan(context));
    }

    private DocumentationContextBuilder defaultContextBuilder(DocumentationPlugin each) {
        DocumentationType documentationType = each.getDocumentationType();
        return this.documentationPluginsManager.createContextBuilder(documentationType, this.defaultConfiguration).requestHandlers(this.handlerProvider.requestHandlers());
    }
}
