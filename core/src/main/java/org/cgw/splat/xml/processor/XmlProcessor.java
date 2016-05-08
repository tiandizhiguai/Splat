package org.cgw.splat.xml.processor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public abstract interface XmlProcessor {

    public static final DateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",
                                                                              Locale.ENGLISH);
}