package org.cgw.splat.filter;

import org.cgw.splat.config.Configuration;
import org.cgw.splat.config.ConfigurationResolver;
import org.cgw.splat.dispatcher.DefaultServiceExecutor;
import org.cgw.splat.dispatcher.ExecutiveInfoHolder;
import org.cgw.splat.dispatcher.ExecutiveInfoResolver;
import org.cgw.splat.dispatcher.RequestInfoHolder;
import org.cgw.splat.dispatcher.ServiceExecutor;
import org.cgw.splat.dispatcher.http.DefaultExecutiveInfoResolver;

/**
 * 框架的入口，提供两个功能：1.初始化框架配置信息和组件； 2.处理用户的请求。
 * 
 * @author guowei.cgw 2015年6月8日 下午2:57:10
 */
public abstract class AbstractPrepareAndExecuteEntrance {

    /**
     * 配置信息
     */
    private Configuration configuration;

    /**
     * 初始化配置信息。
     */
    public void initConfiguration(Class<?>[] argTypes, Object[] argValues) {
        ConfigurationResolver cf = new ConfigurationResolver();
        this.configuration = cf.resolveConfiguration(argTypes, argValues);
    }

    /**
     * 处理用户请求。
     */
    public void doExecute(RequestInfoHolder requestInfoHolder) {

        // 1.获取目标服务和目标方法
        ExecutiveInfoResolver resolver = new DefaultExecutiveInfoResolver();
        ExecutiveInfoHolder beanAndMethodHolder = resolver.resolveServiceAndMethod(requestInfoHolder, configuration);

        // 2.执行服务
        ServiceExecutor serviceExecutor = new DefaultServiceExecutor();
        serviceExecutor.execute(beanAndMethodHolder);
    }

}
