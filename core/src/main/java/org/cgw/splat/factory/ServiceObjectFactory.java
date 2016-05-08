package org.cgw.splat.factory;

/**
 * 创建执行服务对象的抽象工厂。 <br/>
 * 如果开发者要实现自己的插件，则需要实现此接口。
 * 
 * @author guowei.cgw 2015年5月31日 下午3:46:19
 */
public interface ServiceObjectFactory {
    
    /**
     * 获取ServiceObjectFactory的名称
     * 
     * @return ServiceObjectFactory的名称
     */
    public String getName();

    /**
     * 创建服务对象。
     * 
     * @param serviceName
     * @return 服务对象
     */
    public Object createServiceObject(String serviceName);

}
