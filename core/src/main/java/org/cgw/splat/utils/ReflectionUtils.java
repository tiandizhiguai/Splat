package org.cgw.splat.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.cgw.splat.exception.SplatException;


public class ReflectionUtils {

    /**
     * 获取指定注解的指定方法值。
     * 
     * @param annotation
     * @param methodName
     * @return 注解值
     */
    public static Object getAnnotationMethodValue(Annotation annotation, String methodName) {
        try {
            Method method = annotation.annotationType().getDeclaredMethod(methodName);
            return method.invoke(annotation, (Object[]) null);
        } catch (NoSuchMethodException e) {
            throw new SplatException(e);
        } catch (SecurityException e) {
            throw new SplatException(e);
        } catch (IllegalAccessException e) {
            throw new SplatException(e);
        } catch (IllegalArgumentException e) {
            throw new SplatException(e);
        } catch (InvocationTargetException e) {
            throw new SplatException(e);
        }
    }
}
