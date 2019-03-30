package com.arunning.vertx.web.spring;

import com.arunning.vertx.web.spring.annotation.RequestHeader;
import io.vertx.ext.web.RoutingContext;

/**
 * @author chenliangliang
 * @date 2019/3/22
 */
public class RequestHeaderAnnotationRouterMethodArgumentResolver extends AbstractAnnotationRouterMethodArgumentResolver<RequestHeader> {

    @Override
    protected String getParamValue(RoutingContext ctx, String paramName) {
        return ctx.request().getHeader(paramName);
    }
}
