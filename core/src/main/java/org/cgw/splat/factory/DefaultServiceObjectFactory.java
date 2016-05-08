package org.cgw.splat.factory;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cgw.splat.annotation.Service;
import org.cgw.splat.cglib.CglibProxy;
import org.cgw.splat.utils.PackageClassScanner;
import org.cgw.splat.utils.ReflectionUtils;

/**
 * 框架默认的ServiceObjectFactory。<br/>
 * 当没有配置任何ServiceObjectFactory时，框架会默认使用DefaultServiceObjectFactory。
 * 
 * @author caoguowei 2015年6月16日 下午2:23:10
 */
public class DefaultServiceObjectFactory implements ServiceObjectFactory {

    private List<String>        scanPackages;
    private Map<String, Object> servieObjectMap;

    public synchronized void init() {
        servieObjectMap = new HashMap<String, Object>();
        for (String scanPackage : scanPackages) {
            List<Class<?>> scanClasses = PackageClassScanner.scanPackage(scanPackage);
            for (Class<?> scanClass : scanClasses) {
                Annotation[] annotations = scanClass.getAnnotations();
                for (Annotation annotation : annotations) {
                    Class<?> annoClass = annotation.annotationType();
                    if (!Service.class.isAssignableFrom(annoClass)) {
                        continue;
                    }
                    String key = (String) ReflectionUtils.getAnnotationMethodValue(annotation, "value");
                    CglibProxy cglibProxy = new CglibProxy();
                    Object o = cglibProxy.getInstance(scanClass);
                    servieObjectMap.put(key, o);
                }
            }
        }
    }

    @Override
    public String getName() {
        return "default";
    }

    @Override
    public Object createServiceObject(String serviceName) {
        if (servieObjectMap == null) {
            init();
        }
        return servieObjectMap.get(serviceName);
    }

    public Map<String, Object> getServieObjectMap() {
        return servieObjectMap;
    }

    public void setScanPackages(List<String> scanPackages) {
        this.scanPackages = scanPackages;
    }
}
