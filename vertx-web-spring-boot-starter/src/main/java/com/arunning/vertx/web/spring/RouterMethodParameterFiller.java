package com.arunning.vertx.web.spring;

import io.vertx.ext.web.RoutingContext;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chenliangliang
 * @date 2019/3/19
 */
@Component
public class RouterMethodParameterFiller implements RouterMethodArgumentResolver, SelfAdapter {

    private Registry<RouterMethodArgumentResolver> registry = RouterMethodArgumentResolverRegistry.INSTANCE;

    private Map<MethodParameter, RouterMethodArgumentResolver> resolverCache = new ConcurrentHashMap<>(128);

    public Object[] fillParameter(RoutingContext ctx, Method routerMethod) {

        int len = routerMethod.getParameterCount();

        if (len == 0) {
            return new Object[]{};
        }

        Object[] args = new Object[len];

        for (int i = 0; i < len; i++) {
            MethodParameter parameter = new MethodParameter(routerMethod, i);
            args[i] = resolveArgument(parameter, ctx);
        }

        return args;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return getResolver(parameter) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, RoutingContext ctx) {

        RouterMethodArgumentResolver resolver = getResolver(parameter);
        if (resolver == null) {
            throw new IllegalArgumentException("Unknown parameter type [" + parameter.getParameterType().getName() + "]");
        }
        try {
            return resolver.resolveArgument(parameter, ctx);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private RouterMethodArgumentResolver getResolver(MethodParameter parameter) {
        RouterMethodArgumentResolver argumentResolver = this.resolverCache.get(parameter);
        if (argumentResolver == null) {
            for (RouterMethodArgumentResolver resolver : registry.lookup()) {
                if (resolver.supportsParameter(parameter)) {
                    argumentResolver = resolver;
                    this.resolverCache.put(parameter, argumentResolver);
                    break;
                }
            }
        }
        return argumentResolver;
    }


}
