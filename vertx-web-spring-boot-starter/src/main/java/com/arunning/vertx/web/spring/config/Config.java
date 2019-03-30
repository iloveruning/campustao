package com.arunning.vertx.web.spring.config;

/**
 * @author chenliangliang
 * @date 2019/3/24
 */
public interface Config<T> {

    void doConfig(T ctx);

}
