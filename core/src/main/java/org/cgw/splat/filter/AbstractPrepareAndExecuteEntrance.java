package org.cgw.splat.filter;

import org.cgw.splat.config.Configuration;
import org.cgw.splat.config.ConfigurationResolver;
import org.cgw.splat.dispatcher.DefaultServiceExecutor;
import org.cgw.splat.dispatcher.ExecutiveInfoHolder;
import org.cgw.splat.dispatcher.ExecutiveInfoResolver;
import org.cgw.splat.dispatcher.RequestInfoHolder;
import org.cgw.splat.dispatcher.ServiceExecutor;
import org.cgw.splat.dispatcher.http.DefaultExecutiveInfoResolver;

/**
 * ��ܵ���ڣ��ṩ�������ܣ�1.��ʼ�����������Ϣ������� 2.�����û�������
 * 
 * @author guowei.cgw 2015��6��8�� ����2:57:10
 */
public abstract class AbstractPrepareAndExecuteEntrance {

    /**
     * ������Ϣ
     */
    private Configuration configuration;

    /**
     * ��ʼ��������Ϣ��
     */
    public void initConfiguration(Class<?>[] argTypes, Object[] argValues) {
        ConfigurationResolver cf = new ConfigurationResolver();
        this.configuration = cf.resolveConfiguration(argTypes, argValues);
    }

    /**
     * �����û�����
     */
    public void doExecute(RequestInfoHolder requestInfoHolder) {

        // 1.��ȡĿ������Ŀ�귽��
        ExecutiveInfoResolver resolver = new DefaultExecutiveInfoResolver();
        ExecutiveInfoHolder beanAndMethodHolder = resolver.resolveServiceAndMethod(requestInfoHolder, configuration);

        // 2.ִ�з���
        ServiceExecutor serviceExecutor = new DefaultServiceExecutor();
        serviceExecutor.execute(beanAndMethodHolder);
    }

}
