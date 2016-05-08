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
 * ����Ŀ������Ŀ�귽���Ĵ�������
 * 
 * @author caoguowei 2015��5��23�� ����2:21:37
 */
public class DefaultExecutiveInfoResolver implements ExecutiveInfoResolver {

    /**
     * ����Ŀ������������Ŀ�귽����
     * 
     * @param httpRequest
     * @param context
     * @return
     */
    public ExecutiveInfoHolder resolveServiceAndMethod(RequestInfoHolder requestInfoHolder, Configuration configuration) {

        String serviceName = requestInfoHolder.getServiceName();

        // 1.Ѱ��Ŀ��������
        // 1.1 �������δ����õ�serviceObjectFactory��Ѱ��Ŀ��������
        Object targetService = null;
        List<ServiceObjectFactory> serviceObjectFactoryPlugins = configuration.getServiceObjectFactorys();
        for (ServiceObjectFactory serviceObjectFactoryPlugin : serviceObjectFactoryPlugins) {
            targetService = serviceObjectFactoryPlugin.createServiceObject(serviceName);
            if (targetService != null) {
                break;
            }
        }

        // 1.2 ��������õĲ�����Ҳ���������󣬾�ȥĬ�ϵķ�����󹤳�����
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

        // 2.Ѱ��Ŀ�귽��
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

        // 3.����Ŀ�귽������
        Object[] mehtodParam = resolveParam(requestInfoHolder.getParameters(), targetMethod);

        // Ŀ���������Ŀ�귽��������
        ExecutiveInfoHolder executiveInfoHolder = new ExecutiveInfoHolder();
        executiveInfoHolder.setTargetService(targetService);
        executiveInfoHolder.setTargetMethod(targetMethod);
        executiveInfoHolder.setMethodParameters(mehtodParam);
        executiveInfoHolder.setRequestInfoHolder(requestInfoHolder);

        return executiveInfoHolder;
    }

    /**
     * ���������Ĳ�����
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
     * �󶨷����Ĳ���ֵ
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