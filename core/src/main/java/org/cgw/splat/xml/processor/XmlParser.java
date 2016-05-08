package org.cgw.splat.xml.processor;

public abstract interface XmlParser extends XmlProcessor {

    public abstract <T> T parseXml(String paramString);
}