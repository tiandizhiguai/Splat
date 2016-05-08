package org.cgw.splat.dispatcher;


/***
 * 解析请求信息模型。
 * 
 * @author guowei.cgw 2015年5月21日 下午7:17:30
 */
public interface RequestHandler<T1, T2> {

    public RequestInfoHolder handleRequest(T1 request, T2 response);
    
}
