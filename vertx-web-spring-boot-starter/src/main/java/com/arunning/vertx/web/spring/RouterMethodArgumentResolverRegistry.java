package com.arunning.vertx.web.spring;

import java.util.*;

/**
 * @author chenliangliang
 * @date 2019/3/21
 */
public class RouterMethodArgumentResolverRegistry extends AbstractRegistry<RouterMethodArgumentResolver> {

    public static final RouterMethodArgumentResolverRegistry INSTANCE = new RouterMethodArgumentResolverRegistry();

    private RouterMethodArgumentResolverRegistry() {
    }

    @Override
    protected void doRegister(List<RouterMethodArgumentResolver> list, RouterMethodArgumentResolver routerMethodArgumentResolver) {
        list.add(routerMethodArgumentResolver);
    }

}
