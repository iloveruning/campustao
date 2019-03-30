package com.arunning.vertx.web.spring;

import io.vertx.ext.web.RoutingContext;
import org.springframework.core.MethodParameter;

/**
 * @author chenliangliang
 * @date 2019/3/19
 */
public interface RouterMethodArgumentResolver {


    boolean supportsParameter(MethodParameter parameter);


    Object resolveArgument(MethodParameter parameter, RoutingContext ctx) throws Exception;
}
