package com.arunning.vertx.web.spring;


import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;

/**
 * @author chenliangliang
 * @date 2019/3/20
 */
public interface RouterHandlerExceptionResolver<E extends Exception> {

    default boolean supportsException(Exception ex) {
        return getSupportException().isInstance(ex);
    }

    Class<E> getSupportException();

    void resolveException(HttpServerRequest request, HttpServerResponse response, Exception ex);
}
