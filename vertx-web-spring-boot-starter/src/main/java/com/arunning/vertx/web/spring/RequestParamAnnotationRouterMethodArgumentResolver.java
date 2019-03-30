package com.arunning.vertx.web.spring;

import com.arunning.vertx.web.spring.annotation.RequestParam;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.springframework.stereotype.Component;

/**
 * @author chenliangliang
 * @date 2019/3/21
 */
@Component
public class RequestParamAnnotationRouterMethodArgumentResolver extends AbstractAnnotationRouterMethodArgumentResolver<RequestParam> {


    @Override
    protected String getParamValue(RoutingContext ctx, String paramName) {

        String param = ctx.request().getParam(paramName);

        if (param != null) {
            return param;
        }

        if (!HttpMethod.GET.equals(ctx.request().method())) {

            JsonObject body = ctx.getBodyAsJson();
            if (body != null) {
                return body.getString(paramName);
            }
        }

        return null;
    }
}
