package com.zaccoding.tracer.agent.asm;

import com.zaccoding.tracer.agent.ProxyConfigurer;
import com.zaccoding.tracer.agent.trace.TransactionTrace;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.LocalVariablesSorter;

/**
 * @author zacconding
 * @Date 2018-02-18
 * @GitHub : https://github.com/zacscoding
 */
public class ProxyMethodVisitor extends LocalVariablesSorter implements org.objectweb.asm.Opcodes {

    private ProxyConfigurer.MethodProxy methodProxy;
    private String id;
    private String trace = TransactionTrace.class.getName().replace('.', '/');
    private String desc;

    protected ProxyMethodVisitor(int access, String desc, MethodVisitor mv, String id, ProxyConfigurer.MethodProxy methodProxy) {
        super(ASM5, access, desc, mv);
        this.desc = desc;
        this.methodProxy = methodProxy;
        this.id = id;
    }

    @Override
    public void visitCode() {
        mv.visitLdcInsn(id);
        mv.visitMethodInsn(INVOKESTATIC, trace, "startTransaction", "(Ljava/lang/String;)V", false);

        if (methodProxy.isDisplayParamAndReturn()) {
            addParameter();
        }

        mv.visitCode();
    }

    @Override
    public void visitInsn(int opcode) {
        if ((opcode >= IRETURN && opcode <= RETURN)) {
            if (opcode == RETURN) {
                mv.visitInsn(ACONST_NULL);
            } else {
                mv.visitInsn(DUP);
            }
            mv.visitInsn(LCONST_0);
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, trace, "appendReturnValue", "(Ljava/lang/Object;J)V", false);
        }
        mv.visitInsn(opcode);
    }

    private void addParameter() {
        Type[] params = Type.getArgumentTypes(desc);
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                int idx = i + 1;
                String wrapper = null;
                int opcodes = -1;
                switch (params[i].getSort()) {
                    case Type.BOOLEAN:
                        wrapper = "java/lang/Boolean";
                        opcodes = ILOAD;
                        break;
                    case Type.BYTE:
                        wrapper = "java/lang/Byte";
                        opcodes = ILOAD;
                        break;
                    case Type.SHORT:
                        wrapper = "java/lang/Short";
                        opcodes = ILOAD;
                        break;
                    case Type.CHAR:
                        wrapper = "java/lang/Character";
                        opcodes = ILOAD;
                        break;
                    case Type.INT:
                        wrapper = "java/lang/Integer";
                        opcodes = ILOAD;
                        break;
                    case Type.LONG:
                        wrapper = "java/lang/Long";
                        opcodes = LLOAD;
                        break;
                    case Type.FLOAT:
                        wrapper = "java/lang/Float";
                        opcodes = FLOAD;
                        break;
                    case Type.DOUBLE:
                        wrapper = "java/lang/Double";
                        opcodes = DLOAD;
                        break;
                    default:
                        mv.visitVarInsn(Opcodes.ALOAD, idx);
                        break;
                }

                if (wrapper != null && opcodes >= 0) {
                    mv.visitVarInsn(opcodes, idx);
                    mv.visitMethodInsn(INVOKESTATIC, wrapper, "valueOf", "(" + params[i].getDescriptor() + ")L" + wrapper + ";", false);
                }

                mv.visitMethodInsn(INVOKESTATIC, trace, "appendParam", "(Ljava/lang/Object;)V", false);
            }
        }
    }


}