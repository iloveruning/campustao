package com.arunning.vertx.web.spring;

import java.util.List;

/**
 * @author chenliangliang
 * @date 2019/3/21
 */
public interface Registry<T> {

    void register(T t);

    boolean remove(T t);

    List<T> lookup();

    void clear();
}
