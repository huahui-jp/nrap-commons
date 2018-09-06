package com.rayfay.bizcloud.core.commons.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by STZHANG on 2017/9/5.
 */
public class OAuth2TokenRequestMatcher implements RequestMatcher {
    private TokenExtractor tokenExtractor = new BearerTokenExtractor();

    @Override
    public boolean matches(HttpServletRequest request) {
        Authentication authentication = tokenExtractor.extract(request);
        return (authentication != null && isAuthenticated(authentication));
    }

    private boolean isAuthenticated(Authentication authentication) {
        return authentication != null && authentication.getPrincipal() != null && !(authentication instanceof AnonymousAuthenticationToken);
    }
}
