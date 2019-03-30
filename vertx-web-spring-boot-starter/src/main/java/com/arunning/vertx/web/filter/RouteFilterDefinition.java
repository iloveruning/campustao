package com.arunning.vertx.web.filter;

import com.arunning.vertx.web.spring.annotation.RouteFilter;

/**
 * @author chenliangliang
 * @date 2019/3/22
 */
public class RouteFilterDefinition {

    private Filter filter;

    private RouteFilter routeFilter;

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public RouteFilter getRouteFilter() {
        return routeFilter;
    }

    public void setRouteFilter(RouteFilter routeFilter) {
        this.routeFilter = routeFilter;
    }

    @Override
    public String toString() {
        return "RouteFilterDefinition{" +
                "filter=" + filter +
                ", routeFilter=" + routeFilter +
                '}';
    }
}
