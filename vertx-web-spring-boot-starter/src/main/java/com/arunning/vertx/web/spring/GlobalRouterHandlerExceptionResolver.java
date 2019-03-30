package com.arunning.vertx.web.spring;

import com.arunning.vertx.web.AbstractRouterHandlerExceptionResolver;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import org.springframework.stereotype.Component;

/**
 * @author chenliangliang
 * @date 2019/3/20
 */
@Component
public class GlobalRouterHandlerExceptionResolver extends AbstractRouterHandlerExceptionResolver<Exception> {


    @Override
    public void resolveException(HttpServerRequest request, HttpServerResponse response, Exception ex) {

        response.putHeader(HttpHeaders.CONTENT_TYPE, "text/plain; charset=utf-8").setStatusCode(500).end(ex.toString());

    }

}
