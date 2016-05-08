package org.cgw.splat.xml.container;

public abstract interface Container {

    public abstract String[] getXmlArray();

    public abstract <T> T[] getObjectArray();

    public abstract String getXml();

    public abstract <T> T getObject();

    public abstract String getXml(int paramInt);

    public abstract <T> T getObject(int paramInt);

    public abstract boolean add(String paramString);

    public abstract <T> boolean add(T paramT);

    public abstract boolean contains(String paramString);

    public abstract <T> boolean contains(T paramT);

    public abstract boolean remove(String paramString);

    public abstract <T> boolean remove(T paramT);

    public abstract boolean containsAll(String[] paramArrayOfString);

    public abstract <T> boolean containsAll(T[] paramArrayOfT);

    public abstract boolean addAll(String[] paramArrayOfString);

    public abstract <T> boolean addAll(T[] paramArrayOfT);

    public abstract boolean removeAll(String[] paramArrayOfString);

    public abstract <T> boolean removeAll(T[] paramArrayOfT);

    public abstract boolean retainAll(String[] paramArrayOfString);

    public abstract <T> boolean retainAll(T[] paramArrayOfT);

    public abstract boolean isEmpty();

    public abstract int size();

    public abstract <T> void trim();

    public abstract void clear();
}