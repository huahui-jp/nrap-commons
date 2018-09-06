package com.rayfay.bizcloud.core.commons.message;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * Created by huangzy on 2017/8/29.
 */
public interface OperateLogChannelSender {
	String OPERATE_LOG_INFO_SENDER = "_operateLogInfoAsync";

	@Output(OperateLogChannelSender.OPERATE_LOG_INFO_SENDER)
	MessageChannel sendOperateLog();

}
