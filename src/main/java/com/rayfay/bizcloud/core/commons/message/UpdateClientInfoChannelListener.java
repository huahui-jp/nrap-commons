package com.rayfay.bizcloud.core.commons.message;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * Created by shenfu on 2017/7/19.
 */
public interface UpdateClientInfoChannelListener {
	String UPDATE_CLIENT_INFO_LISTENER = "_updateClientInfoChannel";
	String UPDATE_CLIENT_INFO_APIGATEWAY_LISTENER = "_updateClientInfoApiGatewayChannel";

	@Input(UpdateClientInfoChannelListener.UPDATE_CLIENT_INFO_LISTENER)
	SubscribableChannel updateClientInfo();

	@Input(UpdateClientInfoChannelListener.UPDATE_CLIENT_INFO_APIGATEWAY_LISTENER)
	SubscribableChannel updateClientInfoApiGateway();
}
