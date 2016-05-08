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
 * 框架的入口，提供两个功能：1.初始化框架配置信息和组件； 2.处理用户的请求。
 * 
 * @author caoguowei 2015年6月8日 下午2:57:10
 */
public class SplatPrepareAndExecuteFilter extends AbstractPrepareAndExecuteEntrance implements Filter {

    /**
     * 初始化配置信息。
     */
    public void init(FilterConfig filterConfig) throws ServletException {

        Class<?>[] argTypes = new Class<?>[] { ServletContext.class };
        Object[] argValues = new Object[] { filterConfig.getServletContext() };

        // 初始化框架的配置数据
        initConfiguration(argTypes, argValues);
    }

    /**
     * 处理用户请求。
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
                                                                                             ServletException {
        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;

            // 1.解析request请求信息
            RequestHandler<HttpServletRequest, HttpServletResponse> requestHandler = new HttpRequestHandler();
            RequestInfoHolder requestInfoHolder = requestHandler.handleRequest(httpRequest, httpResponse);

            // 2.处理用户请求
            doExecute(requestInfoHolder);
        } else {
            chain.doFilter(request, response);
        }
    }

    public void destroy() {

    }
}