package com.zaccoding.tracer.util;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * Opcodes util class
 *
 * @author zacconding
 */
public abstract class OpcodesUtil implements Opcodes {

    public static boolean isInterface(int access) {
        return (access & Opcodes.ACC_INTERFACE) != 0;
    }

    /**
     * get wrapper class about primitive type
     */
    public static String getWrapperClass(int sort) {
        switch (sort) {
            case Type.BOOLEAN:
                return "java/lang/Boolean";
            case Type.BYTE:
                return "java/lang/Byte";
            case Type.SHORT:
                return "java/lang/Short";
            case Type.CHAR:
                return "java/lang/Character";
            case Type.INT:
                return "java/lang/Integer";
            case Type.LONG:
                return "java/lang/Long";
            case Type.FLOAT:
                return "java/lang/Float";
            case Type.DOUBLE:
                return "java/lang/Double";
            default:
                return null;
        }
    }

    /**
     * get load or store opcodes depend on sort value
     */
    public static int getLoadOrStore(int sort, boolean isLoad) {
        switch (sort) {
            case Type.BOOLEAN:
            case Type.BYTE:
            case Type.SHORT:
            case Type.CHAR:
            case Type.INT:
                return isLoad ? ILOAD : ISTORE;
            case Type.LONG:
                return isLoad ? LLOAD : LSTORE;
            case Type.FLOAT:
                return isLoad ? FLOAD : FSTORE;
            case Type.DOUBLE:
                return isLoad ? DLOAD : DSTORE;
            default:
                return isLoad ? ALOAD : ASTORE;
        }
    }
}
