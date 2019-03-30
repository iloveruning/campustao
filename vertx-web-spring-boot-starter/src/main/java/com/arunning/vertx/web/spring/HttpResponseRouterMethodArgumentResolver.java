package com.arunning.vertx.web.spring;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;

/**
 * @author chenliangliang
 * @date 2019/3/22
 */
@Component
public class HttpResponseRouterMethodArgumentResolver extends AbstractClassTypeRouterMethodArgumentResolver<HttpServerResponse> {

    @Override
    public Object resolveArgument(MethodParameter parameter, RoutingContext ctx) throws Exception {
        return ctx.response();
    }
}
