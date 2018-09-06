//package com.rayfay.bizcloud.core.commons.security;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
//import org.springframework.security.web.FilterChainProxy;
//
//import javax.servlet.*;
//import java.io.IOException;
//import java.util.Enumeration;
//
///**
// * Created by STZHANG on 2017/9/5.
// */
//public class OAuth2NoTokenSkipFilter implements Filter {
//
//    private static final String FILTER_APPLIED = "__spring_security_filterSecurityInterceptor_filterApplied";
//    private static final String FILTER_CHAIN_APPLIED = FilterChainProxy.class.getName().concat(".APPLIED");
//
//    private Logger logger = LoggerFactory.getLogger(OAuth2NoTokenSkipFilter.class);
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        logger.info("OAuth2NoTokenSkipFilter init.");
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//      logger.debug("OAuth2NoTokenSkipFilter doFilter.");
//      Object accessToken =  request.getAttribute(OAuth2AuthenticationDetails.ACCESS_TOKEN_VALUE);
//      if(accessToken == null){
//          logger.debug("No token in request, will continue chain And skip FilterSecurityInterceptor.");
//          request.setAttribute(FILTER_APPLIED, Boolean.TRUE);
//          request.removeAttribute(FILTER_CHAIN_APPLIED);
//      }
//      chain.doFilter(request, response);
//        //dsssssss
//        Enumeration<String> attrNames = request.getAttributeNames();
//        if(attrNames.hasMoreElements()){
//            request.removeAttribute(attrNames.nextElement());
//        }
//
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//}
