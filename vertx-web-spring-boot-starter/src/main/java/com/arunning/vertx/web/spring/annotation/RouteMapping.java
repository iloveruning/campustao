package com.arunning.vertx.web.spring.annotation;

import io.vertx.core.http.HttpMethod;

import java.lang.annotation.*;

/**
 * @author chenliangliang
 * @date 2019/3/19
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface RouteMapping {

    String url();

    HttpMethod method() default HttpMethod.GET;

    int order() default 0 ;

    String[] consumes() default {};

    String[] produces() default {};

}
