package com.zaccoding.tracer.agent.trace.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zacconding
 * @Date 2018-02-18
 * @GitHub : https://github.com/zacscoding
 */
public class SimpleNode<T> {

    private List<SimpleNode<T>> children;
    private SimpleNode<T> parent = null;
    private T data = null;
    private int deps;

    public SimpleNode(T data) {
        this.data = data;
    }

    public SimpleNode(T data, SimpleNode<T> parent) {
        this.data = data;
        this.parent = parent;
    }

    public List<SimpleNode<T>> getChildren() {
        return children;
    }

    public void setParent(SimpleNode<T> parent) {
        this.parent = parent;
    }

    public void addChild(SimpleNode<T> child) {
        child.setParent(this);

        if (this.children == null) {
            this.children = new ArrayList<SimpleNode<T>>();
        }

        this.children.add(child);
    }

    public SimpleNode<T> getParent() {
        return this.parent;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isRoot() {
        return (this.parent == null);
    }

    public boolean isLeaf() {
        if (this.children == null || this.children.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public void removeParent() {
        this.parent = null;
    }

    public int getDeps() {
        return deps;
    }

    public void setDeps(int deps) {
        this.deps = deps;
    }
}