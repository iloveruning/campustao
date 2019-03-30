package com.arunning.vertx.web.spring;

import java.util.List;

/**
 * @author chenliangliang
 * @date 2019/3/21
 */
public class RouterMethodReturnValueHandlerRegistry extends AbstractRegistry<RouterMethodReturnValueHandler> {

    public static final RouterMethodReturnValueHandlerRegistry INSTANCE = new RouterMethodReturnValueHandlerRegistry();

    private RouterMethodReturnValueHandlerRegistry() {
    }

    @Override
    protected void doRegister(List<RouterMethodReturnValueHandler> list, RouterMethodReturnValueHandler routerMethodReturnValueHandler) {
        list.add(routerMethodReturnValueHandler);
    }
}
