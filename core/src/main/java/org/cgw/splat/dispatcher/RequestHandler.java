package org.cgw.splat.dispatcher;


/***
 * ����������Ϣģ�͡�
 * 
 * @author guowei.cgw 2015��5��21�� ����7:17:30
 */
public interface RequestHandler<T1, T2> {

    public RequestInfoHolder handleRequest(T1 request, T2 response);
    
}
