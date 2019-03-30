package com.arunning.vertx.web.spring;

import com.arunning.vertx.web.spring.annotation.CookieValue;
import io.vertx.ext.web.Cookie;
import io.vertx.ext.web.RoutingContext;
import org.springframework.stereotype.Component;

/**
 * @author chenliangliang
 * @date 2019/3/21
 */
@Component
public class CookieValueAnnotationRouterMethodArgumentResolver extends AbstractAnnotationRouterMethodArgumentResolver<CookieValue> {


    @Override
    protected String getParamValue(RoutingContext ctx, String paramName) {
        Cookie cookie = ctx.getCookie(paramName);
        if (cookie == null) {
            return null;
        }

        return cookie.getValue();
    }
}
