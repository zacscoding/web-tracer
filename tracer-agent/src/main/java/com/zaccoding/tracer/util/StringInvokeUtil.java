package com.zaccoding.tracer.util;

import com.sun.corba.se.impl.resolver.BootstrapResolverImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zacconding
 * @Date 2018-01-21
 * @GitHub : https://github.com/zacscoding
 */
public class StringInvokeUtil {
    public enum InvokeType {
        startsWith, endsWith, equals, contains;
    }
    public static Boolean isMatches(String name, String targetName, InvokeType invokeType, boolean isInvoker) {
        String invoker = isInvoker ? name : targetName;
        String param = isInvoker ? targetName : name;
        if(invoker == null) {
            return false;
        }
        switch(invokeType) {
            case startsWith:
                return invoker.startsWith(param);
            case endsWith :
                return invoker.endsWith(param);
            case equals :
                return invoker.equals(param);
            case contains :
                return invoker.contains(param);
            default :
                return false;
        }
    }
}
