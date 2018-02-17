package com.zaccoding.tracer.agent.trace;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * @author zacconding
 * @Date 2018-02-18
 * @GitHub : https://github.com/zacscoding
 */
public class TransactionContext {

    // complete method contexts
    private Stack<MethodContext> collectedMethods;
    // tracing method contexts
    private List<MethodContext> traceMethods;

    /**
     * push complete method context
     */
    public void push(MethodContext methodContext) {
        if (methodContext == null) {
            return;
        }

        if (collectedMethods == null) {
            collectedMethods = new Stack<MethodContext>();
        }

        collectedMethods.push(methodContext);
    }

    /**
     * add trace method context
     */
    public void traceMethod(MethodContext methodContext) {
        if (traceMethods == null) {
            traceMethods = new LinkedList<MethodContext>();
        }

        traceMethods.add(methodContext);
    }

    /**
     * get last trace context or null
     */
    public MethodContext getLastTraceMethod() {
        if (traceMethods == null || traceMethods.size() == 0) {
            return null;
        }

        return traceMethods.get(traceMethods.size() - 1);
    }

    /**
     * remove last trace context or null & remote it
     */
    public MethodContext removeLastTraceMethod() {
        if (traceMethods == null || traceMethods.size() == 0) {
            return null;
        }

        return traceMethods.remove(traceMethods.size() - 1);
    }

    // =================================
    // Getters, Setters
    // =================================
    public Stack<MethodContext> getCollectedMethods() {
        return collectedMethods;
    }

    public void setCollectedMethods(Stack<MethodContext> collectedMethods) {
        this.collectedMethods = collectedMethods;
    }

    public List<MethodContext> getTraceMethods() {
        return traceMethods;
    }

    public void setTraceMethods(List<MethodContext> traceMethods) {
        this.traceMethods = traceMethods;
    }
}
