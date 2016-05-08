package org.cgw.splat.xml.support;


public class DefaultConfigurableContainer extends AbstractConfigurableContainer {

    public <T> DefaultConfigurableContainer(Class<T> clazz){
        super(clazz);

        this.xmlBulider = new DefaultConfigurableXmlBuilder();
        this.xmlParser = new DomConfigurableXmlParser(this.clazz);
    }

    public <T> DefaultConfigurableContainer(int length, Class<T> clazz){
        super(length, clazz);

        this.xmlBulider = new DefaultConfigurableXmlBuilder();
        this.xmlParser = new DomConfigurableXmlParser(this.clazz);
    }

    public <T> DefaultConfigurableContainer(String xml, Class<T> clazz){
        super(xml, clazz);

        this.xmlBulider = new DefaultConfigurableXmlBuilder();
        this.xmlParser = new DomConfigurableXmlParser(this.clazz);
    }

    public <T> DefaultConfigurableContainer(T object, Class<T> clazz){
        super(object, clazz);

        this.xmlBulider = new DefaultConfigurableXmlBuilder();
        this.xmlParser = new DomConfigurableXmlParser(this.clazz);
    }

    public <T> DefaultConfigurableContainer(String[] xmlArray, Class<T> clazz){
        super(xmlArray, clazz);

        this.xmlBulider = new DefaultConfigurableXmlBuilder();
        this.xmlParser = new DomConfigurableXmlParser(this.clazz);
    }

    public <T> DefaultConfigurableContainer(T[] objectArray, Class<T> clazz){
        super(objectArray, clazz);

        this.xmlBulider = new DefaultConfigurableXmlBuilder();
        this.xmlParser = new DomConfigurableXmlParser(this.clazz);
    }

    public <T> T convertXml2Object(String xml) {
        return this.xmlParser.parseXml(xml);
    }

    public <T> String convertObject2Xml(T object) {
        return this.xmlBulider.buildXml(object);
    }
}