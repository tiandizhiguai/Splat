package org.cgw.splat.xml.processor;

public abstract interface XmlBulider extends XmlProcessor {

    public abstract String buildXml(Object paramObject);

    public abstract String getHeader();

    public abstract void setHeader(String paramString);

    public abstract Format getFormat();

    public abstract void setFormat(Format paramFormat);

    public static enum Format {
        NO_SPACE, ONLY_LINE, TAB_AND_LINE;
    }
}