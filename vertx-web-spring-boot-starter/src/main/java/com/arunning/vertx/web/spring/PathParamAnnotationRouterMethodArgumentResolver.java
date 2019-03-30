package com.arunning.vertx.web.spring;

import com.arunning.vertx.web.spring.annotation.PathParam;
import io.vertx.ext.web.RoutingContext;
import org.springframework.stereotype.Component;

/**
 * @author chenliangliang
 * @date 2019/3/20
 */
@Component
public class PathParamAnnotationRouterMethodArgumentResolver extends AbstractAnnotationRouterMethodArgumentResolver<PathParam> {


    @Override
    protected String getParamValue(RoutingContext ctx, String paramName) {
        return ctx.request().getParam(paramName);
    }
}
