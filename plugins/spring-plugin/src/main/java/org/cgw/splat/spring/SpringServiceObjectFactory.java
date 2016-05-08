package org.cgw.splat.spring;

import javax.servlet.ServletContext;

import org.cgw.splat.exception.SplatException;
import org.cgw.splat.factory.ServiceObjectFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * ͨ��Spring��������ִ�з������Ĺ�����
 * 
 * @author guowei.cgw 2015��5��31�� ����5:15:40
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