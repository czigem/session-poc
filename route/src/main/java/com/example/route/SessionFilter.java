package com.example.route;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.session.SaveMode;
import org.springframework.session.data.redis.config.annotation.web.server.EnableRedisWebSession;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;

import com.example.route.SessionFilterFactory.Config;
import reactor.core.publisher.Mono;

@EnableRedisWebSession(saveMode = SaveMode.ALWAYS)
@Component
public class SessionFilter implements GatewayFilter, Ordered {

    Config config;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Map<String, Object> sessionMap = new HashMap<>();
        sessionMap.put("param", "Hi, I am the value from cloud gateway");

        return exchange.getSession()
                       .doOnNext(webSession -> webSession.getAttributes()
                                                         .putAll(sessionMap))
                       .then(chain.filter(exchange));
    }

    @Override
    public int getOrder() {
        return -10;
    }
}
