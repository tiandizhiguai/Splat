package org.cgw.splat.config;

/**
 * 配置文件常量配置项模型。
 * 
 * @author guowei.cgw 2015年6月5日 下午3:43:12
 */
public class ConfigurationItem {

    private String name;
    private String value;

    public ConfigurationItem(){

    }

    public ConfigurationItem(String name, String value){
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}