package org.cgw.splat.xml.support;

import org.cgw.splat.xml.container.ConfigurableContainer;
import org.cgw.splat.xml.processor.XmlBulider;
import org.cgw.splat.xml.processor.XmlParser;

public abstract class AbstractConfigurableContainer extends AbstractContainer implements ConfigurableContainer {

    protected XmlParser  xmlParser;
    protected XmlBulider xmlBulider;

    public <T> AbstractConfigurableContainer(Class<T> clazz){
        super(clazz);
    }

    public <T> AbstractConfigurableContainer(int length, Class<T> clazz){
        super(length, clazz);
    }

    public <T> AbstractConfigurableContainer(String xml, Class<T> clazz){
        super(xml, clazz);
    }

    public <T> AbstractConfigurableContainer(String[] xmlArray, Class<T> clazz){
        super(xmlArray, clazz);
    }

    public <T> AbstractConfigurableContainer(T object, Class<T> clazz){
        super(object, clazz);
    }

    public <T> AbstractConfigurableContainer(T[] objectArray, Class<T> clazz){
        super(objectArray, clazz);
    }

    public XmlParser getXmlParser() {
        return this.xmlParser;
    }

    public void setXmlParser(XmlParser xmlParser) {
        this.xmlParser = xmlParser;
    }

    public XmlBulider getXmlBulider() {
        return this.xmlBulider;
    }

    public void setXmlBulider(XmlBulider xmlBulider) {
        this.xmlBulider = xmlBulider;
    }

    public <T> void setClass(Class<T> clazz) {
        this.clazz = clazz;
    }
}