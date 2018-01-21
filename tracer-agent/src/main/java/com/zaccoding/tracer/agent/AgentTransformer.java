package com.zaccoding.tracer.agent;

import com.zaccoding.tracer.agent.asm.ProxyClassVisitor;
import com.zaccoding.tracer.util.OpcodesUtil;
import org.objectweb.asm.*;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * @author zacconding
 * @Date 2018-01-21
 * @GitHub : https://github.com/zacscoding
 */
public class AgentTransformer implements ClassFileTransformer {
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        try {
            if(className == null) {
                return null;
            }

            // set class desc
            final ClassDesc classDesc = new ClassDesc();
            ClassReader cr = new ClassReader(classfileBuffer);
            cr.accept(new ClassVisitor(Opcodes.ASM5) {
                public void visit(int version, int access, String name, String signature, String superName,
                                  String[] interfaces) {
                    classDesc.set(version, access, name, signature, superName, interfaces);
                    super.visit(version, access, name, signature, superName, interfaces);
                }

                @Override
                public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
                    classDesc.anotation += desc;
                    return super.visitAnnotation(desc, visible);
                }
            }, 0);

            // check interface
            if (OpcodesUtil.isInterface(classDesc.access)) {
                return null;
            }

            if(Configurer.INSTANCE.isError()) {
                return classfileBuffer;
            }

            ProxyConfigurer proxyConfigurer = Configurer.INSTANCE.getProxyConfigurer();
            ProxyConfigurer.ClassProxy classProxy = proxyConfigurer.getClassProxy(className);
            if(classProxy != null) {
                System.out.println("## find target class :: " + className);
                ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_FRAMES|ClassWriter.COMPUTE_MAXS);
                cr.accept(new ProxyClassVisitor(cw, classProxy), ClassReader.EXPAND_FRAMES);
                classfileBuffer = cw.toByteArray();
            }
        }
        catch(Throwable t) {
            t.printStackTrace();
        }

        return classfileBuffer;
    }

    private static boolean isInterface(int access) {
        return (access & Opcodes.ACC_INTERFACE) != 0;
    }
}