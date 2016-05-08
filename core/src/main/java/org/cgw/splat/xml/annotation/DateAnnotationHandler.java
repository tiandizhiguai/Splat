package org.cgw.splat.xml.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.cgw.splat.xml.processor.XmlProcessor;
import org.cgw.splat.xml.utils.Annotations;

public class DateAnnotationHandler {

    public static String handle(Field field, Object fieldValue) {
        if (field.getType() != Date.class) {
            return fieldValue.toString();
        }
        Annotation annotation = Annotations.getAnnotation(field, ADate.class);
        if ((annotation != null) && ((annotation instanceof ADate))) {
            ADate dateAnnotation = (ADate) annotation;
            String format = dateAnnotation.format();
            if (format != null) {
                return new SimpleDateFormat(format).format((Date) fieldValue);
            }
        }
        return fieldValue.toString();
    }

    public static Date handle(Field field, String fieldValue) throws ParseException {
        if (field.getType() != Date.class) {
            return new SimpleDateFormat().parse(fieldValue);
        }
        Annotation annotation = Annotations.getAnnotation(field, ADate.class);
        if ((annotation != null) && ((annotation instanceof ADate))) {
            ADate dateAnnotation = (ADate) annotation;
            String format = dateAnnotation.format();
            if (format != null) {
                return new SimpleDateFormat(format).parse(fieldValue);
            }
        }
        return XmlProcessor.DEFAULT_DATE_FORMAT.parse(fieldValue);
    }
}
