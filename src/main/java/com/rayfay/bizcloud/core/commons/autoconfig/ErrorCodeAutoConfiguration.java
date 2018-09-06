package com.rayfay.bizcloud.core.commons.autoconfig;

import com.rayfay.bizcloud.core.commons.displayed.DisplayedApplication;
import com.rayfay.bizcloud.core.commons.exception.*;
import com.rayfay.bizcloud.core.commons.json.JsonResult;
import org.apache.commons.lang.enums.EnumUtils;
import org.apache.commons.lang.enums.ValuedEnum;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by stzhang on 2017/4/13.
 */
@Configuration
@AutoConfigureOrder(ErrorCodeAutoConfiguration.ORDER)
@ConditionalOnBean(DisplayedApplication.class)
@ConditionalOnClass({ErrorCodeValuedEnum.class, ErrorCodeTypes.class})
@Import(ErrorCodeConfiguration.class)
public class ErrorCodeAutoConfiguration implements BeanPostProcessor {
    public static final int ORDER = -101;
    private final Logger logger = LoggerFactory.getLogger(ErrorCodeAutoConfiguration.class);
    private final Map<Integer, String> initiedThousandObjects = new ConcurrentHashMap<>();
    private final List<Integer> initiedErrorCode  = Lists.newArrayList();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//        logger.info("start to postProcessAfterInitialization {}", bean.getClass().getName());
//        logger.info("getAnnotations: ", bean.getClass().getAnnotations());
        if (AnnotationUtils.isAnnotationDeclaredLocally(ErrorCodeDefinition.class, bean.getClass())) {
            logger.info("start to process {}", bean.getClass().getName());
            ErrorCodeDefinition definition = AnnotationUtils.getAnnotation(bean.getClass(), ErrorCodeDefinition.class);
            int thousands = definition.value();
            //判断是否已经存在同样的thousands 但是不同的类名
            if (initiedThousandObjects.containsKey(thousands)) {
                String v = bean.getClass().getName();
                String kv = initiedThousandObjects.get(thousands);
                if (!v.equals(kv)) {
                    throw new FatalBeanException("存在已经重复的Thousands定义 " + kv + "|" + v);
                }
            } else {
                initiedThousandObjects.put(thousands, bean.getClass().getName());
            }
            logger.debug("basic errCode :  {}", thousands);
            Field[] fields = bean.getClass().getFields();
            for (Field field : fields) {
                if (field.getType().isAssignableFrom(ErrorCodeValuedEnum.class)){
                    logger.info("start to process field: {}", field.getName());
                    ErrorCode errCode =  AnnotationUtils.getAnnotation(field, ErrorCode.class);
                    if (errCode != null) {
                        int caculatedErrorCode = thousands * 1000 + errCode.value();
                        ValuedEnum vEnum = EnumUtils.getEnum(ErrorCodeValuedEnum.class, caculatedErrorCode);
                        if (vEnum == null) {
                            logger.debug("create an new ErrorCodeValuedEnum : {}", caculatedErrorCode);
                            ErrorCodeDescription errorCodeDescription = field.getAnnotation(ErrorCodeDescription.class);
                            String combineNameAndErrorCode = "[" + caculatedErrorCode + "]" + errCode.msg();
                            ErrorCodeValuedEnum fieldValue = new ErrorCodeValuedEnum(caculatedErrorCode, combineNameAndErrorCode);
                            if (errorCodeDescription != null) {
                                logger.debug("errorCodeDescription : {}", errorCodeDescription.value());
                                fieldValue.setDescription(errorCodeDescription.value());
                            }
                            try {
                                logger.debug("field setting");
                                field.set(bean, fieldValue);
                                initiedErrorCode.add(caculatedErrorCode);
                                logger.info("field setting successful {}", caculatedErrorCode);
                            } catch (IllegalAccessException e) {
                                logger.error("", e);
                            }
                        }else if(initiedErrorCode.contains(caculatedErrorCode)){
                            throw new FatalBeanException("存在已经重复的ErrorCode 定义 " + caculatedErrorCode
                                    + " in " + bean.getClass().getName() + "." + field.getName());
                        }
                    }
                }
            }
            logger.info("end process {}", bean.getClass().getName());
        }
        return bean;
    }


}
