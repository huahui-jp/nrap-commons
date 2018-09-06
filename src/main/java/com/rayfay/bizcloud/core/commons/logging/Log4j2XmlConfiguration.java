package com.rayfay.bizcloud.core.commons.logging;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.appender.rolling.SizeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.config.xml.XmlConfiguration;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by stzhang on 2017/3/20.
 */
public class Log4j2XmlConfiguration extends XmlConfiguration {
    final private String appenderName = "log_sleuth";
    final private String SYSTEM_ROOT_LEVEL = "logging.level.root";

    public Log4j2XmlConfiguration(ConfigurationSource source){
        super(source);
    }
    @Override
    protected void doConfigure() {
        super.doConfigure();
        System.out.println("Log4j2XmlConfiguration.doConfigure for sleuth ...");
        String configLocation = this.getConfigurationSource().getLocation();
        if(StringUtils.isNotBlank(configLocation)) {
            final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
            Configuration config = ctx.getConfiguration();
//          map.put("logging.pattern.level", "%clr(%5p) %clr([${spring.application.name:},%X{X-B3-TraceId:-},%X{X-B3-SpanId:-},%X{X-Span-Export:-}]){yellow}");
            final String sleuthLayout = "%d %highlight{%p} - %highlight{[${sys:spring.app.name},%X{X-B3-TraceId},%X{X-B3-SpanId},%X{X-Span-Export}]}{INFO=yellow, DEBUG=yellow, TRACE=blue=yellow} - [%highlight{%c{1.}}]  - %m%n";
            final Layout layout = PatternLayout.createLayout(sleuthLayout, null, config, null,
                    null, true, false, null, null);
            Appender appender = this.getAppender(appenderName);
            if (appender == null) {
                SizeBasedTriggeringPolicy tp = SizeBasedTriggeringPolicy.createPolicy("10M");
//                appender = FileAppender.createAppender("/home/vcap/logs/log_sleuth_.log", "false", "false", appenderName, "true",
//                        "false", "false", "4000", layout, null, "false", null, config);
                appender = RollingFileAppender.createAppender("./logs/log_sleuth_.log", "./logs/log_sleuth-%d{yyyy-MM-dd-HH}-%i.log", "true", appenderName, "false",
                        "4000", "true", tp, null,  layout, null, "false", "false", null, config);
                appender.start();
            }

            Level configedLevel = Level.INFO;
            String rootLevel = config.getProperties().get(SYSTEM_ROOT_LEVEL);
            if(StringUtils.isNotBlank(rootLevel)) {
                configedLevel =  Level.valueOf(rootLevel.trim());
            }
            /**
             * remove all console appender.
             */
            LoggerConfig rootLogger = this.getRootLogger();

            Collection<Appender> consoleAppenders = this.getAppenders().values();
            List<String> consoleAppenderNames = Lists.newArrayList();
            if (consoleAppenders != null) {
                Iterator<Appender> it = consoleAppenders.iterator();
                while (it.hasNext()) {
                    Appender a = it.next();
                    if (a instanceof ConsoleAppender) {
                        rootLogger.removeAppender(a.getName());
                        consoleAppenderNames.add(a.getName());
                        removeAppender(a.getName());
                    }
                }
            }

            //add two appenders.
            Appender consoleAppender = ConsoleAppender.createDefaultAppenderForLayout(layout);
            consoleAppender.start();


            AppenderRef ref = AppenderRef.createAppenderRef(appenderName, Level.INFO, null);
            AppenderRef ref2 = AppenderRef.createAppenderRef(consoleAppender.getName(), Level.INFO, null);


            rootLogger.removeAppender(consoleAppender.getName());
            rootLogger.addAppender(appender, configedLevel, null);
            rootLogger.addAppender(consoleAppender, configedLevel, null);
//            rootLogger.getAppenderRefs().add(ref);
//            rootLogger.getAppenderRefs().add(ref2);
            // all info.
            rootLogger.setLevel(configedLevel);

            //Other loggers
            Map<String, LoggerConfig> loggers = this.getLoggers();
            Iterator<String> loggerNamesIt = loggers.keySet().iterator();
            while (loggerNamesIt.hasNext()){
                String loggerName = loggerNamesIt.next();
                LoggerConfig lc = loggers.get(loggerName);
                for (String consoleAppenderName : consoleAppenderNames) {
                    lc.removeAppender(consoleAppenderName);
                }
                lc.addAppender(appender, configedLevel, null);
                lc.addAppender(consoleAppender, configedLevel, null);

//                lc.getAppenderRefs().add(ref);
//                lc.getAppenderRefs().add(ref2);

                // all info.
                lc.setLevel(configedLevel);
            }

            ctx.updateLoggers(config);

        }
    }

}