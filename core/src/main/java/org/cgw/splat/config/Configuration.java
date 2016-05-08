package org.cgw.splat.config;

import java.util.List;
import java.util.Map;

import org.cgw.splat.factory.ServiceObjectFactory;

/**
 * ���������Ϣģ�͡�
 * 
 * @author guowei.cgw 2015��6��16�� ����1:18:54
 */
public class Configuration {

    /**
     * Ĭ�Ϸ�����󹤳�
     */
    private ServiceObjectFactory       defautServiceObjectFactory;

    /**
     * ���
     */
    private List<ServiceObjectFactory> serviceObjectFactorys;

    /**
     * ����
     */
    private Map<String, List<String>>  properties;

    public List<ServiceObjectFactory> getServiceObjectFactorys() {
        return serviceObjectFactorys;
    }

    public void setServiceObjectFactorys(List<ServiceObjectFactory> serviceObjectFactorys) {
        this.serviceObjectFactorys = serviceObjectFactorys;
    }

    public Map<String, List<String>> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, List<String>> properties) {
        this.properties = properties;
    }

    public ServiceObjectFactory getDefautServiceObjectFactory() {
        return defautServiceObjectFactory;
    }

    public void setDefautServiceObjectFactory(ServiceObjectFactory defautServiceObjectFactory) {
        this.defautServiceObjectFactory = defautServiceObjectFactory;
    }
   
}