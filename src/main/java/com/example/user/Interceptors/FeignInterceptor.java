//package com.example.user.Interceptors;
//
//import feign.RequestInterceptor;
//import feign.RequestTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.oauth2.client.OAuth2ClientContext;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.stereotype.Component;
//
//@Component
//public class FeignInterceptor implements RequestInterceptor {
//    @Autowired
//    private OAuth2ClientContext context;
//
//    @Override
//    public void apply(RequestTemplate requestTemplate) {
//        if (context.getAccessToken() != null
//                && context.getAccessToken().getValue() != null
//                && OAuth2AccessToken.BEARER_TYPE.equalsIgnoreCase(context.getAccessToken().getTokenType())) {
//            requestTemplate.header("Authorization", String.format("%s %s", OAuth2AccessToken.BEARER_TYPE, context.getAccessToken().getValue()));
//        }
//    }
//}
//
