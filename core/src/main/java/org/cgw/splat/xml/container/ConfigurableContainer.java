package org.cgw.splat.xml.container;

import org.cgw.splat.xml.processor.XmlBulider;
import org.cgw.splat.xml.processor.XmlParser;

public abstract interface ConfigurableContainer extends Container {

    public abstract XmlParser getXmlParser();

    public abstract void setXmlParser(XmlParser paramXmlParser);

    public abstract XmlBulider getXmlBulider();

    public abstract void setXmlBulider(XmlBulider paramXmlBulider);

    public abstract <T> void setClass(Class<T> paramClass);
}