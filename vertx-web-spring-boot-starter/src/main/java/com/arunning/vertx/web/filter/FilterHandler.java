package com.arunning.vertx.web.filter;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.vertx.core.http.HttpHeaders.CONTENT_TYPE;

/**
 * @author chenliangliang
 * @date 2019/3/22
 */
public class FilterHandler implements Handler<RoutingContext> {

    private static Logger logger = LoggerFactory.getLogger(FilterHandler.class);

    private Filter filter;

    public FilterHandler(Filter filter) {
        this.filter = filter;
    }


    @Override
    public void handle(RoutingContext ctx) {

        final HttpServerResponse response = ctx.response();

        try {
            boolean res = filter.doFilter(ctx);
            if (res) {
                ctx.next();
            }
        } catch (Exception e) {
            logger.error("FilterHandler doFilter error.", e);
            if (!response.ended()) {
                response.putHeader(CONTENT_TYPE, "text/plain; charset=utf-8").setStatusCode(500).end(e.getMessage());
            }
        }


    }
}
