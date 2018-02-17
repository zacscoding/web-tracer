package com.zaccoding.tracer.agent;

/**
 * @author zacconding
 * @Date 2018-02-18
 * @GitHub : https://github.com/zacscoding
 */
public class CustomObjectParser {

    public static String parse(Object inst) {
        return inst == null ? "NULL" : inst.toString();
    }

}
