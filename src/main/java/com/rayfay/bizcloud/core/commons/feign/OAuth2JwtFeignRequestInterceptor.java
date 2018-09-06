package com.rayfay.bizcloud.core.commons.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * Created by stzhang on 2017/4/14.
 */
public class OAuth2JwtFeignRequestInterceptor implements RequestInterceptor {

	private Logger logger = LoggerFactory.getLogger(OAuth2JwtFeignRequestInterceptor.class);

	public static final String BEARER = "Bearer";
	public static final String AUTHORIZATION = "Authorization";
	private final OAuth2ClientContext oAuth2ClientContext;

	public OAuth2JwtFeignRequestInterceptor(OAuth2ClientContext oAuth2ClientContext) {
		this.oAuth2ClientContext = oAuth2ClientContext;
	}

	@Override
	public void apply(RequestTemplate template) {
		String accessTokenStr = extract(BEARER);
		if (accessTokenStr != null) {
			template.header(AUTHORIZATION, extract(BEARER));
		}
	}

	// 获取AccessToken
	protected String extract(String tokenType) {

		String token = FeignClientContext.getToken();

		if (StringUtils.isNotEmpty(token)) {
			return String.format("%s %s", tokenType, token);
		}

		OAuth2AccessToken accessToken = getToken();
		if (accessToken != null) {
			return String.format("%s %s", tokenType, accessToken.getValue());
		}
		return null;
	}

	private OAuth2AccessToken getToken() {
		OAuth2AccessToken accessToken = null;

		try {
			accessToken = this.oAuth2ClientContext.getAccessToken();
			if (accessToken != null && accessToken.isExpired()) {
				throw new OAuth2Exception("accessToken expired.");
			}
		} catch (Exception e) {
			logger.error("Get Access token error.", e);
		}

		return accessToken;

	}

}
