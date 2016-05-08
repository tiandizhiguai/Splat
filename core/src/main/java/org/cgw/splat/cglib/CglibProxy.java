package org.cgw.splat.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * ͨ��cglib�ķ��似�������������
 * 
 * @author guowei.cgw 2015��6��8�� ����8:17:02
 */
public class CglibProxy implements MethodInterceptor {

    public Object getInstance(Class<?> clazz, Class<?>[] argTypes, Object[] argValues) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return enhancer.create(argTypes, argValues);
    }

    public Object getInstance(Class<?> clazz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        Object result = methodProxy.invokeSuper(obj, args);
        return result;
    }

}
