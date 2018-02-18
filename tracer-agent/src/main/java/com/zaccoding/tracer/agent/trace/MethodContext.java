package com.zaccoding.tracer.agent.trace;

import java.util.LinkedList;
import java.util.List;

/**
 * @author zacconding
 * @Date 2018-02-18
 * @GitHub : https://github.com/zacscoding
 */
public class MethodContext {

    // Classname::methodname
    private String id;
    // param values
    private List<String> params;
    // return values
    private String returnValue;
    // method execute time
    private long excuteTime;
    // exist exception
    private boolean existException;
    private int paramCnt = 0;
    private int deps;

    public int getParamCntAndInc() {
        return ++paramCnt;
    }

    // =================================
    // Constructors, Getters, Setters
    // =================================
    public MethodContext() {
    }

    public MethodContext(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getParams() {
        return params;
    }

    public List<String> getParamasOrCreate() {
        if (params == null) {
            params = new LinkedList<String>();
        }

        return this.params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }

    public String getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(String returnValue) {
        this.returnValue = returnValue;
    }

    public long getExcuteTime() {
        return excuteTime;
    }

    public void setExcuteTime(long excuteTime) {
        this.excuteTime = excuteTime;
    }

    public boolean isExistException() {
        return existException;
    }

    public void setExistException(boolean existException) {
        this.existException = existException;
    }

    public int getDeps() {
        return deps;
    }

    public void setDeps(int deps) {
        this.deps = deps;
    }
}
