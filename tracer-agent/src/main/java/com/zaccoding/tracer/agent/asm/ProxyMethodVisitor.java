package com.zaccoding.tracer.agent.asm;

import com.zaccoding.tracer.agent.ProxyConfigurer;
import com.zaccoding.tracer.agent.trace.TransactionTrace;
import com.zaccoding.tracer.util.OpcodesUtil;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.LocalVariablesSorter;

/**
 * @author zacconding
 */
public class ProxyMethodVisitor extends LocalVariablesSorter implements org.objectweb.asm.Opcodes {

    private Label startFinally = new Label();

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
                mv.visitLdcInsn("void");
            } else {
                mv.visitInsn(DUP);
                Type returnType = Type.getReturnType(desc);
                String wrapper = OpcodesUtil.getWrapperClass(returnType.getSort());
                if (wrapper != null) {
                    mv.visitMethodInsn(INVOKESTATIC, wrapper, "valueOf", "(" + returnType.getDescriptor() + ")L" + wrapper + ";", false);
                }
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
                String wrapper = OpcodesUtil.getWrapperClass(params[i].getSort());
                if (wrapper == null) {
                    mv.visitVarInsn(Opcodes.ALOAD, idx);
                } else {
                    int opcodes = OpcodesUtil.getLoadOrStore(params[i].getSort(), true);
                    mv.visitVarInsn(opcodes, idx);
                    mv.visitMethodInsn(INVOKESTATIC, wrapper, "valueOf", "(" + params[i].getDescriptor() + ")L" + wrapper + ";", false);
                }

                mv.visitMethodInsn(INVOKESTATIC, trace, "appendParam", "(Ljava/lang/Object;)V", false);
            }
        }
    }
}