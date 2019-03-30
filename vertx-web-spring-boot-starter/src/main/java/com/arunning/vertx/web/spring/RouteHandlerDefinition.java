package com.arunning.vertx.web.spring;

import com.arunning.vertx.web.spring.annotation.RouteMapping;

import java.lang.reflect.Method;

/**
 * @author chenliangliang
 * @date 2019/3/19
 */
public class RouteHandlerDefinition {

    private Object routerBean;

    private Method routeMappedMethod;

    private String path;

    private RouteMapping routeMapping;

    public Object getRouterBean() {
        return routerBean;
    }

    public void setRouterBean(Object routerBean) {
        this.routerBean = routerBean;
    }

    public Method getRouteMappedMethod() {
        return routeMappedMethod;
    }

    public void setRouteMappedMethod(Method routeMappedMethod) {
        this.routeMappedMethod = routeMappedMethod;
    }

    public RouteMapping getRouteMapping() {
        return routeMapping;
    }

    public void setRouteMapping(RouteMapping routeMapping) {
        this.routeMapping = routeMapping;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "RouteHandlerInvocation{" +
                "routerBean=" + routerBean +
                ", routeMappedMethod=" + routeMappedMethod +
                '}';
    }
}
