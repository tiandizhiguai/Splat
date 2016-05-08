package org.cgw.splat.utils;

public class ClassLoaderUtils {

    /**
     * 缓存当前线程的classLoader以提高性能
     */
    private static ClassLoader currentThreadClassLoader = Thread.currentThread().getContextClassLoader();

    private ClassLoaderUtils(){
        // 不准外部创建此类的对象
    }

    public static ClassLoader getCurrentThreadClassLoader() {
        return currentThreadClassLoader;
    }
}