package com.zaccoding.tracer.agent.trace;

import com.zaccoding.tracer.agent.CustomObjectParser;
import com.zaccoding.tracer.agent.LOGGER;
import com.zaccoding.tracer.agent.trace.tree.SimpleNode;
import java.util.List;
import java.util.Stack;

/**
 * @author zacconding
 */
public class TransactionTrace {

    public static void startTransaction(String id) {
        TransactionContext ctx = TransactionManager.startTrasaction();
        if (ctx == null) {
            LOGGER.println("[ERROR] called startTransaction. but there is no TransactionContext");
            return;
        }

        System.out.println("@@TransactionTrace::startTransaction is called");
        ctx.startMethod(new MethodContext(id));
    }

    public static void appendParam(Object value) {
        TransactionContext ctx = TransactionManager.startTrasaction();
        if (ctx == null) {
            LOGGER.println("[ERROR] called startTransaction. but there is no TransactionContext");
            return;
        }

        MethodContext methodCtx = ctx.getCurrentMethod();
        if (methodCtx == null) {
            LOGGER.println("[ERROR] called getLastTraceMethod. but there is no MethodContext");
            return;
        }

        System.out.println("@@TransactionTrace::appendParam is called");
        int paramCnt = methodCtx.getParamCntAndInc();
        methodCtx.getParamasOrCreate().add(parseParam(paramCnt, value));
    }

    public static void appendReturnValue(Object value, long executeTime) {
        TransactionContext ctx = TransactionManager.startTrasaction();
        if (ctx == null) {
            System.out.println("[ERROR] called startTransaction. but there is no TransactionContext");
            return;
        }

        MethodContext methodCtx = ctx.endMethod();
        if (methodCtx == null) {
            System.out.println("[ERROR] called getLastTraceMethod. but there is no MethodContext");
            return;
        }

        // set return value & execute time
        methodCtx.setReturnValue(parseReturnValue(value));
        methodCtx.setExcuteTime(executeTime);

        // check complete all
        if (!ctx.hasTraceMethod()) {
            endTrasaction();
        }
    }

    private static void endTrasaction() {
        TransactionContext ctx = TransactionManager.endTransaction();
        if (ctx == null) {
            return;
        }

        System.out.println("@@TransactionTrace::endTrasaction is called");

        StringBuilder sb = new StringBuilder();
        SimpleNode<MethodContext> root = ctx.getRootNode();
        if (root == null) {
            LOGGER.println("[ERROR] there is no root node in method tree");
            return;
        }

        final String newLine = System.getProperty("line.separator");
        prefixTraversal(sb, root, newLine);

        // temp :: System.out.println
        System.out.println(sb.toString());
        //LOGGER.println(sb.toString());
    }

    private static void prefixTraversal(StringBuilder sb, SimpleNode<MethodContext> methodNode, String newLine) {
        if (methodNode == null || sb == null) {
            return;
        }

        int deps = methodNode.getDeps();
        String depsVal = "";
        // append deps
        for (int i = 0; i < deps; i++) {
            depsVal += "|  ";
        }

        MethodContext methodContext = methodNode.getData();
        sb.append(depsVal).append("+--").append(methodContext.getId()).append("[").append(methodContext.getExcuteTime()).append("ms] : ").append(methodContext.getReturnValue()).append(newLine);
        List<String> params = methodContext.getParams();
        if (params != null && params.size() > 0) {
            for (String param : params) {
                sb.append(depsVal).append(" -- ").append(param).append(newLine);
            }
        }

        // traversal child
        List<SimpleNode<MethodContext>> childs = methodNode.getChildren();
        if (childs != null) {
            for (SimpleNode<MethodContext> child : childs) {
                prefixTraversal(sb, child, newLine);
            }
        }
    }

    private static String parseParam(int idx, Object value) {
        return idx + " : " + CustomObjectParser.parse(value);
    }

    private static String parseReturnValue(Object value) {
        return CustomObjectParser.parse(value);
    }
}
