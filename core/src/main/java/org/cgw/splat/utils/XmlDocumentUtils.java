package org.cgw.splat.utils;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.cgw.splat.exception.SplatException;

public class XmlDocumentUtils {

    public static DocumentBuilder buildDocumentBuilder() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            return dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new SplatException(e);
        }
    }
}
