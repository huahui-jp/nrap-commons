package com.rayfay.bizcloud.core.commons.logging;

import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Order;
import org.apache.logging.log4j.core.config.plugins.Plugin;

/**
 * Created by stzhang on 2017/3/20.
 */
@Plugin(name="Log4j2XmlConfigurationFactory", category="ConfigurationFactory")
@Order(5)
public class Log4j2XmlConfigurationFactory extends ConfigurationFactory {
    public static final String[] SUFFIXES = new String[] {".xml", "*"};

    @Override
    protected String[] getSupportedTypes() {
        return SUFFIXES;
    }

    @Override
    public Configuration getConfiguration(ConfigurationSource source) {
        return new Log4j2XmlConfiguration(source);
    }
}
