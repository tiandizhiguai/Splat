package org.cgw.splat.xml.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class Annotations {

    public static Annotation[] getAnnotations(Field field) {
        return field.getAnnotations();
    }

    public static Annotation getAnnotation(Field field, Class<? extends Annotation> clazz) {
        return field.getAnnotation(clazz);
    }
}