package com.arunning.vertx.web.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author chenliangliang
 * @date 2019/3/22
 */
@Component
public class RouteHandlerFactory {

    @Autowired
    private RouterMethodParameterFiller parameterFiller;
    @Autowired
    private RouterMethodReturnValueHandlerAdapter returnValueHandler;
    @Autowired
    private RouterHandlerExceptionResolverAdapter exceptionResolver;


    public RouteHandler newRouteHandler(RouteHandlerDefinition definition) {
        RouteHandler handler = new RouteHandler(definition);

        handler.setExceptionResolver(exceptionResolver);
        handler.setParameterFiller(parameterFiller);
        handler.setReturnValueHandler(returnValueHandler);

        return handler;
    }
}
