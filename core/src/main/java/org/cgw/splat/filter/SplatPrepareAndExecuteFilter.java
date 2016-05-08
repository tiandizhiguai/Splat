package org.cgw.splat.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cgw.splat.dispatcher.RequestHandler;
import org.cgw.splat.dispatcher.RequestInfoHolder;
import org.cgw.splat.dispatcher.http.HttpRequestHandler;

/**
 * ��ܵ���ڣ��ṩ�������ܣ�1.��ʼ�����������Ϣ������� 2.�����û�������
 * 
 * @author caoguowei 2015��6��8�� ����2:57:10
 */
public class SplatPrepareAndExecuteFilter extends AbstractPrepareAndExecuteEntrance implements Filter {

    /**
     * ��ʼ��������Ϣ��
     */
    public void init(FilterConfig filterConfig) throws ServletException {

        Class<?>[] argTypes = new Class<?>[] { ServletContext.class };
        Object[] argValues = new Object[] { filterConfig.getServletContext() };

        // ��ʼ����ܵ���������
        initConfiguration(argTypes, argValues);
    }

    /**
     * �����û�����
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
                                                                                             ServletException {
        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;

            // 1.����request������Ϣ
            RequestHandler<HttpServletRequest, HttpServletResponse> requestHandler = new HttpRequestHandler();
            RequestInfoHolder requestInfoHolder = requestHandler.handleRequest(httpRequest, httpResponse);

            // 2.�����û�����
            doExecute(requestInfoHolder);
        } else {
            chain.doFilter(request, response);
        }
    }

    public void destroy() {

    }
}