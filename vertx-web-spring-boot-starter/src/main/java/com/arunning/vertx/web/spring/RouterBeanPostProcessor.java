package com.arunning.vertx.web.spring;

import com.arunning.vertx.web.filter.Filter;
import com.arunning.vertx.web.filter.RouteFilterDefinition;
import com.arunning.vertx.web.spring.annotation.RestRouter;
import com.arunning.vertx.web.spring.annotation.RouteFilter;
import com.arunning.vertx.web.spring.annotation.RouteMapping;
import com.arunning.vertx.web.spring.annotation.Router;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author chenliangliang
 * @date 2019/3/19
 */
@Component
public class RouterBeanPostProcessor implements BeanPostProcessor {

    private Registry<RouteFilterDefinition> filterDefinitionRegistry = RouteFilterDefinitionRegistry.INSTANCE;
    private Registry<RouteHandlerDefinition> handlerDefinitionRegistry = RouteHandlerDefinitionRegistry.INSTANCE;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        Class<?> beanClass = bean.getClass();
        Router router = AnnotationUtils.findAnnotation(beanClass, Router.class);
        RestRouter restRouter = AnnotationUtils.findAnnotation(beanClass, RestRouter.class);

        if (router != null || restRouter != null) {

            String value = restRouter == null ? router.value() : restRouter.value();

            String basePath = handlePath(value);

            for (Method method : beanClass.getDeclaredMethods()) {

                RouteMapping routeMapping = AnnotationUtils.findAnnotation(method, RouteMapping.class);

                if (routeMapping != null) {

                    method.setAccessible(true);

                    String path = basePath + handlePath(routeMapping.url());

                    RouteHandlerDefinition definition = new RouteHandlerDefinition();
                    definition.setPath(path);
                    definition.setRouterBean(bean);
                    definition.setRouteMappedMethod(method);
                    definition.setRouteMapping(routeMapping);

                    handlerDefinitionRegistry.register(definition);
                }
            }

        }

        RouteFilter routeFilter = AnnotationUtils.getAnnotation(beanClass, RouteFilter.class);

        if (routeFilter != null && bean instanceof Filter) {

            RouteFilterDefinition routeFilterDefinition = new RouteFilterDefinition();
            routeFilterDefinition.setFilter((Filter) bean);
            routeFilterDefinition.setRouteFilter(routeFilter);

            filterDefinitionRegistry.register(routeFilterDefinition);
        }

        return bean;
    }


    private String handlePath(String path) {
        String s = path == null ? "" : path.trim();
        if (s.length() > 0) {
            if (!s.startsWith("/")) {
                s = "/" + s;
            }
            if (path.endsWith("/")) {
                s = s.substring(0, s.length() - 1);
            }
        }
        return s;
    }
}
