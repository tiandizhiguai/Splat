package org.cgw.splat.exception;

/**
 * ��ܵ�����ʱ�쳣��
 * 
 * @author guowei.cgw 2015��6��12�� ����10:49:04
 */
public class SplatException extends RuntimeException {

    private static final long serialVersionUID = 3085226054555400967L;

    public SplatException(){
        super();
    }

    public SplatException(String message){
        super(message);
    }

    public SplatException(Throwable cause){
        super(cause);
    }
}