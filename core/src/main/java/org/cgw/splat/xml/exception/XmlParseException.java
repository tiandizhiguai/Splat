package org.cgw.splat.xml.exception;

public class XmlParseException extends RuntimeException {

    private static final long serialVersionUID = 2L;

    public XmlParseException(){
    }

    public XmlParseException(String message, Throwable cause){
        super(message, cause);
    }

    public XmlParseException(String message){
        super(message);
    }

    public XmlParseException(Throwable cause){
        super(cause);
    }
}