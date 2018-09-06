package com.rayfay.bizcloud.core.commons.exception;

import com.rayfay.bizcloud.core.commons.json.JsonResult;
import com.rayfay.bizcloud.core.commons.autoconfig.ErrorCodeAutoConfiguration;
import org.apache.commons.lang.enums.EnumUtils;
import org.apache.commons.lang.enums.ValuedEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by stzhang on 2017/4/13.
 */
@Configuration
@ComponentScan(basePackageClasses=ErrorCodeConfiguration.class)
@ControllerAdvice
public class ErrorCodeConfiguration {
    private Logger logger = LoggerFactory.getLogger(ErrorCodeConfiguration.class);

    @ExceptionHandler(NRAPException.class)
    @ResponseBody
    public JsonResult resolveException(Object exceptionObject, Exception e) {
        if (e instanceof NRAPException) {
            logger.error("系统发生异常: \n", e);
            NRAPException ex = (NRAPException) e;
            JsonResult jsonResult = new JsonResult(false);
            jsonResult.setMessage(ex.getMessage());
            jsonResult.setErrorCode(ex.getErrorCode());
            return jsonResult;
        }
        return null;
    }



}
