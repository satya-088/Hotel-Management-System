package com.hms.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.hms.util.JwtUtil;

import reactor.core.publisher.Mono;
@Component
public class AuthenticationFilter implements GlobalFilter{
	 @Autowired
	    private RouteValidator validator;

	    @Autowired
	    private JwtUtil jwtUtil;

	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		System.out.println("****my filtering*****");
        
        	ServerHttpRequest request=null;
        	//chks the URL whether it is login/register/eureka
            if (validator.isSecured.test(exchange.getRequest())) {
        		System.out.println("***if*****");

            	
                //header contains token or not
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("missing authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                try {
                    jwtUtil.validateToken(authHeader);
                    System.out.println("validation success...");
                 request=  exchange.getRequest().mutate().
                		 header("loggedInUser", jwtUtil.getUsernameFromToken(authHeader)).build();

                } catch (Exception e) {
                    System.out.println("invalid access...!");
                    throw new RuntimeException("un authorized access to application"+e.getMessage());
                }
            }
            return chain.filter(exchange.mutate().request(request).build());
     
	}


	
}