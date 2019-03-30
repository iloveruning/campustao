package com.arunning.vertx.web.spring;

import java.util.*;

/**
 * @author chenliangliang
 * @date 2019/3/21
 */
public abstract class AbstractRegistry<T> implements Registry<T> {


    private Set<T> set = new HashSet<>(16);

    private List<T> list = new ArrayList<>(12);

    private List<T> unmodifList = Collections.unmodifiableList(list);


    protected abstract void doRegister(List<T> list, T t);


    @Override
    public synchronized void register(T t) {

        if (t instanceof SelfAdapter || set.contains(t)) {
            return;
        }

        this.set.add(t);
        doRegister(list, t);
    }

    @Override
    public synchronized boolean remove(T t) {

        boolean remove = this.set.remove(t);

        if (remove) {
            this.list.remove(t);
        }

        return remove;
    }

    @Override
    public List<T> lookup() {
        return this.unmodifList;
    }

    @Override
    public synchronized void clear() {
        this.set.clear();
        this.list.clear();
    }
}
