package com.zaccoding.tracer.agent.trace;

/**
 * @author zacconding
 * @Date 2018-02-18
 * @GitHub : https://github.com/zacscoding
 */
public class TransactionManager {

    private static ThreadLocal<TransactionContext> contexts = new ThreadLocal<TransactionContext>();

    /**
     * return current thread`s TransactionContext (or create)
     */
    public static TransactionContext startTrasaction() {
        if (contexts.get() == null) {
            contexts.set(new TransactionContext());
        }

        return contexts.get();
    }

    /**
     * get current thread`s TransactionContext
     */
    public static TransactionContext getContext() {
        return contexts.get();
    }

    /**
     * remove current thread`s TransactionContext & return it
     */
    public static TransactionContext endTransaction() {
        TransactionContext ctx = contexts.get();
        contexts.remove();
        return ctx;
    }
}
