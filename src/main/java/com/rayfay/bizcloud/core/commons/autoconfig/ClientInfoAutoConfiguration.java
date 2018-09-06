package com.rayfay.bizcloud.core.commons.autoconfig;

import com.rayfay.bizcloud.core.commons.clientinfo.ClientInfo;
import com.rayfay.bizcloud.core.commons.clientinfo.ClientInfoController;
import com.rayfay.bizcloud.core.commons.clientinfo.ClientInfoKeys;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

/**
 * Created by shenfu on 2017/7/12.
 */
@Conditional(ClientInfoCondition.class)
@Configuration
@ComponentScan(basePackageClasses = ClientInfoController.class)
@Import(ClientInfo.class)
public class ClientInfoAutoConfiguration implements EnvironmentAware {

	private final ApplicationContext applicationContext;

	@Autowired
	public ClientInfoAutoConfiguration(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Override
	public void setEnvironment(Environment environment) {
		ClientInfo clientInfo = (ClientInfo) applicationContext.getBean(ClientInfo.class.getName());
		if (StringUtils.isBlank(clientInfo.getClientId()) || StringUtils.isBlank(clientInfo.getClientKey())) {
			clientInfo.setClientId(environment.getProperty(ClientInfoKeys.CLIENT_ID));
			clientInfo.setClientKey(environment.getProperty(ClientInfoKeys.CLIENT_KEY));
		}
	}
}
