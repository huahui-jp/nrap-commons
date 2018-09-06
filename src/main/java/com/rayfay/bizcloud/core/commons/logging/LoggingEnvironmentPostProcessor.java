package com.rayfay.bizcloud.core.commons.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.util.PluginManager;
import org.apache.logging.log4j.core.config.plugins.util.ResolverUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * Created by stzhang on 2017/3/20.
 */
public class LoggingEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

    final private String SYSTEM_APP_NAME_KEY = "spring.app.name";
    final private String SYSTEM_ROOT_LEVEL = "logging.level.root";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String springApplicationName = environment.resolvePlaceholders("${spring.application.name}");
        String levelKey = "${log4j2.logging.level.root}";
        String rootLevel = environment.resolvePlaceholders(levelKey);
        if(levelKey.equals(rootLevel)){
            rootLevel = null;
        }
        environment.getSystemProperties().put(SYSTEM_APP_NAME_KEY, springApplicationName);
        final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
//        System.out.println("ctx:" + ctx.toString());
        Configuration config = ctx.getConfiguration();
        if(rootLevel != null) {
            config.getProperties().put(SYSTEM_ROOT_LEVEL, rootLevel);
        }
        config.getProperties().put(SYSTEM_APP_NAME_KEY, springApplicationName);
//        ThreadContext.put(SYSTEM_APP_NAME_KEY, springApplicationName);
        System.setProperty(SYSTEM_APP_NAME_KEY, springApplicationName);
        // scan log4j2 plugins.
        String thisPackageName = this.getClass().getPackage().getName();
        System.out.println("scans log4j2 plugins in " + thisPackageName);
        PluginManager.addPackage(thisPackageName);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 11;

    }
}