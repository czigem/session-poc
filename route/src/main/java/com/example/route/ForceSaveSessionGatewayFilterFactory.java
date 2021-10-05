package com.example.route;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.support.GatewayToStringStyler;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;

import reactor.core.publisher.Mono;

@Component
public class ForceSaveSessionGatewayFilterFactory extends AbstractGatewayFilterFactory {
    public OrderedGatewayFilter apply(Object config) {
        return new OrderedGatewayFilter(new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                return exchange.getSession()
                               .doOnNext(session -> {
                                   exchange.getRequest()
                                           .mutate()
                                           .header("Cookie", "SESSION=" + session.getId())
                                           .build();
                               })
                               .flatMap(WebSession::save)
                               .then(chain.filter(exchange));
            }
        }, 100);
    }
}
