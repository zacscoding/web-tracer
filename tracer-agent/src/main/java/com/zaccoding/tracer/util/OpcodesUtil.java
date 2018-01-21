package com.zaccoding.tracer.util;

import org.objectweb.asm.Opcodes;

/**
 * @author zacconding
 * @Date 2018-01-21
 * @GitHub : https://github.com/zacscoding
 */
public abstract class OpcodesUtil{
    public static boolean isInterface(int access) {
        return (access & Opcodes.ACC_INTERFACE) != 0;
    }
}
