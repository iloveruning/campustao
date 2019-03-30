package com.arunning.vertx.web.spring;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author chenliangliang
 * @date 2019/3/19
 */
public class RouteHandler implements Handler<RoutingContext> {

    private static Logger logger = LoggerFactory.getLogger(RouteHandler.class);

    private RouteHandlerDefinition definition;

    private RouterMethodParameterFiller parameterFiller;

    private RouterMethodReturnValueHandlerAdapter returnValueHandler;

    private RouterHandlerExceptionResolverAdapter exceptionResolver;


    RouteHandler(RouteHandlerDefinition definition) {
        this.definition = definition;
    }


    @Override
    public void handle(RoutingContext ctx) {

        Method routeMappedMethod = definition.getRouteMappedMethod();

        Object routerBean = definition.getRouterBean();

        Object[] args = parameterFiller.fillParameter(ctx, routeMappedMethod);

        HttpServerResponse response = ctx.response();

        try {
            Object ret = routeMappedMethod.invoke(routerBean, args);
            logger.info("routeMappedMethod.invoke success");

            MethodParameter retType = new MethodParameter(routeMappedMethod, -1);

            try {
                returnValueHandler.handleReturnValue(ret, retType, response);
            } catch (Exception e) {

                logger.error("handle retureValue error.", e);

                if (!response.ended() && !response.closed()) {
                    exceptionResolver.resolveException(ctx.request(), response, e);
                }
            }
        } catch (IllegalAccessException e) {
            //ignore
        } catch (InvocationTargetException e) {
            logger.error("routerHandlerMethod invoke failed! routerHandlerMethod={},routerBean={},args={}",
                    routeMappedMethod, routerBean, args, e);

            Throwable cause = e.getTargetException();

            if (cause instanceof Exception) {

                exceptionResolver.resolveException(ctx.request(), response, (Exception) cause);
            } else {

                //todo:不可捕获的异常，直接抛出
                ctx.fail(cause);
                logger.error("不可处理异常!", cause);
            }
        }

    }

    public RouteHandlerDefinition getDefinition() {
        return definition;
    }

    public void setDefinition(RouteHandlerDefinition definition) {
        this.definition = definition;
    }

    public RouterMethodParameterFiller getParameterFiller() {
        return parameterFiller;
    }

    public void setParameterFiller(RouterMethodParameterFiller parameterFiller) {
        this.parameterFiller = parameterFiller;
    }

    public RouterMethodReturnValueHandlerAdapter getReturnValueHandler() {
        return returnValueHandler;
    }

    public void setReturnValueHandler(RouterMethodReturnValueHandlerAdapter returnValueHandler) {
        this.returnValueHandler = returnValueHandler;
    }

    public RouterHandlerExceptionResolverAdapter getExceptionResolver() {
        return exceptionResolver;
    }

    public void setExceptionResolver(RouterHandlerExceptionResolverAdapter exceptionResolver) {
        this.exceptionResolver = exceptionResolver;
    }

    @Override
    public String toString() {
        return "RouteHandler{" +
                "definition=" + definition +
                '}';
    }
}
