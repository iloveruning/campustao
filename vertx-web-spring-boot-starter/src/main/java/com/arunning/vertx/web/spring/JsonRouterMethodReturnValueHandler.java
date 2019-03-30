package com.arunning.vertx.web.spring;

import com.alibaba.fastjson.JSON;
import com.arunning.vertx.web.spring.annotation.ResponseJson;
import com.arunning.vertx.web.spring.annotation.RestRouter;
import io.vertx.core.http.HttpServerResponse;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;

import static io.vertx.core.http.HttpHeaders.CONTENT_TYPE;

/**
 * @author chenliangliang
 * @date 2019/3/20
 */
@Component
public class JsonRouterMethodReturnValueHandler implements RouterMethodReturnValueHandler {


    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return returnType.hasMethodAnnotation(ResponseJson.class) ||
                returnType.getDeclaringClass().isAnnotationPresent(RestRouter.class);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, HttpServerResponse response) throws Exception {

        response.headers().add(CONTENT_TYPE, "application/json; charset=utf-8");

        String retJson;
        if (returnValue == null) {
            retJson = "";
        } else {
            retJson = JSON.toJSONString(returnValue);
        }

        response.setStatusCode(200).end(retJson);
    }
}
