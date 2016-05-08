package org.cgw.splat.constant;

/**
 * 
 * ���ؿͻ��˵����ݸ�ʽ���塣 
 * 
 * @author guowei.cgw 2015��6��14�� ����7:45:56
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
