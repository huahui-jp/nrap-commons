package com.rayfay.bizcloud.core.commons.message;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * Created by stzhang on 2017/3/13.
 */
public interface AppBusClient {
    String OUTPUT = "_springAppStarted";
    @Output(AppBusClient.OUTPUT)
    MessageChannel appBusOutput();
}
