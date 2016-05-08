package org.cgw.splat.xml.utils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class Reflects {

    public static boolean isComplexType(Class<?> clazz) {
        return (Object.class.isAssignableFrom(clazz) && !Serializable.class.isAssignableFrom(clazz))
               || (clazz.isArray())
               || (Iterable.class.isAssignableFrom(clazz));
    }

    public static boolean hasField(Class<?> clazz) {
        return (clazz.getDeclaredFields() != null) && (clazz.getDeclaredFields().length != 0);
    }

    public static <T> T getInstance(Class<T> clazz) throws InstantiationException, IllegalAccessException {
        return clazz.newInstance();
    }

    public static <T> Class<T> getArrayGenericType(Field field) {
        Type type = field.getGenericType();
        if ((type instanceof ParameterizedType)) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            return (Class) parameterizedType.getActualTypeArguments()[0];
        }
        return null;
    }
}