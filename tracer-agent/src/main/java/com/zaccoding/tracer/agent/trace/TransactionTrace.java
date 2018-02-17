package com.zaccoding.tracer.agent.trace;

import com.zaccoding.tracer.agent.CustomObjectParser;
import com.zaccoding.tracer.agent.LOGGER;
import java.util.List;
import java.util.Stack;

/**
 * @author zacconding
 * @Date 2018-02-18
 * @GitHub : https://github.com/zacscoding
 */
public class TransactionTrace {

    public static void startTransaction(String id) {
        TransactionContext ctx = TransactionManager.startTrasaction();
        if (ctx == null) {
            LOGGER.println("[ERROR] called startTransaction. but there is no TransactionContext");
            return;
        }

        System.out.println("@@TransactionTrace::startTransaction is called");
        ctx.traceMethod(new MethodContext(id));
    }

    public static void appendParam(Object value) {
        TransactionContext ctx = TransactionManager.startTrasaction();
        if (ctx == null) {
            LOGGER.println("[ERROR] called startTransaction. but there is no TransactionContext");
            return;
        }

        MethodContext methodCtx = ctx.getLastTraceMethod();
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
            LOGGER.println("[ERROR] called startTransaction. but there is no TransactionContext");
            return;
        }

        MethodContext methodCtx = ctx.removeLastTraceMethod();
        if (methodCtx == null) {
            LOGGER.println("[ERROR] called getLastTraceMethod. but there is no MethodContext");
            return;
        }
        System.out.println("@@TransactionTrace::appendReturnValue is called");

        // set return value & execute time
        methodCtx.setReturnValue(parseReturnValue(value));
        methodCtx.setExcuteTime(executeTime);

        // push complete trace
        ctx.push(methodCtx);

        // check complete all
        if (ctx.getTraceMethods().size() == 0) {
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
        Stack<MethodContext> methods = ctx.getCollectedMethods();
        if (methods == null || methods.size() == 0) {
            LOGGER.println("[ERROR] there is no traced method stack");
            return;
        }

        final String newLine = System.getProperty("line.separator");
        int idx = 0;

        while (!methods.isEmpty()) {
            String deps = "";
            // append deps
            for (int j = 0; j < idx; j++) {
                deps += "  ";
            }

            MethodContext methodContext = methods.pop();
            sb.append(deps).append(methodContext.getId()).append("[").append(methodContext.getExcuteTime()).append("ms] : ").append(methodContext.getReturnValue()).append(newLine);
            List<String> params = methodContext.getParams();
            if (params != null && params.size() > 0) {
                for (String param : params) {
                    sb.append(deps).append(param).append(newLine);
                }
            }
            idx++;
        }

        // temp :: System.out.println
        System.out.println(sb.toString());
        //LOGGER.println(sb.toString());
    }

    private static String parseParam(int idx, Object value) {
        return idx + " : " + CustomObjectParser.parse(value);
    }

    private static String parseReturnValue(Object value) {
        return CustomObjectParser.parse(value);
    }
}
