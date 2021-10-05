[Track issue](https://github.com/spring-cloud/spring-cloud-gateway/issues/2304)

**Describe the bug**
It seems to me that in spring-cloud-gateway with redis cache doesn't work with SaveSession GateWay Filter as it was intended.

Would you be as kind to take a look to the following question at [StackOverflow](https://stackoverflow.com/questions/66334868/spring-gateway-redis-session-store-type-saving-issue).

On top of the question at StackOverflow, I think we are dealing with two issue. One the session information is not saved to **redis** before the **rest** service is invoked and the other is that we don't have the session information in the request we pass to downstream.

**SessionFilter**
```
public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Map<String, Object> sessionMap = new HashMap<>();
        sessionMap.put("param", "Hi, I am the value from cloud gateway");
        return exchange.getSession()
                       .doOnNext(webSession -> webSession.getAttributes()
                                                         .putAll(sessionMap))
                       .then(chain.filter(exchange));
    }
```
**RestHandler**
```
    @GetMapping(value = "test", produces = { APPLICATION_JSON_VALUE })
    String test(HttpServletRequest request) {
        Cookie sessionCookie = WebUtils.getCookie(request, "SESSION");
        return "Session: " + (sessionCookie == null ? "" : sessionCookie.getValue());
    }
```
**RoteConfig**
```
spring:
  cloud:
    gateway:
      routes:
        - id: rest
          uri: http://localhost:7001
          predicates:
            - Path=/api/**
          filters:
            - SaveSession
            - SessionFilterFactory
```

**Sample**
I have created two service component, **route** and **rest**.
**route** is a Gateway router create save the session and pass it to downstream, to the **rest** service component.
**rest** is a simple rest service. When hit it returns the session cookie value or empty if it is not found.

The two service component can be found at [GitHub](https://github.com/czigem/session-poc) and you need a local redis listening on 6379 (I have used [DockerHub](https://hub.docker.com/_/redis).) After you build and start them you can try with the following command.
```
curl --location --request GET 'http://localhost:7000/api/test'
```
You will see that the response doesn't contain the SESSION information.
