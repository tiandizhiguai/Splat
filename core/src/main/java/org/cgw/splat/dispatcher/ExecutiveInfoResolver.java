package org.cgw.splat.dispatcher;

import org.cgw.splat.config.Configuration;

/**
 * ����Ŀ������Ŀ�귽���ĳ���������
 * 
 * @author caoguowei 2015��5��23�� ����2:21:37
 */
public interface ExecutiveInfoResolver {

    /**
     * ����Ŀ������������Ŀ�귽����
     * 
     * @param httpRequest
     * @param context
     * @return
     */
    public ExecutiveInfoHolder resolveServiceAndMethod(RequestInfoHolder requestInfoHolder, Configuration configuration);

}