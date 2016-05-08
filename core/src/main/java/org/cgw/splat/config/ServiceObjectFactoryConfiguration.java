package org.cgw.splat.config;

/**
 * �����ļ�����������ģ�͡�
 * 
 * @author guowei.cgw 2015��6��5�� ����3:43:12
 */
public class ServiceObjectFactoryConfiguration {

    private Class<?> objectFactoryClassType;

    private Class<?>[] objectFactoryArgTypes;

    private Object[] objectFactoryArgValues;

    public Class<?> getObjectFactoryClassType() {
        return objectFactoryClassType;
    }

    public void setObjectFactoryClassType(Class<?> objectFactoryClassType) {
        this.objectFactoryClassType = objectFactoryClassType;
    }

    public Class<?>[] getObjectFactoryArgTypes() {
        return objectFactoryArgTypes;
    }

    public void setObjectFactoryArgTypes(Class<?>[] objectFactoryArgTypes) {
        this.objectFactoryArgTypes = objectFactoryArgTypes;
    }

    public Object[] getObjectFactoryArgValues() {
        return objectFactoryArgValues;
    }

    public void setObjectFactoryArgValues(Object[] objectFactoryArgValues) {
        this.objectFactoryArgValues = objectFactoryArgValues;
    }

}
