package com.example.route;

import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class SessionFlushFactory extends AbstractGatewayFilterFactory<SessionFlushFactory.Config> {

    public SessionFlushFactory() {
        super(SessionFlushFactory.Config.class);
    }

    @Override
    public SessionFlush apply(SessionFlushFactory.Config config) {
        return new SessionFlush();
    }

    public static class Config {
    }

}
