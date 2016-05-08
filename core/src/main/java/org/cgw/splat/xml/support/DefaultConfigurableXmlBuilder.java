package org.cgw.splat.xml.support;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Iterator;

import org.cgw.splat.xml.annotation.DateAnnotationHandler;
import org.cgw.splat.xml.exception.XmlBuildException;
import org.cgw.splat.xml.processor.XmlBulider;
import org.cgw.splat.xml.utils.Commons;
import org.cgw.splat.xml.utils.Reflects;

public class DefaultConfigurableXmlBuilder extends AbstractXmlBuilder {

    public static final int START_LEVEL = 1;

    public DefaultConfigurableXmlBuilder(){
    }

    public DefaultConfigurableXmlBuilder(XmlBulider.Format format){
        super(format);
    }

    public DefaultConfigurableXmlBuilder(String header, XmlBulider.Format format){
        super(header, format);
    }

    public DefaultConfigurableXmlBuilder(String header){
        super(header);
    }

    public void appendHeader(StringBuffer stringBuffer, Object object) {
        stringBuffer.append(this.header).append("\r\n");
    }

    public void appendBody(StringBuffer stringBuffer, Object object) {
        if (object == null) return;
        try {
            appendRecursion(stringBuffer, object, object.getClass().getSimpleName(), 1);
        } catch (IllegalArgumentException e) {
            throw new XmlBuildException("xml构建异常，此异常是由于采用反射获取属性中的值时发现参数不合法，请仔细检查你传入的对象。", e);
        } catch (IllegalAccessException e) {
            throw new XmlBuildException("xml构建异常，此异常是由于采用反射获取属性中的值时发现访问被禁止，请仔细检查你传入的对象的属性都可以正常访问。", e);
        }
    }

    void appendRecursion(StringBuffer stringBuffer, Object object, String tagName, int level)
                                                                                             throws IllegalArgumentException,
                                                                                             IllegalAccessException {
        if (Commons.isNull(object)) {
            return;
        }
        int currentLevel = level;
        Class clazz = object.getClass();
        if (!Reflects.hasField(clazz)) {
            insertHeadTag(stringBuffer, tagName);
            if (needLine(this.format)) {
                insertLine(stringBuffer);
            }
            insertEndTag(stringBuffer, tagName);
        } else {
            Field[] fields = clazz.getDeclaredFields();
            insertHeadTag(stringBuffer, tagName);
            if (needLine(this.format)) {
                insertLine(stringBuffer);
            }
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);
                Object fieldValue = field.get(object);
                if (fieldValue == null) {
                    continue;
                }
                if ((needLine(this.format)) && (i != 0)) {
                    insertLine(stringBuffer);
                }
                if (needTab(this.format)) {
                    insertTab(stringBuffer, currentLevel);
                }
                if ((Reflects.isComplexType(field.getType())) && (!Iterable.class.isAssignableFrom(field.getType()))) {
                    appendRecursion(stringBuffer, fieldValue, field.getName(), currentLevel + 1);
                } else if (Iterable.class.isAssignableFrom(field.getType())) {
                    int currentArrayLevel = currentLevel + 1;
                    insertHeadTag(stringBuffer, field.getName());
                    if (needLine(this.format)) {
                        insertLine(stringBuffer);
                    }
                    Class iterableGenericType = Reflects.getArrayGenericType(field);
                    int count = 0;
                    for (Iterator localIterator = ((Iterable) fieldValue).iterator(); localIterator.hasNext();) {
                        Object tempObject = localIterator.next();
                        if ((needLine(this.format)) && (count++ != 0)) {
                            insertLine(stringBuffer);
                        }
                        if (needTab(this.format)) {
                            insertTab(stringBuffer, currentArrayLevel);
                        }
                        if (!Reflects.isComplexType(iterableGenericType)) insertSimpleType(stringBuffer,
                                                                                           iterableGenericType.getSimpleName(),
                                                                                           field, tempObject);
                        else {
                            appendRecursion(stringBuffer, tempObject, tempObject.getClass().getSimpleName(),
                                            currentArrayLevel + 1);
                        }
                    }
                    if (needLine(this.format)) {
                        insertLine(stringBuffer);
                    }
                    if (needTab(this.format)) {
                        insertTab(stringBuffer, currentArrayLevel - 1);
                    }
                    insertEndTag(stringBuffer, field.getName());
                } else {
                    insertSimpleType(stringBuffer, field.getName(), field, fieldValue);
                }
            }
            if (needLine(this.format)) {
                insertLine(stringBuffer);
            }
            if (needTab(this.format)) {
                insertTab(stringBuffer, currentLevel - 1);
            }
            insertEndTag(stringBuffer, tagName);
        }
    }

    void insertSimpleType(StringBuffer stringBuffer, String tagName, Field field, Object fieldValue) {
        stringBuffer.append("<").append(tagName).append(">");
        if (field.getType() == Date.class) stringBuffer.append(DateAnnotationHandler.handle(field, fieldValue));
        else {
            stringBuffer.append(fieldValue);
        }
        stringBuffer.append("</").append(tagName).append(">");
    }

    void insertHeadTag(StringBuffer stringBuffer, String tagName) {
        stringBuffer.append("<").append(tagName).append(">");
    }

    void insertEndTag(StringBuffer stringBuffer, String tagName) {
        stringBuffer.append("</").append(tagName).append(">");
    }

    void insertTab(StringBuffer stringBuffer, int number) {
        for (int i = 0; i < number; i++)
            stringBuffer.append("\t");
    }

    void insertLine(StringBuffer stringBuffer) {
        stringBuffer.append("\r\n");
    }

    boolean needLine(XmlBulider.Format format) {
        return (format.equals(XmlBulider.Format.ONLY_LINE)) || (format.equals(XmlBulider.Format.TAB_AND_LINE));
    }

    boolean needTab(XmlBulider.Format format) {
        return format.equals(XmlBulider.Format.TAB_AND_LINE);
    }

    public void prepareBuild() {
    }

    public void finishBuild() {
    }
}