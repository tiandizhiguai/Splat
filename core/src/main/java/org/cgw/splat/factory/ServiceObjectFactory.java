package org.cgw.splat.factory;

/**
 * ����ִ�з������ĳ��󹤳��� <br/>
 * ���������Ҫʵ���Լ��Ĳ��������Ҫʵ�ִ˽ӿڡ�
 * 
 * @author guowei.cgw 2015��5��31�� ����3:46:19
 */
public interface ServiceObjectFactory {
    
    /**
     * ��ȡServiceObjectFactory������
     * 
     * @return ServiceObjectFactory������
     */
    public String getName();

    /**
     * �����������
     * 
     * @param serviceName
     * @return �������
     */
    public Object createServiceObject(String serviceName);

}
