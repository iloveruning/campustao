package com.arunning.vertx.web.spring.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author chenliangliang
 * @date 2019/3/20
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface RouteFilter {

    String url();


}
