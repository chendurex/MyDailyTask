package com.algorithms;

/**
 * Created by Administrator on 2016/7/18.
 */
public class MyBinaryTree<E extends Comparable> {

    public static void main(String[] args) {
        MyBinaryTree tree = new MyBinaryTree(5);
        tree.insert(1);tree.insert(10);tree.insert(15);tree.insert(20);
        tree.insert(25);tree.insert(18);tree.insert(8);tree.insert(21);
        tree.insert(29);tree.insert(31);tree.insert(11);tree.insert(17);
        System.out.println(tree.preNode(tree.get(21)).ele);
        System.out.println(tree.nextNode(tree.get(20)).ele);
    }

    private Node root;

    public MyBinaryTree(E ele) {
        root = new Node(null, null, ele, null);
    }

    public Node preNode(Node node) {
        if (node.left != null) {
            if (node.left.right != null) {
                Node preNode = node.left.right;
                while (preNode.right != null) {
                    preNode = preNode.right;
                }
                return preNode;
            }
            return node.left;
        } else {
            while (node.parent != null) {
                if (node.parent.right == node) {
                    return node.parent;
                }
                node = node.parent;
            }
        }
        return new Node(null,null,null,null);
    }

    public Node nextNode(Node node) {
        if (node.right != null) {
            Node nextNode = node.right;
            while (nextNode.left != null) {
                nextNode = nextNode.left;
            }
            return nextNode;
        } else {
            Node nextNode = node;
            while (nextNode.parent.left != nextNode) {
                nextNode = nextNode.parent;
            }
            return nextNode.parent;
        }
    }

    public Node get(E ele) {
        if (ele == null) {
            throw new IllegalArgumentException("value can not be null");
        }
        return get(ele, root);
    }

    private Node get(E ele, Node node) {
        while (node != null) {
            final int compare = ele.compareTo(node.ele);
            if (compare == 0) {
                return node;
            } else if (compare < 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return new Node(null, null, null, null);
    }


    public void insert(E ele) {
        if (ele == null) {
            throw new IllegalArgumentException("value can not be null");
        }
        insert(new Node(null, null,ele,null), root);
    }

    private void insert(Node node, Node pNode) {
        Node<E> parent = pNode;
        while (pNode != null) {
            parent = pNode;
            final int compare = node.ele.compareTo(pNode.ele);
            if (compare == 0) {
                return ;
            } else if (compare < 0) {
                pNode = pNode.left;
            } else {
                pNode = pNode.right;
            }
        }
        if (node.ele.compareTo(parent.ele) < 0) {
            parent.left = node;
        } else {
            parent.right = node;
        }
        node.parent = parent;
    }

    private static class Node<E extends Comparable> {
        Node left;
        Node right;
        Node parent;
        E ele;

        public Node(Node parent,Node left, E ele, Node right) {
            this.parent = parent;
            this.left = left;
            this.right = right;
            this.ele = ele;
        }
    }
}
