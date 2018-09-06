package com.rayfay.bizcloud.core.commons.autoconfig;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class ClientInfoCondition implements Condition {
	@Override
	public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
		return StringUtils.isNotBlank(conditionContext.getEnvironment().getProperty("p-rabbitmq"))
				|| StringUtils.isNotBlank(conditionContext.getEnvironment().getProperty("spring.rabbitmq.host"));
	}
}
