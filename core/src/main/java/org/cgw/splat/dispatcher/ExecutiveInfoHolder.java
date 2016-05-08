package org.cgw.splat.dispatcher;

import java.lang.reflect.Method;

public class ExecutiveInfoHolder {

    private Object            targetService;
    private Method            targetMethod;
    private Object[]          methodParameters;
    private RequestInfoHolder requestInfoHolder;

    public RequestInfoHolder getRequestInfoHolder() {
        return requestInfoHolder;
    }

    public void setRequestInfoHolder(RequestInfoHolder requestInfoHolder) {
        this.requestInfoHolder = requestInfoHolder;
    }

    public Object getTargetService() {
        return targetService;
    }

    public void setTargetService(Object targetService) {
        this.targetService = targetService;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public void setTargetMethod(Method targetMethod) {
        this.targetMethod = targetMethod;
    }

    public Object[] getMethodParameters() {
        return methodParameters;
    }

    public void setMethodParameters(Object[] methodParameters) {
        this.methodParameters = methodParameters;
    }
}