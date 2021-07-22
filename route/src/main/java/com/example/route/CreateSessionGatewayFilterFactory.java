package com.example.route;

import java.util.HashMap;
import java.util.Map;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class CreateSessionGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {
    @Override
    public GatewayFilter apply(Object config) {
        return new GatewayFilter() {

            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                Map<String, Object> sessionMap = new HashMap<>();
                sessionMap.put("param", "Hi, I am the value from cloud gateway");
                return exchange.getSession()
                               .doOnNext(webSession -> webSession.getAttributes()
                                                                 .putAll(sessionMap))
                               .then(chain.filter(exchange));
            }
        };
    }
}
