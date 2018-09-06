package com.rayfay.bizcloud.core.commons.clientinfo;

import com.alibaba.fastjson.JSON;
import com.rayfay.bizcloud.core.commons.utils.EncryptorUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;


/**
 * Created by shenfu on 2017/7/13.
 */
public class ClientInfoFeignRequestInterceptor implements RequestInterceptor {
	private final ClientInfo clientInfo;

	public ClientInfoFeignRequestInterceptor(ClientInfo clientInfo) {
		this.clientInfo = clientInfo;
	}

	@Override
	public void apply(RequestTemplate requestTemplate) {
		requestTemplate.header(ClientInfoKeys.CLIENT_INFO, EncryptorUtils.getEncrypt(JSON.toJSONString(clientInfo)));
	}
}
