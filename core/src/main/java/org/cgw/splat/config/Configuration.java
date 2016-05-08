package org.cgw.splat.config;

import java.util.List;
import java.util.Map;

import org.cgw.splat.factory.ServiceObjectFactory;

/**
 * 框架配置信息模型。
 * 
 * @author guowei.cgw 2015年6月16日 下午1:18:54
 */
public class Configuration {

    /**
     * 默认服务对象工厂
     */
    private ServiceObjectFactory       defautServiceObjectFactory;

    /**
     * 插件
     */
    private List<ServiceObjectFactory> serviceObjectFactorys;

    /**
     * 常量
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