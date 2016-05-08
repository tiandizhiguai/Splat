package org.cgw.splat.xml.support;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.cgw.splat.xml.annotation.DateAnnotationHandler;
import org.cgw.splat.xml.exception.XmlParseException;
import org.cgw.splat.xml.utils.Commons;
import org.cgw.splat.xml.utils.Reflects;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DomConfigurableXmlParser extends AbstractXmlParser {

    public <T> DomConfigurableXmlParser(Class<T> clazz){
        super(clazz);
    }

    public <T> T doParseXml(String xml) {
        DocumentBuilderFactory domfac = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder dombuilder = domfac.newDocumentBuilder();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(xml.getBytes());
            Document doc = dombuilder.parse(inputStream);
            Element root = doc.getDocumentElement();
            return (T) parseRecursion(this.clazz, root);
        } catch (ParserConfigurationException e) {
            throw new XmlParseException("�ĵ�����������ʧ�ܣ�δ�ҵ��ﵽ����Ҫ����ĵ�������", e);
        } catch (FileNotFoundException e) {
            throw new XmlParseException("δ�ҵ�xml��", e);
        } catch (SAXException e) {
            throw new XmlParseException("DOM�������̷�������", e);
        } catch (IOException e) {
            throw new XmlParseException("IO���̷�������", e);
        } catch (IllegalArgumentException e) {
            throw new XmlParseException("�����xml����Ϊ��", e);
        } catch (SecurityException e) {
            throw new XmlParseException("�������͵Ĵ�String�����Ĺ��췽�����ܾ�����", e);
        } catch (IllegalAccessException e) {
            throw new XmlParseException("��������ֵʱ���ܾ�����", e);
        } catch (InstantiationException e) {
            throw new XmlParseException("���Բ���Ĭ���޲ι��췽����ʼ��ʵ��ʱʧ��", e);
        } catch (ParseException e) {
            throw new XmlParseException("�������ڴ���", e);
        } catch (NoSuchMethodException e) {
            throw new XmlParseException("�������͵Ĵ�String�����Ĺ��췽��δ�ҵ�", e);
        } catch (InvocationTargetException e) {
            throw new XmlParseException("���صĹ��췽������", e);
        }
    }

    private <T> T parseRecursion(Class<T> clazz, Node root) throws IllegalArgumentException, IllegalAccessException,
                                                   InstantiationException, ParseException, SecurityException,
                                                   NoSuchMethodException, InvocationTargetException {
        if ((Commons.isNull(clazz)) || (!Reflects.isComplexType(clazz))) {
            return null;
        }
        T object = Reflects.getInstance(clazz);
        if (!Reflects.hasField(clazz)) {
            return object;
        }
        NodeList nodeList = root.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() != 1) {
                continue;
            }
            String nodeName = node.getNodeName();
            String nodeValue = node.getTextContent();
            if ((nodeName == null) || (nodeValue == null)) {
                continue;
            }
            Field field = null;
            try {
                field = clazz.getDeclaredField(nodeName);
            } catch (NoSuchFieldException e) {
                continue;
            }
            field.setAccessible(true);
            if ((!Reflects.isComplexType(field.getType())) && (nodeValue.trim().length() > 0)) {
                Constructor constructor = field.getType().getConstructor(new Class[] { String.class });
                if (Date.class.isAssignableFrom(field.getType())) {
                    field.set(object, DateAnnotationHandler.handle(field, nodeValue));
                } else if (constructor != null) {
                    constructor.setAccessible(true);
                    field.set(object, constructor.newInstance(new Object[] { nodeValue }));
                } else if ((field.getType() == Character.TYPE) || (field.getType() == Character.class)) {
                    field.set(object, Character.valueOf(nodeValue.charAt(0)));
                }
            } else if ((!Reflects.isComplexType(field.getType())) && (nodeValue.trim().length() == 0)) {
                field.set(object, field.getType().newInstance());
            } else if ((Reflects.isComplexType(field.getType())) && (Iterable.class.isAssignableFrom(field.getType()))) {
                Class iterableGenericType = Reflects.getArrayGenericType(field);
                Iterable iterable = null;
                if (List.class.isAssignableFrom(field.getType())) {
                    iterable = new ArrayList();
                }
                if (Set.class.isAssignableFrom(field.getType())) {
                    iterable = new HashSet();
                }
                NodeList iterableNodeList = node.getChildNodes();
                for (int j = 0; j < iterableNodeList.getLength(); j++) {
                    Node iterableNode = iterableNodeList.item(j);
                    if (iterableNode.getNodeType() != 1) {
                        continue;
                    }
                    if (List.class.isAssignableFrom(field.getType())) {
                        ((List) iterable).add(parseRecursion(iterableGenericType, iterableNode));
                    }
                    if (Set.class.isAssignableFrom(field.getType())) {
                        ((Set) iterable).add(parseRecursion(iterableGenericType, iterableNode));
                    }
                }
                field.set(object, iterable);
            } else {
                field.set(object, parseRecursion(field.getType(), node));
            }
        }
        return object;
    }
}