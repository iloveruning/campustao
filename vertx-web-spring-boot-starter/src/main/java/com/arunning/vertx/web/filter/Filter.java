package com.arunning.vertx.web.filter;

import io.vertx.ext.web.RoutingContext;

/**
 * @author chenliangliang
 * @date 2019/3/20
 */
public interface Filter {

    default boolean blocking() {
        return false;
    }

    boolean doFilter(RoutingContext ctx) throws Exception;


}
