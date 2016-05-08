package org.cgw.splat.dispatcher;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.cgw.splat.constant.ResponseDataType;
import org.cgw.splat.dispatcher.result.DataResponseHandler;
import org.cgw.splat.dispatcher.result.JsonDataResponseHandler;
import org.cgw.splat.exception.SplatException;
import org.cgw.splat.xml.container.Container;
import org.cgw.splat.xml.support.DefaultConfigurableContainer;

/**
 * Http请求处理器。
 * 
 * @author caoguowei 2015年6月12日 下午6:57:35
 */
public class DefaultServiceExecutor implements ServiceExecutor {

    public boolean execute(ExecutiveInfoHolder beanAndMethodHolder) {

        Object targetService = beanAndMethodHolder.getTargetService();
        Method targetMethod = beanAndMethodHolder.getTargetMethod();
        Object[] methodParameters = beanAndMethodHolder.getMethodParameters();
        RequestInfoHolder requestInfoHolder = beanAndMethodHolder.getRequestInfoHolder();
        String dataType = requestInfoHolder.getResponseDataType();

        // 1.执行目标方法
        Object context = null;
        try {
            context = targetMethod.invoke(targetService, methodParameters);
        } catch (IllegalAccessException e) {
            throw new SplatException(e);
        } catch (InvocationTargetException e) {
            throw new SplatException(e);
        } catch (IllegalArgumentException e) {
            throw new SplatException(e);
        }

        // 2.向客户端写数据
        OutputStream outputStream = requestInfoHolder.getOutputStream();
        writeContext(outputStream, context, dataType);
        return true;
    }

    private void writeContext(OutputStream outputStream, Object context, String dataType) {
        DataResponseHandler dataResponseHandler;
        String formatContext = null;
        String charset = "GBK";
        if (ResponseDataType.JSON.getValue().equals(dataType)) {
            dataResponseHandler = new JsonDataResponseHandler();
            formatContext = dataResponseHandler.handleData(context);
        } else if (ResponseDataType.XML.getValue().equals(dataType)) {
            Container container = new DefaultConfigurableContainer(context.getClass());
            container.add(context);
            formatContext = container.getXml();
            charset = "UTF-8";
        }

        doWrite(outputStream, formatContext, charset);
    }

    private void doWrite(OutputStream outputStream, String context, String charset) {
        try {
            outputStream.write(context.getBytes(charset));
        } catch (IOException e) {
            throw new SplatException(e);
        }
    }
}