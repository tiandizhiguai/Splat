package org.cgw.splat.xml.support;

import org.cgw.splat.xml.processor.XmlBulider;

public abstract class AbstractXmlBuilder implements XmlBulider {

    public static final String            DEFAULT_XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    public static final String            TAG_HEAD_PREFIX    = "<";
    public static final String            TAG_END_PREFIX     = "</";
    public static final String            TAG_SUFFIX         = ">";
    public static final String            LINE               = "\r\n";
    public static final String            TAB                = "\t";
    public static final XmlBulider.Format DEFAULT_FORMAT     = XmlBulider.Format.NO_SPACE;

    protected String                      header             = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

    protected XmlBulider.Format           format             = DEFAULT_FORMAT;

    protected final StringBuffer          xmlStringBuffer    = new StringBuffer();

    public AbstractXmlBuilder(){
    }

    public AbstractXmlBuilder(XmlBulider.Format format){
        this.format = format;
    }

    public AbstractXmlBuilder(String header){
        this.header = header;
    }

    public AbstractXmlBuilder(String header, XmlBulider.Format format){
        this.header = header;
        this.format = format;
    }

    public String buildXml(Object object) {
        prepareBuild();
        appendHeader(this.xmlStringBuffer, object);
        appendBody(this.xmlStringBuffer, object);
        finishBuild();
        return this.xmlStringBuffer.toString();
    }

    public String getHeader() {
        return this.header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public XmlBulider.Format getFormat() {
        return this.format;
    }

    public void setFormat(XmlBulider.Format format) {
        this.format = format;
    }

    public abstract void prepareBuild();

    public abstract void appendHeader(StringBuffer paramStringBuffer, Object paramObject);

    public abstract void appendBody(StringBuffer paramStringBuffer, Object paramObject);

    public abstract void finishBuild();
}