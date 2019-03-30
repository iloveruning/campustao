package com.arunning.vertx.web.utils;

import java.util.*;
import java.util.function.Function;

/**
 * @author chenliangliang
 * @date 2019/3/20
 */
public class ReflectionUtil {

    private static Map<Class<?>, Function<String, Object>> primitiveClassMap = new HashMap<>();

    static {

        Function<String, Object> charFun = v -> {
            if (v.length() == 1) {
                return v.charAt(0);
            }
            throw new IllegalArgumentException("value:" + v + " is not Character");
        };

        primitiveClassMap.put(Integer.class, Integer::valueOf);
        primitiveClassMap.put(Short.class, Short::valueOf);
        primitiveClassMap.put(Byte.class, Byte::valueOf);
        primitiveClassMap.put(Character.class, charFun);
        primitiveClassMap.put(Long.class, Long::valueOf);
        primitiveClassMap.put(Double.class, Double::valueOf);
        primitiveClassMap.put(Float.class, Float::valueOf);
        primitiveClassMap.put(Boolean.class, Boolean::valueOf);

        primitiveClassMap.put(int.class, Integer::parseInt);
        primitiveClassMap.put(short.class, Short::valueOf);
        primitiveClassMap.put(byte.class, Byte::valueOf);
        primitiveClassMap.put(long.class, Long::valueOf);
        primitiveClassMap.put(char.class, charFun);
        primitiveClassMap.put(double.class, Double::valueOf);
        primitiveClassMap.put(float.class, Float::valueOf);
        primitiveClassMap.put(boolean.class, Boolean::valueOf);

        primitiveClassMap.put(String.class, v -> v);
    }


    private ReflectionUtil() {
    }


    /**
     * 是否是基本类型
     *
     * @param parameterType
     * @return
     */
    public static boolean isPrimitive(Class<?> parameterType) {
        return primitiveClassMap.containsKey(parameterType);
    }


    public static Object parsePrimitive(Class<?> parameterType, String value) {

        Function<String, Object> fun = primitiveClassMap.get(parameterType);

        if (Objects.isNull(fun)) {
            throw new IllegalArgumentException("parameterType=" + parameterType + " is not primitive-class");
        }

        return fun.apply(value);
    }

    public static List<Class<?>> getSuperClasses(Class<?> clz, Class<?> finalClz, boolean includeSelf) {
        List<Class<?>> list = new ArrayList<>();
        if (includeSelf) {
            list.add(clz);
        }
        Class<?> clazz = clz;
        while (true) {

            if (clazz.equals(finalClz)) {
                break;
            }

            clazz = clazz.getSuperclass();
            if (clazz == null) {
                break;
            }

            list.add(clazz);
        }
        return list;
    }

    public static List<Class<?>> getSuperClasses(Class<?> clz, boolean includeSelf) {
        return getSuperClasses(clz, null, includeSelf);
    }



}
