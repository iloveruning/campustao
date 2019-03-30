package com.arunning.vertx.web.spring;

import com.arunning.vertx.web.filter.RouteFilterDefinition;

import java.util.List;

/**
 * @author chenliangliang
 * @date 2019/3/22
 */
public class RouteFilterDefinitionRegistry extends AbstractRegistry<RouteFilterDefinition> {

    public static final RouteFilterDefinitionRegistry INSTANCE = new RouteFilterDefinitionRegistry();

    private RouteFilterDefinitionRegistry() {
    }

    @Override
    protected void doRegister(List<RouteFilterDefinition> list, RouteFilterDefinition routeFilterDefinition) {
        list.add(routeFilterDefinition);
    }
}
