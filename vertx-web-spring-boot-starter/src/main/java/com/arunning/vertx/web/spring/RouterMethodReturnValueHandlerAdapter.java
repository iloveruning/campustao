package com.arunning.vertx.web.spring;

import io.vertx.core.http.HttpServerResponse;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chenliangliang
 * @date 2019/3/20
 */
@Component
public class RouterMethodReturnValueHandlerAdapter implements RouterMethodReturnValueHandler, SelfAdapter {

    private Registry<RouterMethodReturnValueHandler> registry = RouterMethodReturnValueHandlerRegistry.INSTANCE;

    private Map<MethodParameter, RouterMethodReturnValueHandler> returnValueHandlerCache = new ConcurrentHashMap<>(64);

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return getReturnValueHandler(returnType) != null;
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, HttpServerResponse response) throws Exception {


        RouterMethodReturnValueHandler returnValueHandler = getReturnValueHandler(returnType);

        if (returnValueHandler == null) {
            throw new IllegalStateException("No matched ReturnValueHandler for returnType [" + returnType + "] ");
        }

        if (!response.closed() && !response.ended()) {
            returnValueHandler.handleReturnValue(returnValue, returnType, response);
        }
    }

    @Nullable
    private RouterMethodReturnValueHandler getReturnValueHandler(MethodParameter parameter) {
        RouterMethodReturnValueHandler returnValueHandler = this.returnValueHandlerCache.get(parameter);

        if (returnValueHandler == null) {
            for (RouterMethodReturnValueHandler handler : registry.lookup()) {
                if (handler.supportsReturnType(parameter)) {
                    returnValueHandler = handler;
                    this.returnValueHandlerCache.put(parameter, returnValueHandler);
                    break;
                }
            }
        }

        return returnValueHandler;
    }
}
