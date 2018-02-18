package com.zaccoding.tracer.agent.trace;

import com.zaccoding.tracer.agent.trace.tree.SimpleNode;
import com.zaccoding.tracer.agent.trace.tree.SimpleTree;
import com.zaccoding.tracer.agent.trace.tree.SimpleTreeImpl;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * @author zacconding
 * @Date 2018-02-18
 * @GitHub : https://github.com/zacscoding
 */
public class TransactionContext {

    private SimpleTree<MethodContext> methodTree;

    public TransactionContext() {
        this.methodTree = new SimpleTreeImpl<MethodContext>();
    }

    /**
     * start method call
     */
    public void startMethod(MethodContext methodContext) {
        if (methodTree == null) {
            methodTree = new SimpleTreeImpl<MethodContext>();
        }

        methodTree.add(methodContext);
    }

    /**
     * get current method
     */
    public MethodContext getCurrentMethod() {
        if (methodTree != null) {
            return methodTree.getCurrentData();
        }

        return null;
    }

    /**
     * get current method & complete this method
     */
    public MethodContext endMethod() {
        MethodContext methodCtx = null;

        if (methodTree != null) {
            methodCtx = methodTree.getCurrentData();
            methodTree.complete();
        }

        return methodCtx;
    }

    /**
     * check remain tracing method context
     */
    public boolean hasTraceMethod() {
        if (methodTree != null) {
            return methodTree.getCurrentNode() != null;
        }

        return true;
    }

    public SimpleNode<MethodContext> getRootNode() {
        return methodTree == null ? null : methodTree.getRoot();
    }

}
