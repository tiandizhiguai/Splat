package org.cgw.splat.xml.exception;

public class XmlBuildException extends RuntimeException {

    private static final long serialVersionUID = 3L;

    public XmlBuildException(){
    }

    public XmlBuildException(String message, Throwable cause){
        super(message, cause);
    }

    public XmlBuildException(String message){
        super(message);
    }

    public XmlBuildException(Throwable cause){
        super(cause);
    }
}