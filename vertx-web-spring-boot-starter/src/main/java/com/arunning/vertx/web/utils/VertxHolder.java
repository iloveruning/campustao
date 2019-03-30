package com.arunning.vertx.web.utils;

import io.vertx.core.Vertx;

import java.util.Objects;

/**
 * @author chenliangliang
 * @date 2019/3/22
 */
public class VertxHolder {

    private static Vertx vertx;

    private VertxHolder() {
    }

    public static void init(Vertx vertx) {
        Objects.requireNonNull(vertx, "vertx must not be null");
        VertxHolder.vertx = vertx;
    }

    public static Vertx getVertx() {
        Objects.requireNonNull(vertx, "未初始化Vertx");
        return vertx;
    }
}
