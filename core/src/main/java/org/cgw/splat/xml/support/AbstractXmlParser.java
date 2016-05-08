package org.cgw.splat.xml.support;

import org.cgw.splat.xml.processor.XmlParser;

public abstract class AbstractXmlParser implements XmlParser {

    protected Class<?> clazz;

    public <T> AbstractXmlParser(Class<T> clazz){
        this.clazz = clazz;
    }

    public <T> T parseXml(String xml) {
        return doParseXml(xml);
    }

    public abstract <T> T doParseXml(String paramString);
}