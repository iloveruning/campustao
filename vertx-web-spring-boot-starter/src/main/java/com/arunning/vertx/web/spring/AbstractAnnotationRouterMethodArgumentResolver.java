package com.arunning.vertx.web.spring;

import com.arunning.vertx.web.utils.ReflectionUtil;
import io.vertx.ext.web.RoutingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.ResolvableType;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;

/**
 * @author chenliangliang
 * @date 2019/3/22
 */
public abstract class AbstractAnnotationRouterMethodArgumentResolver<A extends Annotation> implements RouterMethodArgumentResolver {

    @Autowired
    private ParameterNameDiscoverer parameterNameDiscoverer;

    private Class<A> supportedAnnotation;

    protected AbstractAnnotationRouterMethodArgumentResolver(Class<A> supportedAnnotation){
        this.supportedAnnotation=supportedAnnotation;
    }

    protected AbstractAnnotationRouterMethodArgumentResolver(){
        this.supportedAnnotation=generateSupportAnnotation();
    }

    public Class<A> getSupportedAnnotation() {
        return supportedAnnotation;
    }

    @SuppressWarnings("unchecked")
    private Class<A> generateSupportAnnotation() {
        return (Class<A>) ResolvableType.forClass(this.getClass()).getSuperType().resolveGeneric(0);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(getSupportedAnnotation());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, RoutingContext ctx) throws Exception {

        Class<?> parameterType = parameter.getParameterType();

        if (!ReflectionUtil.isPrimitive(parameterType)) {

            throw new IllegalStateException("parameterType must be primitive");
        }

        Annotation a = parameter.getParameterAnnotation(getSupportedAnnotation() );

        RequestAnnoFactory.RequestAnno requestAnno = RequestAnnoFactory.getRequestAnno(a);

        String paramName = requestAnno.value();

        if (!StringUtils.hasText(paramName)) {

            String[] parameterNames = parameterNameDiscoverer.getParameterNames(parameter.getMethod());
            if (parameterNames == null) {
                if (requestAnno.required()) {
                    //todo: 自定义异常，然后进行处理
                    throw new Exception("");
                } else {
                    return null;
                }
            } else {
                paramName = parameterNames[parameter.getParameterIndex()];
            }
        }

        String param = getParamValue(ctx,paramName);

        if (param != null) {
            return ReflectionUtil.parsePrimitive(parameterType, param);
        }

        if (StringUtils.hasText(requestAnno.defaultValue())){
            return ReflectionUtil.parsePrimitive(parameterType, requestAnno.defaultValue());
        }

        if (!requestAnno.required()) {
            return null;
        }

        //todo: 自定义异常，然后进行处理
        throw new Exception("");

    }

    protected abstract String getParamValue(RoutingContext ctx,String paramName);

}
