package com.arunning.vertx.web.spring;

import com.arunning.vertx.web.utils.ReflectionUtil;

import java.util.Comparator;
import java.util.List;

/**
 * @author chenliangliang
 * @date 2019/3/21
 */
public class RouterHandlerExceptionResolverRegistry extends AbstractRegistry<RouterHandlerExceptionResolver> implements Comparator<Class<?>> {

    public static final RouterHandlerExceptionResolverRegistry INSTANCE = new RouterHandlerExceptionResolverRegistry();

    private RouterHandlerExceptionResolverRegistry() {
    }

    @Override
    protected void doRegister(List<RouterHandlerExceptionResolver> list, RouterHandlerExceptionResolver routerHandlerExceptionResolver) {
        int size = list.size();
        if (size == 0) {
            list.add(routerHandlerExceptionResolver);
            return;
        }

        Class<? extends Exception> supportException = routerHandlerExceptionResolver.getSupportException();

        int low = 0;
        int high = size - 1;
        int mid, cmp;

        while (low <= high) {

            mid = (low + high) >>> 1;

            Class<? extends Exception> se = list.get(mid).getSupportException();

            cmp = compare(supportException, se);

            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp > 0) {
                high = mid - 1;
            } else {
                list.add(mid, routerHandlerExceptionResolver);
                break;
            }
        }

        if (list.size() == size) {
            list.add(low, routerHandlerExceptionResolver);
        }

    }


    @Override
    public int compare(Class<?> c1, Class<?> c2) {

        List<Class<?>> superClasses1 = ReflectionUtil.getSuperClasses(c1, Exception.class, true);
        List<Class<?>> superClasses2 = ReflectionUtil.getSuperClasses(c2, Exception.class, true);

        int s1 = superClasses1.size();
        int s2 = superClasses2.size();

        return s1 - s2;
    }

}
