package org.cgw.splat.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.cgw.splat.cglib.CglibProxy;
import org.cgw.splat.constant.SplatConstant;
import org.cgw.splat.exception.SplatException;
import org.cgw.splat.factory.DefaultServiceObjectFactory;
import org.cgw.splat.factory.ServiceObjectFactory;
import org.cgw.splat.utils.ClassLoaderUtils;
import org.cgw.splat.utils.ConfigurationFileResolverUtils;

/**
 * �����ļ���������
 * 
 * @author caoguowei 2015��6��4�� ����4:31:37
 */
public class ConfigurationResolver {

    private static final String pluginLocationPattern = ".*-plugin.xml";
    private static final String configurationFile     = "splat.xml";
    
    /**
     * ��ʼ��������Ϣ��
     * 
     * @param servletConext
     * @return ������Ϣ
     */
    public Configuration resolveConfiguration(Class<?>[] objectFactoryArgTypes, Object[] objectFactoryArgValues) {
        Configuration config = new Configuration();
        // 1.��������
        config.setProperties(resolveProperty());
        // 2.�������
        config.setServiceObjectFactorys(resolveServiceObjectFactorys(objectFactoryArgTypes, objectFactoryArgValues));
        // 3.Ĭ�Ϸ�����󹤳�
        DefaultServiceObjectFactory defaultServiceObjectFactory = new DefaultServiceObjectFactory();
        Map<String, List<String>> properties = config.getProperties();
        List<String> scanPackages = properties.get(SplatConstant.SCAN_PACKAGE_ATTRIBUTE);
        defaultServiceObjectFactory.setScanPackages(scanPackages);
        config.setDefautServiceObjectFactory(defaultServiceObjectFactory);

        return config;
    }
    
    /**
     * ��ʼ�����������Ĺ������ʵ����
     * 
     * @return ������ʵ��
     */
    private List<ServiceObjectFactory> resolveServiceObjectFactorys(Class<?>[] objectFactoryArgTypes,
                                                                    Object[] objectFactoryArgValues) {
        List<File> allPatternFiles = ConfigurationFileResolverUtils.findConfigurationFiles(pluginLocationPattern);
        List<ServiceObjectFactory> serviceFactoryObjects = new ArrayList<ServiceObjectFactory>(2);
        for(File patternFile : allPatternFiles){
            List<ConfigurationItem> items = ConfigurationFileResolverUtils.findConfigurations(patternFile, SplatConstant.PLUGIN_NODE);
            List<ServiceObjectFactoryConfiguration> serviceItems = getServiceObjectFactoryConfiguration(items,
                                                                                                        objectFactoryArgTypes,
                                                                                                        objectFactoryArgValues);
            serviceFactoryObjects.addAll(createPluginObjectFacotrys(serviceItems));
        }
        return serviceFactoryObjects;
    }

    private List<ServiceObjectFactoryConfiguration> getServiceObjectFactoryConfiguration(List<ConfigurationItem> configurationItems,
                                                                                         Class<?>[] objectFactoryArgTypes,
                                                                                         Object[] objectFactoryArgValues) {
        List<ServiceObjectFactoryConfiguration> serviceItems = new ArrayList<ServiceObjectFactoryConfiguration>();
        for (ConfigurationItem item : configurationItems) {
            ServiceObjectFactoryConfiguration serviceItem = new ServiceObjectFactoryConfiguration();
            Class<?> classType = getClassByFullName(item.getValue());
            // �������̳�ServiceObjectFactory�ӿ�
            if (!ServiceObjectFactory.class.isAssignableFrom(classType)) {
                throw new IllegalStateException(item.getValue() + " must implements interface ServiceObjectFactory");
            }
            serviceItem.setObjectFactoryClassType(classType);
            serviceItem.setObjectFactoryArgTypes(objectFactoryArgTypes);
            serviceItem.setObjectFactoryArgValues(objectFactoryArgValues);
            serviceItems.add(serviceItem);
        }

        return serviceItems;
    }

    /**
     * ��ʼ��������Ϣ��
     * 
     * @return ������
     */
    private Map<String, List<String>> resolveProperty() {
        // property file
        List<File> allPatternFiles = ConfigurationFileResolverUtils.findConfigurationFiles(configurationFile);
        Map<String, List<String>> properties = new HashMap<String, List<String>>();
        for(File patternFile : allPatternFiles){
            List<ConfigurationItem> items = ConfigurationFileResolverUtils.findConfigurations(patternFile, SplatConstant.PROPERTY_NODE);
            createProperties(items, properties);
        }
        return properties;
    }
    
    /**
     * ���������������Ĺ������ʵ����
     * 
     * @param xmlFiles
     * @return
     */
    private List<ServiceObjectFactory> createPluginObjectFacotrys(List<ServiceObjectFactoryConfiguration> objectFactoryItems) {
        List<ServiceObjectFactory> plugins = new ArrayList<ServiceObjectFactory>();
        for (ServiceObjectFactoryConfiguration serviceItem : objectFactoryItems) {
            Class<?> classType = serviceItem.getObjectFactoryClassType();
            Class<?>[] objectFactoryArgTypes = serviceItem.getObjectFactoryArgTypes();
            Object[] objectFactoryArgValues = serviceItem.getObjectFactoryArgValues();
            // ͨ��cglib�����������
            CglibProxy cglibProxy = new CglibProxy();
            plugins.add((ServiceObjectFactory) cglibProxy.getInstance(classType, objectFactoryArgTypes,
                                                                      objectFactoryArgValues));
        }
        return plugins;
    }

    /**
     * ���������������Ĺ������ʵ����
     * 
     * @param xmlFiles
     * @return
     */
    private void createProperties(List<ConfigurationItem> configurationItems, Map<String, List<String>> properties) {
        for (ConfigurationItem item : configurationItems) {
            String value = item.getValue();
            if(StringUtils.isEmpty(value)){
                throw new SplatException("The property '" + item.getName() + "' has not set value, are you sure?");
            }
            String[] values = StringUtils.split(value, ",");
            properties.put(item.getName(), Arrays.asList(values));
        }
    }
    
    private Class<?> getClassByFullName(String fullClassName) {
        Class<?> clazz = null;
        try {
            clazz = ClassLoaderUtils.getCurrentThreadClassLoader().loadClass(fullClassName);
        } catch (ClassNotFoundException e) {
            throw new SplatException(e);
        }
        return clazz;
    }
    
}