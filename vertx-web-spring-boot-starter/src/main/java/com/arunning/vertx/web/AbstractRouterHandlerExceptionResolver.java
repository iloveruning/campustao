package com.arunning.vertx.web;

import com.arunning.vertx.web.spring.RouterHandlerExceptionResolver;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author chenliangliang
 * @date 2019/3/24
 */
public abstract class AbstractRouterHandlerExceptionResolver<E extends Exception> implements RouterHandlerExceptionResolver<E> {


    private Class<E> supportException;

    protected AbstractRouterHandlerExceptionResolver(Class<E> supportException) {
        this.supportException = supportException;
    }

    protected AbstractRouterHandlerExceptionResolver() {
        this.supportException = generateSupportException();
    }

    @Override
    public Class<E> getSupportException() {
        return this.supportException;
    }

    @SuppressWarnings("unchecked")
    private Class<E> generateSupportException() {
        Type type = this.getClass().getGenericSuperclass();

        ParameterizedType p = (ParameterizedType) type;

        return (Class<E>) p.getActualTypeArguments()[0];
    }
}
