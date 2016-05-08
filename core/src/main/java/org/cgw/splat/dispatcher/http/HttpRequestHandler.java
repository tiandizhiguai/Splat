package org.cgw.splat.dispatcher.http;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.cgw.splat.constant.ResponseDataType;
import org.cgw.splat.dispatcher.RequestHandler;
import org.cgw.splat.dispatcher.RequestInfoHolder;
import org.cgw.splat.exception.SplatException;
import org.cgw.splat.utils.HttpRequestUtils;

/**
 * 解析Http请求信息模型。
 * 
 * @author caoguowei 2015年5月21日 下午7:18:43
 */
public class HttpRequestHandler implements RequestHandler<HttpServletRequest, HttpServletResponse> {

    /**
     * 解析Http请求的服务名称、方法名及返回的数据格式信息。
     * 
     * @param httpRequest
     * @param httpResponse
     * @return
     */
    public RequestInfoHolder handleRequest(HttpServletRequest request, HttpServletResponse response) {
        String uri = HttpRequestUtils.getUri(request);
        RequestInfoHolder handler = new RequestInfoHolder();
        uri = parseResponseDataType(uri, handler);
        String[] uris = StringUtils.split(uri, "/");
        
        if (uris.length == 0) {
            throw new IllegalStateException("Invalid request uri: " + uri);
        }

        handler.setServiceName(uris[uris.length - 2]);
        handler.setMethodName(uris[uris.length - 1]);
        handler.setParameters(parseParameters(request));
        try {
            handler.setOutputStream(response.getOutputStream());
        } catch (IOException e) {
            throw new SplatException(e);
        }

        return handler;
    }
    
    protected Map<String, String> parseParameters(HttpServletRequest request) {
        Enumeration<String> names = request.getParameterNames();
        Map<String, String> valueMap = new HashMap<String, String>();
        for (; names.hasMoreElements();) {
            String name = names.nextElement();
            String value = request.getParameter(name);
            if (StringUtils.isEmpty(value)) {
                continue;
            }
            valueMap.put(name, value);
        }
        return valueMap;
    }
    
    /**
     * 确定向客户端返回的数据格式。
     * @param uri
     * @param handler
     * @return
     */
    protected String parseResponseDataType(String uri, RequestInfoHolder handler) {
        ResponseDataType[] types = ResponseDataType.values();
        for (ResponseDataType type : types) {
            String typeName = type.getValue();
            String extension = "." + typeName;
            if (uri.endsWith(extension)) {
                uri = uri.substring(0, uri.length() - extension.length());
                handler.setResponseDataType(typeName);
                return uri;
            }
        }
        
        //Set the default response data type: json.
        if (StringUtils.isEmpty(handler.getResponseDataType())) {
            throw new SplatException("Unsupported  respose data type");
            // 以后可以考虑提供默认的数据类型支持
            // handler.setResponseDataType(ResponseDataType.JSON.getValue());
        }
        
        // This should also handle cases such as /foo/bar-1.0/description. It is tricky to
        // distinquish /foo/bar-1.0 but perhaps adding a numeric check in the future could
        // work
        int index = uri.lastIndexOf('.');
        if (index == -1 || uri.indexOf('/', index) >= 0) {
            return uri;
        }
        
        throw new IllegalStateException("Invalid request uri : " + uri);
    }
}