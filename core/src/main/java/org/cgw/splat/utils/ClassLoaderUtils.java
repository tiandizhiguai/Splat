package org.cgw.splat.utils;

public class ClassLoaderUtils {

    /**
     * ���浱ǰ�̵߳�classLoader���������
     */
    private static ClassLoader currentThreadClassLoader = Thread.currentThread().getContextClassLoader();

    private ClassLoaderUtils(){
        // ��׼�ⲿ��������Ķ���
    }

    public static ClassLoader getCurrentThreadClassLoader() {
        return currentThreadClassLoader;
    }
}