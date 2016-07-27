package com.algorithms;

/**
 * Created by Administrator on 2016/7/27.
 */
public class RedBlackTree {
    private final int RED = 0;
    private final int BLACK = 1;
    private final Node SENTINEL = new Node(null,null,null,null,BLACK);
    private Node ROOT ;
    /**
     * 左旋是将当前节点与右节点进行逆时针旋转，那么我们规定传入的节点为node，右节点为rNode，父节点为pNode
     * 1，把rNode的左子节点变为node的右节点-->rNode的左子节点>node
     * 2，判断rNode的左子节点是否为哨兵，如果不是则修改父节点指针
     * 3，判断node节点的父节点是否为哨兵，如果是则为ROOT节点，则将rNode改为ROOT节点
     * 4，判断node节点是父节点的左节点则修改为pNode的左节点指针，否则修改为右节点指针
     * 5，修改指针对应的关系
     * @param node
     */
    public void leftRatation(Node node) {
        Node rNode = node.right;
        final Node pNode = node.parent;
        final Node rNodeLeft = rNode.left;
        node.right = rNodeLeft;
        if (rNodeLeft != SENTINEL) {
            rNodeLeft.parent = node;
        }
        if (pNode == SENTINEL) {
            rNode = ROOT;
        } else if (pNode.right == node) {
            pNode.right = rNode;
        } else {
            pNode.left = rNode;
        }
        node.parent = rNode;
        rNode.parent = pNode;
        rNode.left = node;
    }

    private static class Node<E extends Comparable> {
        Node left;
        Node right;
        Node parent;
        int color;
        E ele;

        public Node(Node parent,Node left, E ele, Node right,int color) {
            this.parent = parent;
            this.left = left;
            this.right = right;
            this.ele = ele;
            this.color = color;
        }
    }
}
