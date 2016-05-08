package org.cgw.splat.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;

import org.apache.commons.collections4.CollectionUtils;
import org.cgw.splat.config.ConfigurationItem;
import org.cgw.splat.constant.SplatConstant;
import org.cgw.splat.exception.SplatException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 配置文件解析器帮助类。
 * 
 * @author caoguowei 2015年6月9日 下午4:37:16
 */
public class ConfigurationFileResolverUtils {

    /**
     * 获取配置文件。
     * 
     * @return 配置文件
     */
    public static List<File> findConfigurationFiles(String locationPattern) {
        
        // classpath files
        Enumeration<URL> urls = ConfigurationFileResolverUtils.getClasspathFileUri("");
        if(urls == null){
            return null;
        }
        
        // pattern files
        List<File> allPatternFiles = new ArrayList<File>();
        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            if (!"file".equals(url.getProtocol())) {
                continue;
            }

            File rootDirFile = new File(url.getPath());
            List<File> patternFiles = NameMatchingFilePatternUtils.findNameMatchingFiles(locationPattern, rootDirFile);
            if (CollectionUtils.isEmpty(patternFiles)) {
                continue;
            }
            allPatternFiles.addAll(patternFiles);
        }
        return allPatternFiles;
    }
    
    /**
     * 获取classpath下的文件路径。
     * @param fileNamePattern
     * @return
     */
    public static Enumeration<URL> getClasspathFileUri(String fileName) {
        // classpath file
        Enumeration<URL> urls = null;
        try {
            urls = ClassLoaderUtils.getCurrentThreadClassLoader().getResources(fileName);
        } catch (IOException e) {
            throw new SplatException("Class path has an error");
        }
        return urls;
    }
    
    /**
     * 创建配置组件对象。
     * 
     * @param xmlFiles
     * @return
     */
    public static List<ConfigurationItem> findConfigurations(File xmlFile, String nodeName) {
        List<ConfigurationItem> items = new ArrayList<ConfigurationItem>();
        List<NodeList> constantNodes = findNodes(xmlFile, nodeName);
        for (NodeList constantNode : constantNodes) {
            for (int i = 0; i < constantNode.getLength(); i++) {
                Node node = constantNode.item(i);
                NamedNodeMap attrs = node.getAttributes();
                items.add(fineOneItem(attrs));
            }
        }
        
        return items;
    }

    private static List<NodeList> findNodes(File xmlFile, String nodeTagName) {
        List<NodeList> beanNodes = new ArrayList<NodeList>();
        Document doc = null;
        try {
            DocumentBuilder db = XmlDocumentUtils.buildDocumentBuilder();
            doc = db.parse(xmlFile);
        } catch (SAXException e) {
            throw new SplatException(e);
        } catch (IOException e) {
            throw new SplatException(e);
        }
        
        if(doc != null){
            Element root = doc.getDocumentElement();
            beanNodes.add(root.getElementsByTagName(nodeTagName));
        }
        return beanNodes;
    }

    private static ConfigurationItem fineOneItem(NamedNodeMap attrs) {
        ConfigurationItem item = new ConfigurationItem();
        for (int j = 0; j < attrs.getLength(); j++) {
            Node attrNode = attrs.item(j);
            if (SplatConstant.NAME_ATTRIBUTE.equals(attrNode.getNodeName())) {
                item.setName(attrNode.getNodeValue());
            } else if (SplatConstant.VALUE_ATTRIBUTE.equals(attrNode.getNodeName())) {
                item.setValue(attrNode.getNodeValue());
            }
        }
        return item;
    }
}