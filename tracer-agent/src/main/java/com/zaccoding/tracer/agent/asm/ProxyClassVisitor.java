package com.zaccoding.tracer.agent.asm;

import com.zaccoding.tracer.agent.ProxyConfigurer;
import com.zaccoding.tracer.agent.ProxyConfigurer.MethodProxy;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.LocalVariablesSorter;

import java.util.List;

/**
 * @author zacconding
 * @Date 2018-01-21
 * @GitHub : https://github.com/zacscoding
 */
public class ProxyClassVisitor extends ClassVisitor implements Opcodes {

    private ProxyConfigurer.ClassProxy classConfig;
    private ProxyConfigurer.MethodProxy defaultMethodProxy;
    private boolean isAll = false;
    private String className;

    public ProxyClassVisitor(ClassVisitor cv, String className, ProxyConfigurer.ClassProxy classConfig) {
        super(ASM5, cv);
        this.className = className;
        this.classConfig = classConfig;
        List<ProxyConfigurer.MethodProxy> methodProxies = classConfig.getMethodProxies();
        if (methodProxies == null || methodProxies.size() == 0 ||
            (methodProxies.size() == 1) && methodProxies.get(0).getMethodName().equals("*")) {
            isAll = true;
            defaultMethodProxy = new ProxyConfigurer.MethodProxy();
            defaultMethodProxy.setDisplayExecutionTime(true);
            defaultMethodProxy.setDisplayParamAndReturn(true);
        }
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if (mv == null) {
            return mv;
        }

        ProxyConfigurer.MethodProxy methodProxy = null;
        if ("<init>".equals(name)) {
            return mv;
        } else if (isAll) {
            return new ProxyMethodVisitor(access, desc, mv, getMethodId(className, name, desc), defaultMethodProxy);
        } else if ((methodProxy = classConfig.getMethodProxy(name)) != null) {
            return new ProxyMethodVisitor(access, desc, mv, getMethodId(className, name, desc), methodProxy);
        }
        return mv;
    }

    private String getMethodId(String className, String methodName, String methodDesc) {
        StringBuilder sb = new StringBuilder(className.length() + methodName.length() + methodDesc.length() + 2);
        return sb.append(className).append("::").append(methodName).append(methodDesc).toString();
    }

}
