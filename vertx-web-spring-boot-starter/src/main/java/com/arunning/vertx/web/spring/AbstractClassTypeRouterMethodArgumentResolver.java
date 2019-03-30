package com.arunning.vertx.web.spring;

import org.springframework.core.MethodParameter;
import org.springframework.core.ResolvableType;

/**
 * @author chenliangliang
 * @date 2019/3/24
 */
public abstract class AbstractClassTypeRouterMethodArgumentResolver<T> implements RouterMethodArgumentResolver {

    private Class<T> supportClass;

    protected AbstractClassTypeRouterMethodArgumentResolver() {
        this.supportClass = generateSupportAnnotation();
    }

    protected AbstractClassTypeRouterMethodArgumentResolver(Class<T> supportClass) {
        this.supportClass = supportClass;
    }

    @SuppressWarnings("unchecked")
    private Class<T> generateSupportAnnotation() {
        return (Class<T>) ResolvableType.forClass(this.getClass()).getSuperType().resolveGeneric(0);
    }

    public Class<T> getSupportClass() {
        return this.supportClass;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return getSupportClass().isAssignableFrom(parameter.getParameterType());
    }
}
