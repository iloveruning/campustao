package com.arunning.vertx.web.spring.annotation;

import java.lang.annotation.*;

/**
 * @author chenliangliang
 * @date 2019/3/20
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PathParam {

    String value() default "";

    boolean required() default true;

    String defaultValue() default "";


}
