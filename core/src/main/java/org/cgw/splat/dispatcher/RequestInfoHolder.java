package org.cgw.splat.dispatcher;

import java.io.OutputStream;
import java.util.Map;

/**
 * Http������Ϣģ��
 * 
 * @author guowei.cgw 2015��5��21�� ����7:38:06
 */
public class RequestInfoHolder {

    private String              serviceName;
    private String              methodName;
    private String              responseDataType;
    private Map<String, String> parameters;
    private OutputStream        outputStream;

    // private String inputCharacterEncoding;
    // private String outputCharacterEncoding;

    // public String getInputCharacterEncoding() {
    // return inputCharacterEncoding;
    // }
    //
    // public void setInputCharacterEncoding(String inputCharacterEncoding) {
    // this.inputCharacterEncoding = inputCharacterEncoding;
    // }
    //
    // public String getOutputCharacterEncoding() {
    // return outputCharacterEncoding;
    // }
    //
    // public void setOutputCharacterEncoding(String outputCharacterEncoding) {
    // this.outputCharacterEncoding = outputCharacterEncoding;
    // }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getResponseDataType() {
        return responseDataType;
    }

    public void setResponseDataType(String responseDataType) {
        this.responseDataType = responseDataType;
    }
}