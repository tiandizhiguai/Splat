package org.cgw.splat.dispatcher;

import org.cgw.splat.config.Configuration;

/**
 * 创建目标服务和目标方法的抽象处理器。
 * 
 * @author caoguowei 2015年5月23日 下午2:21:37
 */
public interface ExecutiveInfoResolver {

    /**
     * 处理目标服务服务对象和目标方法。
     * 
     * @param httpRequest
     * @param context
     * @return
     */
    public ExecutiveInfoHolder resolveServiceAndMethod(RequestInfoHolder requestInfoHolder, Configuration configuration);

}