package com.zaccoding.tracer.agent.asm;

import com.zaccoding.tracer.agent.ProxyConfigurer;
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
    private boolean isAll = false;

    public ProxyClassVisitor(ClassVisitor cv, ProxyConfigurer.ClassProxy classConfig) {
        super(ASM5, cv);
        this.classConfig = classConfig;
        List<ProxyConfigurer.MethodProxy> methodProxies = classConfig.getMethodProxies();
        if(methodProxies == null || methodProxies.size() == 0 ||
                (methodProxies.size() == 1 ) && methodProxies.get(0).getMethodName().equals("*") ) {
            isAll = true;
        }
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        System.out.println("name : " + name + ", desc : " + desc);
        MethodVisitor mv = super.visitMethod(access,name,desc,signature,exceptions);
        if(mv == null) {
            return mv;
        }

//        ProxyConfigurer.MethodProxy methodProxy = classConfig.getMethodProxy(name);
//        if(isAll) {
//            return new ProxyMethodVisitor(access,desc,mv);
//        }
//        else {
//            // find method target
//        }

        return mv;
    }
}

class ProxyMethodVisitor extends LocalVariablesSorter implements org.objectweb.asm.Opcodes {
    protected ProxyMethodVisitor(int access, String desc, MethodVisitor mv) {
        super(ASM5, access, desc, mv);
    }

    @Override
    public void visitCode() {
        mv.visitCode();
    }
}