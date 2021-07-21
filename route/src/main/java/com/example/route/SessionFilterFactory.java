package com.example.route;

import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class SessionFilterFactory extends AbstractGatewayFilterFactory<SessionFilterFactory.Config> {

    public SessionFilterFactory() {
        super(Config.class);
    }

    @Override
    public SessionFilter apply(Config config) {
        return new SessionFilter();
    }


    public static class Config {
    }

}
