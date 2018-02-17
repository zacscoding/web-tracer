package com.zaccoding.tracer.agent;

import java.io.PrintWriter;

/**
 * @author zacconding
 * @Date 2018-02-18
 * @GitHub : https://github.com/zacscoding
 */
public class LOGGER {

    private static PrintWriter pw;

    static {
        pw = new PrintWriter(System.out);
    }

    public static void println(Object message) {
        pw.println(parse(message));
    }

    private static String parse(Object message) {
        // 설정값 + reflection으로 추출하는 거 추가해야 함
        return message == null ? "NULL" : message.toString();
    }
}
