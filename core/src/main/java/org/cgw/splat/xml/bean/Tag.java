package org.cgw.splat.xml.bean;

import java.util.List;

public abstract interface Tag<T> {

    public abstract String getName();

    public abstract T getValue();

    public abstract <E> List<Tag<E>> getTagList();
}