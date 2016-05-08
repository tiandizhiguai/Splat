package org.cgw.splat.xml.support;

import org.cgw.splat.xml.container.Container;
import org.cgw.splat.xml.utils.Commons;

public abstract class AbstractContainer implements Container {

    public static final int DEFAULT_LENGTH        = 10;
    public static final int DEFAULT_EXPAND_LENGTH = 5;
    public static final int DEFAULT_EXPAND_TIMES  = 1;
    protected String[]      xmlArray;
    protected Object[]      objectArray;
    protected int           size;
    protected Class<?>      clazz;

    public <T> AbstractContainer(Class<T> clazz){
        init(this.size, 10, clazz);
    }

    public <T> AbstractContainer(int length, Class<T> clazz){
        if (length <= 0) {
            length = 10;
        }
        init(this.size, length, clazz);
    }

    public <T> AbstractContainer(String xml, Class<T> clazz){
        this(new String[] { xml }, clazz);
    }

    public <T> AbstractContainer(T object, Class<T> clazz){
        init(1, 1, clazz);
        this.objectArray[0] = object;
        this.xmlArray[0] = convertObject2Xml(object);
    }

    public <T> AbstractContainer(String[] xmlArray, Class<T> clazz){
        if (Commons.isEmpty(xmlArray)) {
            return;
        }
        this.xmlArray = xmlArray;
        this.size = xmlArray.length;
        this.objectArray = new Object[xmlArray.length];
        for (int i = 0; i < xmlArray.length; i++) {
            Object object = convertXml2Object(xmlArray[i]);
            this.objectArray[i] = object;
        }
    }

    public <T> AbstractContainer(T[] objectArray, Class<T> clazz){
        if (Commons.isEmpty(objectArray)) {
            return;
        }
        this.objectArray = objectArray;
        this.size = objectArray.length;
        this.xmlArray = new String[objectArray.length];
        for (int i = 0; i < objectArray.length; i++) {
            String xml = convertObject2Xml(objectArray[i]);
            this.xmlArray[i] = xml;
        }
    }

    final <T> void init(int size, int length, Class<T> clazz) {
        this.clazz = clazz;
        this.size = size;
        this.objectArray = new Object[length];
        this.xmlArray = new String[length];
    }

    public String[] getXmlArray() {
        return this.xmlArray;
    }

    public <T> T[] getObjectArray() {
        return (T[]) this.objectArray;
    }

    public String getXml() {
        if (this.size > 0) {
            return this.xmlArray[0];
        }
        return null;
    }

    public <T> T getObject() {
        if (this.size > 0) {
            return (T) this.objectArray[0];
        }
        return null;
    }

    public String getXml(int index) {
        return this.xmlArray[index];
    }

    public <T> T getObject(int index) {
        return (T) this.objectArray[index];
    }

    public boolean add(String xml) {
        if (Commons.isNull(xml)) {
            return false;
        }
        if (this.xmlArray.length == this.size) {
            expand(1);
        }
        this.xmlArray[this.size] = xml;
        this.objectArray[this.size] = convertXml2Object(xml);
        this.size += 1;
        return true;
    }

    public <T> boolean add(T object) {
        if (Commons.isNull(object)) {
            return false;
        }
        if (this.objectArray.length == this.size) {
            expand(1);
        }
        this.objectArray[this.size] = object;
        this.xmlArray[this.size] = convertObject2Xml(object);
        this.size += 1;
        return true;
    }

    public boolean contains(String xml) {
        for (int i = 0; i < this.xmlArray.length; i++) {
            if (((xml == null) && (this.xmlArray[i] == null)) || (this.xmlArray[i].equals(xml))) {
                return true;
            }
        }
        return false;
    }

    public <T> boolean contains(T object) {
        for (int i = 0; i < this.objectArray.length; i++) {
            if (((object == null) && (this.objectArray[i] == null)) || (this.objectArray[i].equals(object))) {
                return true;
            }
        }
        return false;
    }

    public boolean remove(String xml) {
        if (Commons.isNull(xml)) {
            trim();
            return true;
        }
        for (int i = 0; i < this.xmlArray.length; i++) {
            if (this.xmlArray[i].equals(xml)) {
                this.xmlArray[i] = null;
                this.objectArray[i] = null;
            }
        }
        trim();
        return true;
    }

    public <T> boolean remove(T object) {
        if (Commons.isNull(object)) {
            trim();
            return true;
        }
        for (int i = 0; i < this.objectArray.length; i++) {
            if (this.objectArray[i].equals(object)) {
                this.objectArray[i] = null;
                this.xmlArray[i] = null;
            }
        }
        remove(convertObject2Xml(object));
        trim();
        return true;
    }

    public boolean containsAll(String[] xmlArray) {
        if (xmlArray.length > this.xmlArray.length) {
            return false;
        }
        for (int i = 0; i < xmlArray.length; i++) {
            if (!contains(xmlArray[i])) {
                return false;
            }
        }
        return true;
    }

    public <T> boolean containsAll(T[] objectArray) {
        if (objectArray.length > this.objectArray.length) {
            return false;
        }
        for (int i = 0; i < objectArray.length; i++) {
            if (!contains(objectArray[i])) {
                return false;
            }
        }
        return true;
    }

    public boolean addAll(String[] xmlArray) {
        if (Commons.isEmpty(xmlArray)) {
            return true;
        }
        int times = xmlArray.length % 5 + 1;
        expand(times);
        for (int i = 0; i < xmlArray.length; i++) {
            this.xmlArray[(this.size++)] = xmlArray[i];
            this.objectArray[(this.size++)] = convertXml2Object(xmlArray[i]);
        }
        return true;
    }

    public <T> boolean addAll(T[] objectArray) {
        if (Commons.isEmpty(objectArray)) {
            return true;
        }
        int times = objectArray.length % 5 + 1;
        expand(times);
        for (int i = 0; i < objectArray.length; i++) {
            this.objectArray[(this.size++)] = objectArray[i];
            this.xmlArray[(this.size++)] = convertObject2Xml(objectArray[i]);
        }
        return true;
    }

    public boolean removeAll(String[] xmlArray) {
        if (Commons.isEmpty(xmlArray)) {
            return true;
        }
        for (int i = 0; i < xmlArray.length; i++) {
            if (!Commons.isNull(xmlArray[i])) {
                for (int j = 0; j < this.xmlArray.length; j++) {
                    if (this.xmlArray[j].equals(xmlArray[i])) {
                        this.xmlArray[j] = null;
                        this.objectArray[j] = null;
                    }
                }
            }
        }
        trim();
        return true;
    }

    public <T> boolean removeAll(T[] objectArray) {
        if (Commons.isEmpty(objectArray)) {
            return true;
        }
        for (int i = 0; i < objectArray.length; i++) {
            if (!Commons.isNull(objectArray[i])) {
                for (int j = 0; j < this.objectArray.length; j++) {
                    if (this.objectArray[j].equals(objectArray[i])) {
                        this.objectArray[j] = null;
                        this.xmlArray[j] = null;
                    }
                }
            }
        }
        trim();
        return true;
    }

    public boolean retainAll(String[] xmlArray) {
        for (int i = 0; i < this.xmlArray.length; i++) {
            boolean contains = false;
            for (int j = 0; j < xmlArray.length; j++) {
                if (xmlArray[j].equals(this.xmlArray[i])) {
                    contains = true;
                    break;
                }
            }
            if (!contains) {
                remove(this.xmlArray[i]);
            }
        }
        trim();
        return true;
    }

    public <T> boolean retainAll(T[] objectArray) {
        for (int i = 0; i < this.objectArray.length; i++) {
            boolean contains = false;
            for (int j = 0; j < objectArray.length; j++) {
                if (objectArray[j].equals(this.objectArray[i])) {
                    contains = true;
                    break;
                }
            }
            if (!contains) {
                remove(this.objectArray[i]);
            }
        }
        trim();
        return true;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }

    public final <T> void trim() {
        if (this.size == 0) {
            return;
        }
        int tempSize = 0;
        String[] tempXmlArray = new String[this.xmlArray.length];
        Object[] tempObjectArray = new Object[this.objectArray.length];
        int i = 0;
        for (int j = 0; i < this.xmlArray.length; i++) {
            if (!Commons.isNull(this.xmlArray[i])) {
                tempXmlArray[(j++)] = this.xmlArray[i];
                tempSize++;
            }
        }
        i = 0;
        for (int j = 0; i < this.objectArray.length; i++) {
            if (!Commons.isNull(this.objectArray[i])) {
                tempObjectArray[(j++)] = this.objectArray[i];
            }
        }
        this.xmlArray = tempXmlArray;
        this.objectArray = tempObjectArray;
        this.size = tempSize;
    }

    public void clear() {
        init(0, 10, null);
    }

    final <T> void expand(int times) {
        String[] tempXmlArray = new String[this.size + 5 * times];
        Object[] tempObjectArray = new Object[this.size + 5 * times];
        System.arraycopy(this.xmlArray, 0, tempXmlArray, 0, this.xmlArray.length);
        System.arraycopy(this.objectArray, 0, tempObjectArray, 0, this.objectArray.length);
        this.xmlArray = tempXmlArray;
        this.objectArray = tempObjectArray;
    }

    public abstract <T> T convertXml2Object(String paramString);

    public abstract <T> String convertObject2Xml(T paramT);
}
