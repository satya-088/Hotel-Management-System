package com.hms.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
 
@Configuration
public class FeignRequestInterceptor implements RequestInterceptor{
	@Override
    public void apply(RequestTemplate requestTemplate) {
        // Retrieve the current HttpServletRequest
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String authToken = request.getHeader("Authorization"); // Adjust the header name as needed
            System.out.println("Token of  feign: "+authToken);
            if (authToken != null) {
                // Add the token to Feign request headers
                requestTemplate.header("Authorization", authToken);
            }
        }
    }
}

