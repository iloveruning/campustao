package com.arunning.vertx.web.spring;

import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.RoutingContext;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;

/**
 * @author chenliangliang
 * @date 2019/3/22
 */
@Component
public class HttpRequestRouterMethodArgumentResolver extends AbstractClassTypeRouterMethodArgumentResolver<HttpServerRequest> {

    @Override
    public Object resolveArgument(MethodParameter parameter, RoutingContext ctx) throws Exception {
        return ctx.request();
    }
}
