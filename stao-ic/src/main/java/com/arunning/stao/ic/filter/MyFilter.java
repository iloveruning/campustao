package com.arunning.stao.ic.filter;

import com.arunning.vertx.web.filter.Filter;
import com.arunning.vertx.web.spring.annotation.RouteFilter;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chenliangliang
 * @date 2019/3/22
 */
@Slf4j
@RouteFilter(url = "/*")
public class MyFilter implements Filter {


    private AtomicInteger count = new AtomicInteger(0);

    @Override
    public boolean doFilter(RoutingContext ctx) throws Exception {

        String uri = ctx.request().uri();

        log.info("我是过滤器! uri={},count => {}", uri, count.incrementAndGet());

        ctx.cookies().forEach(c -> System.out.println("name=" + c.getName() + ",value=" + c.getValue()));

        System.out.println(ctx.session());

        if ("/login".equals(uri)) {
            return true;
        }


        return true;
    }
}
