package com.zaccoding.tracer.agent.trace.tree;

import java.util.List;

/**
 * @author zacconding
 * @Date 2018-02-18
 * @GitHub : https://github.com/zacscoding
 */
public class SimpleTreeImpl<T> implements SimpleTree<T> {

    private SimpleNode<T> root;
    private SimpleNode<T> current;
    private int deps;

    @Override
    public void add(T t) {
        if (root == null) {
            root = new SimpleNode<T>(t);
            current = root;
        } else {
            SimpleNode<T> newNode = new SimpleNode<T>(t);
            current.addChild(newNode);
            current = newNode;
        }
        current.setDeps(deps);
        deps++;
    }

    @Override
    public void complete() {
        if (current != null) {
            current = current.getParent();
            deps--;
        }
    }

    @Override
    public SimpleNode<T> getRoot() {
        return root;
    }

    @Override
    public SimpleNode<T> getCurrentNode() {
        return current == null ? null : current;
    }

    @Override
    public T getCurrentData() {
        return current == null ? null : current.getData();
    }

//    public static void main(String[] args) {
//        SimpleTree<String> tree = new SimpleTreeImpl<String>();
//        // start A
//        tree.add("A");
//        System.out.println(tree.getCurrentData() + " : " + tree.getCurrentNode().getDeps());
//        // start B
//        tree.add("B");
//        System.out.println(tree.getCurrentData() + " : " + tree.getCurrentNode().getDeps());
//        // start C
//        tree.add("C");
//        System.out.println(tree.getCurrentData() + " : " + tree.getCurrentNode().getDeps());
//        // complete C
//        tree.complete();
//        // start D
//        tree.add("D");
//        System.out.println(tree.getCurrentData() + " : " + tree.getCurrentNode().getDeps());
//        // complete D
//        tree.complete();
//        // complete B
//        tree.complete();
//        // start E
//        tree.add("E");
//        System.out.println(tree.getCurrentData() + " : " + tree.getCurrentNode().getDeps());
//        // complete E
//        tree.complete();
//        // complete A
//        tree.complete();
//
//        System.out.println("===========================================");
//        SimpleNode<String> root = tree.getRoot();
//        prefix(root);
//    }
//
//    public static void prefix(SimpleNode<String> node) {
//        if (node != null) {
//            System.out.println(node.getData() + " : " + node.getDeps());
//            List<SimpleNode<String>> childs = node.getChildren();
//            if (childs != null) {
//                for (SimpleNode<String> child : childs) {
//                    prefix(child);
//                }
//            }
//        }
//    }
}
