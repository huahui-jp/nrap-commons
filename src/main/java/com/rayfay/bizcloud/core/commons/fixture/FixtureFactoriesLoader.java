package com.rayfay.bizcloud.core.commons.fixture;

import com.rayfay.bizcloud.core.commons.autoconfig.AppStartedAutoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Created by STZHANG on 2017/6/8.
 */
public class FixtureFactoriesLoader {
    private static Logger logger = LoggerFactory.getLogger(AppStartedAutoConfiguration.class);

    private static final String FACTORIES_RESOURCE_LOCATION = "META-INF/fixture/fixture.factories";

    public static List<String> loadFactoryNames(Class<?> factoryClass, ClassLoader classLoader) {
        String factoryClassName = factoryClass.getName();

        try {
            Enumeration<URL> urls = classLoader != null?classLoader.getResources(FACTORIES_RESOURCE_LOCATION)
                    :ClassLoader.getSystemResources(FACTORIES_RESOURCE_LOCATION);
            ArrayList result = new ArrayList();

            while(urls.hasMoreElements()) {
                URL url = (URL)urls.nextElement();
                Properties properties = PropertiesLoaderUtils.loadProperties(new UrlResource(url));
                String factoryClassNames = properties.getProperty(factoryClassName);
                result.addAll(Arrays.asList(StringUtils.commaDelimitedListToStringArray(factoryClassNames)));
            }
            logger.info("load fixture factories: {}", result);
            return result;

        } catch (IOException e) {
            throw new IllegalArgumentException("Unable to load [" + factoryClass.getName() + "] factories from location [" + FACTORIES_RESOURCE_LOCATION + "]", e);
        }
    }




}
