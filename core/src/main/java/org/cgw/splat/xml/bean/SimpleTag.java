package org.cgw.splat.xml.bean;

import java.util.ArrayList;
import java.util.List;

public class SimpleTag<T> implements Tag<T> {

    protected final String name;
    protected T            value;
    protected List<Tag<T>> tagList = new ArrayList<Tag<T>>();

    public SimpleTag(String name){
        this.name = name;
    }

    public SimpleTag(String name, T value){
        this.name = name;
        this.value = value;
    }

    public SimpleTag(String name, List<Tag<T>> tagList){
        this.name = name;
        this.tagList = tagList;
    }

    public SimpleTag(String name, T value, List<Tag<T>> tagList){
        this.name = name;
        this.value = value;
        this.tagList = tagList;
    }

    public String getName() {
        return this.name;
    }

    public T getValue() {
        return this.value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public List<Tag<T>> getTagList() {
        return this.tagList;
    }

    public void setTagList(List<Tag<T>> tagList) {
        this.tagList = tagList;
    }
}