package com.arunning.vertx.web.spring.annotation;

import java.lang.annotation.*;

/**
 * @author chenliangliang
 * @date 2019/3/21
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestHeader {

    String value() default "";

    boolean required() default true;

    String defaultValue() default "";
}
