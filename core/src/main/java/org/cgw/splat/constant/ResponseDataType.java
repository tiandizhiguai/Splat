package org.cgw.splat.constant;

/**
 * 
 * 返回客户端的数据格式定义。 
 * 
 * @author guowei.cgw 2015年6月14日 下午7:45:56
 */
public enum ResponseDataType {
    
    JSON("json"),
    XML("xml");
    
    private String value;

    private ResponseDataType(String value){
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
}
