package com.arunning.vertx.web.spring;

import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.Session;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;

/**
 * @author chenliangliang
 * @date 2019/3/22
 */
@Component
public class SessionRouterMethodArgumentResolver extends AbstractClassTypeRouterMethodArgumentResolver<Session> {

    @Override
    public Object resolveArgument(MethodParameter parameter, RoutingContext ctx) throws Exception {
        return ctx.session();
    }

}
