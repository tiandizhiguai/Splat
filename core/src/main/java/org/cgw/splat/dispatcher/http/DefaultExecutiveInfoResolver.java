package org.cgw.splat.dispatcher.http;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.cgw.splat.config.Configuration;
import org.cgw.splat.dispatcher.ExecutiveInfoHolder;
import org.cgw.splat.dispatcher.ExecutiveInfoResolver;
import org.cgw.splat.dispatcher.RequestInfoHolder;
import org.cgw.splat.exception.SplatException;
import org.cgw.splat.factory.ServiceObjectFactory;

/**
 * 创建目标服务和目标方法的处理器。
 * 
 * @author caoguowei 2015年5月23日 下午2:21:37
 */
public class DefaultExecutiveInfoResolver implements ExecutiveInfoResolver {

    /**
     * 处理目标服务服务对象和目标方法。
     * 
     * @param httpRequest
     * @param context
     * @return
     */
    public ExecutiveInfoHolder resolveServiceAndMethod(RequestInfoHolder requestInfoHolder, Configuration configuration) {

        String serviceName = requestInfoHolder.getServiceName();

        // 1.寻找目标服务对象
        // 1.1 这里依次从配置的serviceObjectFactory中寻找目标服务对象
        Object targetService = null;
        List<ServiceObjectFactory> serviceObjectFactoryPlugins = configuration.getServiceObjectFactorys();
        for (ServiceObjectFactory serviceObjectFactoryPlugin : serviceObjectFactoryPlugins) {
            targetService = serviceObjectFactoryPlugin.createServiceObject(serviceName);
            if (targetService != null) {
                break;
            }
        }

        // 1.2 如果从配置的插件中找不到服务对象，就去默认的服务对象工厂中找
        if (targetService == null) {
            ServiceObjectFactory defaultServiceObjectFactory = configuration.getDefautServiceObjectFactory();
            if (defaultServiceObjectFactory == null) {
                throw new SplatException("DefaultServiceObjectFactory is null");
            }
            targetService = defaultServiceObjectFactory.createServiceObject(serviceName);
        }

        if (targetService == null) {
            throw new SplatException("Target Service '" + serviceName + "' not found, are you sure?");
        }

        // 2.寻找目标方法
        String methodName = requestInfoHolder.getMethodName();
        Class<?> serviceClass = targetService.getClass();
        Method[] beanMethods = serviceClass.getMethods();
        Method targetMethod = null;
        for (Method method : beanMethods) {
            if (method.getName().equals(methodName)) {
                targetMethod = method;
                break;
            }
        }

        if (targetMethod == null) {
            throw new SplatException("Target Method '" + methodName + "' not found, are you sure?");
        }

        // 3.解析目标方法参数
        Object[] mehtodParam = resolveParam(requestInfoHolder.getParameters(), targetMethod);

        // 目标服务对象和目标方法持有者
        ExecutiveInfoHolder executiveInfoHolder = new ExecutiveInfoHolder();
        executiveInfoHolder.setTargetService(targetService);
        executiveInfoHolder.setTargetMethod(targetMethod);
        executiveInfoHolder.setMethodParameters(mehtodParam);
        executiveInfoHolder.setRequestInfoHolder(requestInfoHolder);

        return executiveInfoHolder;
    }

    /**
     * 解析方法的参数。
     * 
     * @param paramterMap
     * @param executeMethod
     * @return
     */
    private Object[] resolveParam(Map<String, String> parameters, Method executeMethod) {
        Class<?>[] parameterTypes = executeMethod.getParameterTypes();
        List<Object> paramInstances = new ArrayList<Object>(parameterTypes.length);
        for (Class<?> param : parameterTypes) {
            Object paramInstance = null;
            try {
                paramInstance = param.newInstance();
                PropertyDescriptor[] descriptors = Introspector.getBeanInfo(param).getPropertyDescriptors();
                setValues(parameters, descriptors, paramInstance);
            } catch (InstantiationException e) {
                throw new SplatException(e);
            } catch (IllegalAccessException e) {
                throw new SplatException(e);
            } catch (IntrospectionException e) {
                throw new SplatException(e);
            }
            paramInstances.add(paramInstance);
        }
        return paramInstances.toArray(new Object[0]);
    }

    /**
     * 绑定方法的参数值
     * 
     * @param paramterMap
     * @param descriptors
     * @param paramInstance
     */
    private void setValues(Map<String, String> parameters, PropertyDescriptor[] descriptors, Object paramInstance) {
        for (PropertyDescriptor descriptor : descriptors) {
            Method setter = descriptor.getWriteMethod();
            if (setter == null) {
                continue;
            }
            String paramValue = parameters.get(descriptor.getName());
            if (StringUtils.isBlank(paramValue)) {
                continue;
            }
            Class<?> paramType = descriptor.getPropertyType();
            try {
                if (paramType.isAssignableFrom(int.class)) {
                    setter.invoke(paramInstance, Integer.valueOf(paramValue));
                } else if (paramType.isAssignableFrom(boolean.class)) {
                    setter.invoke(paramInstance, Boolean.valueOf(paramValue));
                } else {
                    setter.invoke(paramInstance, paramValue);
                }
            } catch (NumberFormatException e) {
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
}