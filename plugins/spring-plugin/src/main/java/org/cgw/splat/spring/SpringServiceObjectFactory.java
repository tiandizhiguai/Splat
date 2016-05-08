package org.cgw.splat.spring;

import javax.servlet.ServletContext;

import org.cgw.splat.exception.SplatException;
import org.cgw.splat.factory.ServiceObjectFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 通过Spring容器创建执行服务对象的工厂。
 * 
 * @author guowei.cgw 2015年5月31日 下午5:15:40
 */
public class SpringServiceObjectFactory implements ServiceObjectFactory {

    private String             name;
    private ApplicationContext applicationContext;

    public SpringServiceObjectFactory(ServletContext servletContext){
        this.applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
    }

    @Override
    public Object createServiceObject(String serviceName) {
        if (this.applicationContext == null) {
            throw new SplatException("Spring ApplicationContext is uninitialized");
        }
        if(applicationContext.containsBean(serviceName)){
            return this.applicationContext.getBean(serviceName);
        }

        return null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}