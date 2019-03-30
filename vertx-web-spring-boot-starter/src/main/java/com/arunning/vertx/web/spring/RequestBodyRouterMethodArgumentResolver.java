package com.arunning.vertx.web.spring;

import com.alibaba.fastjson.JSONObject;
import com.arunning.vertx.web.spring.annotation.RequestBody;
import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;

/**
 * @author chenliangliang
 * @date 2019/3/21
 */
@Component
public class RequestBodyRouterMethodArgumentResolver implements RouterMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasMethodAnnotation(RequestBody.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, RoutingContext ctx) throws Exception {


        HttpMethod method = ctx.request().method();

        if (HttpMethod.GET.equals(method)) {

            MultiMap params = ctx.request().params();

            JSONObject jsonObject = new JSONObject();

            params.entries().forEach(entry -> jsonObject.put(entry.getKey(), entry.getValue()));

            return jsonObject.toJavaObject(parameter.getParameterType());

        } else {

            JsonObject json = ctx.getBodyAsJson();

            JSONObject jsonObject = new JSONObject(json.getMap());

            return jsonObject.toJavaObject(parameter.getParameterType());
        }

    }
}
