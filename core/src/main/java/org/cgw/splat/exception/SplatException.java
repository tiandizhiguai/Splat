package org.cgw.splat.exception;

/**
 * 框架的运行时异常。
 * 
 * @author guowei.cgw 2015年6月12日 上午10:49:04
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