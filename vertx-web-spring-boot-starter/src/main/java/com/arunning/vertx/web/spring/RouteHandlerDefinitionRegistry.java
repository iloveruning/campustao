package com.arunning.vertx.web.spring;

import java.util.Comparator;
import java.util.List;

/**
 * @author chenliangliang
 * @date 2019/3/22
 */
public class RouteHandlerDefinitionRegistry extends AbstractRegistry<RouteHandlerDefinition> implements Comparator<RouteHandlerDefinition> {

    public static final RouteHandlerDefinitionRegistry INSTANCE = new RouteHandlerDefinitionRegistry();

    private RouteHandlerDefinitionRegistry() {
    }

    @Override
    protected void doRegister(List<RouteHandlerDefinition> list, RouteHandlerDefinition definition) {
        list.add(definition);
        list.sort(this);
    }


    @Override
    public int compare(RouteHandlerDefinition o1, RouteHandlerDefinition o2) {
        return o2.getRouteMapping().order() - o1.getRouteMapping().order();
    }
}
