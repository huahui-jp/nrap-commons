package com.rayfay.bizcloud.core.commons.utils;

import com.alibaba.fastjson.JSONObject;
import com.rayfay.bizcloud.core.commons.message.OperateLogChannelSender;
import com.rayfay.bizcloud.core.commons.model.OperateLog;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by WZHANG on 2018/2/5.
 */
@Component
@EnableBinding(OperateLogChannelSender.class)
public class OperateLogUtils  implements EnvironmentAware {

    private static final Logger logger = LoggerFactory.getLogger(OperateLogUtils.class);

    @Autowired
    private OperateLogChannelSender operateLogChannelSender;

    private static OperateLogUtils operateLogUtils;

    private String clientId;

    private String applicationName;

    @Override
    public void setEnvironment(Environment environment) {
        try{
            if(StringUtils.isNotEmpty(environment.getProperty("VCAP_APPLICATION"))) {
                JSONObject applicationJson = JSONObject.parseObject(environment.getProperty("VCAP_APPLICATION"));
                this.applicationName = applicationJson.getString("name");
            }
            if(StringUtils.isNotEmpty(environment.getProperty("clientId"))) {
                clientId =  environment.getProperty("clientId");
            }
        }catch(Exception ex) {
            this.applicationName = null;
            this.clientId = null;
        }
    }

    @PostConstruct
    public void init1() {
        operateLogUtils = this;
        operateLogUtils.operateLogChannelSender = this.operateLogChannelSender;
        operateLogUtils.clientId = this.clientId;
        operateLogUtils.applicationName = this.applicationName;
    }

    /**
     * 发送系统操作日志
     *
     * @param operateLog operateLog
     */
    public static void sendOperateLog(OperateLog operateLog){

        try {
            operateLog.setClientId(operateLogUtils.clientId);
            operateLog.setSpringApplicationName(operateLogUtils.applicationName);
            operateLogUtils.operateLogChannelSender.sendOperateLog().send(MessageBuilder.withPayload(JSONObject.toJSON(operateLog).toString()).build());
        }
        catch (Exception e)
        {
            logger.error("发送操作日志失败" + e.getMessage());
        }
    }

}
