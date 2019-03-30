package com.arunning.vertx.web.spring;

import com.arunning.vertx.web.spring.annotation.SessionAttr;
import io.vertx.ext.web.RoutingContext;
import org.springframework.stereotype.Component;

/**
 * @author chenliangliang
 * @date 2019/3/21
 */
@Component
public class SessionAttrAnnotationRouterMethodArgumentResolver extends AbstractAnnotationRouterMethodArgumentResolver<SessionAttr> {

    @Override
    protected String getParamValue(RoutingContext ctx, String paramName) {
        return ctx.session().get(paramName).toString();
    }
}
