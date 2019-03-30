package com.arunning.vertx.web.spring;

import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author chenliangliang
 * @date 2019/3/20
 */
@Component
public class RouterHandlerExceptionResolverAdapter implements RouterHandlerExceptionResolver<Exception>, SelfAdapter {

    private Registry<RouterHandlerExceptionResolver> registry = RouterHandlerExceptionResolverRegistry.INSTANCE;

    private Map<Exception, RouterHandlerExceptionResolver> exceptionResolverCache = new ConcurrentHashMap<>(128);

    @Override
    public Class<Exception> getSupportException() {
        return Exception.class;
    }

    @Override
    public boolean supportsException(Exception ex) {
        return getExceptionResolver(ex) != null;
    }

    @Override
    public void resolveException(HttpServerRequest request, HttpServerResponse response, Exception ex) {
        RouterHandlerExceptionResolver resolver = getExceptionResolver(ex);

        if (resolver == null) {
            throw new IllegalStateException("Unknown exception type [" + ex.getClass().getCanonicalName() + "]");
        }

        resolver.resolveException(request, response, ex);
    }

    private RouterHandlerExceptionResolver getExceptionResolver(Exception ex) {

        RouterHandlerExceptionResolver exceptionResolver = this.exceptionResolverCache.get(ex);

        if (exceptionResolver == null) {
            for (RouterHandlerExceptionResolver resolver : registry.lookup()) {
                if (resolver.supportsException(ex)) {
                    exceptionResolver = resolver;
                    this.exceptionResolverCache.put(ex, exceptionResolver);
                    break;
                }
            }
        }
        return exceptionResolver;
    }
}
