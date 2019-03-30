package com.arunning.vertx.web.spring;

import io.vertx.core.http.HttpServerResponse;
import org.springframework.core.MethodParameter;

/**
 * @author chenliangliang
 * @date 2019/3/20
 */
public interface RouterMethodReturnValueHandler {


    boolean supportsReturnType(MethodParameter returnType);


    void handleReturnValue(Object returnValue, MethodParameter returnType, HttpServerResponse response) throws Exception;

}
