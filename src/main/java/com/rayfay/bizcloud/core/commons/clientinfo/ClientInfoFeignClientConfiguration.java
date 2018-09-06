package com.rayfay.bizcloud.core.commons.clientinfo;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by shenfu on 2017/7/13.
 */
@Configuration
public class ClientInfoFeignClientConfiguration {
	private final ClientInfo clientInfo;

	@Autowired
	public ClientInfoFeignClientConfiguration(ClientInfo clientInfo) {
		this.clientInfo = clientInfo;
	}

	@Bean
	public RequestInterceptor clientInfoInterceptor() {
		return new ClientInfoFeignRequestInterceptor(clientInfo);
	}
}
