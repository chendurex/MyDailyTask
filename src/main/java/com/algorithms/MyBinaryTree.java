package com.algorithms;

/**
 * Created by Administrator on 2016/7/18.
 */
public class MyBinaryTree<E extends Comparable> {

    public static void main(String[] args) {
        MyBinaryTree tree = new MyBinaryTree(10);
        tree.insert(5);
        tree.insert(20);
    }

    private Node root;

    public MyBinaryTree(E ele) {
        root = new Node(null, ele, null);
    }

    public void insert(E ele) {
        if (ele == null) {
            throw new IllegalArgumentException("value can not be null");
        }
        insert(root,new Node(null,ele,null));
    }

    public void insert(Node node, Node another) {
        if (node.ele.compareTo(another.ele) > 0) {
            if (node.left == null) {
                node.left = another;
            } else {
                insert(node.left,another);
            }
        } else {
            if (node.right == null) {
                node.right = another;
            } else {
                insert(node.right,another);
            }
        }
    }

    private static class Node<E extends Comparable> {
        Node left;
        Node right;
        E ele;

        public Node(Node left, E ele, Node right) {
            this.left = left;
            this.right = right;
            this.ele = ele;
        }
    }
}
