package com.example.route;

import java.time.Duration;
import java.util.List;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;

import io.netty.handler.codec.http.HttpHeaders;
import reactor.core.publisher.Mono;

@Component
public class SessionFlush implements GatewayFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

//        List<String> cookies = exchange.getRequest().getHeaders().get("Cookie");

        return exchange
                .getSession()
                .doOnNext(session -> {
                    String sid = session.getId();
                    exchange.getRequest()
                            .mutate()
                            .header("Cookie", "SESSION="+ sid)
                            .build();
                })
                .doOnNext(session -> session.save().subscribe().dispose())
                .then(chain.filter(exchange));
    }

    @Override
    public int getOrder() {
        return 10;
    }
}

//        return exchange
//                .getSession()
//                .doOnNext(session -> {
//                String sid = session.getId();
//                exchange.getRequest()
//                .mutate()
//                .header("Cookie", "SESSION="+ sid)
//                .build();
//                })
//                .doOnNext(session ->
//                session.save().subscribe())
//                .delayElement(Duration.ofSeconds(3))
//                .then(chain.filter(exchange));
