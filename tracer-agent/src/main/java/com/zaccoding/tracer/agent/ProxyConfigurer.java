package com.zaccoding.tracer.agent;

import com.zaccoding.tracer.util.StringInvokeUtil;

import java.util.List;

/**
 * @author zacconding
 * @Date 2018-01-21
 * @GitHub : https://github.com/zacscoding
 */
public class ProxyConfigurer {
    private List<ClassProxy> classProxies;

    public List<ClassProxy> getClasses() {
        return classProxies;
    }

    public void setClasses(List<ClassProxy> classProxies) {
        this.classProxies = classProxies;
    }

    public ClassProxy getClassProxy(String className) {
        if(classProxies == null || classProxies.size() == 0) {
            return null;
        }

        for(ClassProxy classProxy : classProxies) {
            if(StringInvokeUtil.isMatches(classProxy.getClassName(), className, classProxy.invokeType, classProxy.isInvoker)) {
                return classProxy;
            }
        }

        return null;
    }


    public static class ClassProxy {
        private String className;
        private StringInvokeUtil.InvokeType invokeType;
        private boolean isInvoker;
        private List<MethodProxy> methodProxies;
        public ClassProxy(){}
        public ClassProxy(String className, StringInvokeUtil.InvokeType invokeType, boolean isInvoker, List<MethodProxy> methodProxies) {
            this.className = className;
            this.invokeType = invokeType;
            this.isInvoker = isInvoker;
            this.methodProxies = methodProxies;
        }

        public MethodProxy getMethodProxy(String methodName) {
            if(methodProxies == null || methodProxies.size() == 0) {
                return null;
            }

            for(MethodProxy methodProxy : methodProxies) {
                if(StringInvokeUtil.isMatches(methodProxy.getMethodName(), methodName, methodProxy.invokeType, methodProxy.isInvoker)) {
                    return methodProxy;
                }
            }

            return null;
        }

        public String getClassName() {
            return className;
        }
        public void setClassName(String className) {
            this.className = className;
        }
        public StringInvokeUtil.InvokeType getInvokeType() {
            return invokeType;
        }
        public void setInvokeType(StringInvokeUtil.InvokeType invokeType) {
            this.invokeType = invokeType;
        }
        public boolean isInvoker() {
            return isInvoker;
        }
        public void setInvoker(boolean invoker) {
            isInvoker = invoker;
        }
        public List<MethodProxy> getMethodProxies() {
            return methodProxies;
        }
        public void setMethodProxies(List<MethodProxy> methodProxyList) {
            this.methodProxies = methodProxyList;
        }
    }

    public static class MethodProxy {
        private String methodName;
        private StringInvokeUtil.InvokeType invokeType;
        private boolean isInvoker;
        private boolean displayParamAndReturn = true;
        private boolean displayExecutionTime = false;
        private boolean displayException = false;

        public MethodProxy() {}
        public MethodProxy(String methodName, StringInvokeUtil.InvokeType invokeType, boolean isInvoker) {
            this.methodName = methodName;
            this.invokeType = invokeType;
            this.isInvoker = isInvoker;
        }

        public String getMethodName() {
            return methodName;
        }

        public void setMethodName(String methodName) {
            this.methodName = methodName;
        }

        public StringInvokeUtil.InvokeType getInvokeType() {
            return invokeType;
        }

        public void setInvokeType(StringInvokeUtil.InvokeType invokeType) {
            this.invokeType = invokeType;
        }

        public boolean isInvoker() {
            return isInvoker;
        }

        public void setInvoker(boolean invoker) {
            isInvoker = invoker;
        }

        public boolean isDisplayParamAndReturn() {
            return displayParamAndReturn;
        }

        public void setDisplayParamAndReturn(boolean displayParamAndReturn) {
            this.displayParamAndReturn = displayParamAndReturn;
        }

        public boolean isDisplayExecutionTime() {
            return displayExecutionTime;
        }

        public void setDisplayExecutionTime(boolean displayExecutionTime) {
            this.displayExecutionTime = displayExecutionTime;
        }

        public boolean isDisplayException() {
            return displayException;
        }

        public void setDisplayException(boolean displayException) {
            this.displayException = displayException;
        }
    }
}
