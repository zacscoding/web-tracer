package com.zaccoding.tracer.agent;

import com.google.gson.Gson;
import com.zaccoding.tracer.agent.trace.TransactionTrace;
import java.lang.instrument.Instrumentation;

/**
 * @author zacconding
 * @Date 2018-01-21
 * @GitHub : https://github.com/zacscoding
 */
public class Agent {

    private static Instrumentation instrumentation;

    public static Instrumentation getInstrumentation() {
        return instrumentation;
    }

    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("@@Start premain");
        LOGGER.println("Start premain");
        if (Agent.instrumentation != null) {
            System.out.println("@@ Agent::instrumentation is not null");
            return;
        }
        try {
            if (Configurer.INSTANCE.isError()) {
                System.out.println("@@ Agent::Configurer.INSTANCE.isError() is true");
                return;
            }
            
            Agent.instrumentation = inst;
            Agent.instrumentation.addTransformer(new AgentTransformer());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
