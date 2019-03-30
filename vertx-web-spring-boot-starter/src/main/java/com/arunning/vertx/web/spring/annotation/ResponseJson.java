package com.arunning.vertx.web.spring.annotation;

import java.lang.annotation.*;

/**
 * @author chenliangliang
 * @date 2019/3/20
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponseJson {
}
