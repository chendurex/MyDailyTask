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

        tree.delete(20);
        System.out.println(tree.preNode(tree.get(21)).ele);
        System.out.println(tree.nextNode(tree.get(18)).ele);
    }

    private Node root;

    public MyBinaryTree(E ele) {
        root = new Node(null, null, ele, null);
    }

    /**
     * 查找前驱节点
     * 1，如果存在左节点，则前驱节点是左节点的最右子节点
     * 2，否则前驱节点是祖先节点延伸出来的右节点
     * 3，否则不存在前驱节点返回空节点
     * @param node
     * @return
     */
    public Node preNode(Node node) {
        if (node.left != null) {
            Node preNode = node.left;
            while (preNode.right != null) {
                preNode = preNode.right;
            }
            return preNode;
        } else {
            Node preNode = node;
            while (preNode.parent != null) {
                if (preNode.parent.right == preNode) {
                    return preNode.parent;
                }
                preNode = preNode.parent;
            }
        }
        return emptyNode();
    }

    /**
     * 1，如果存在右节点，则后继节点是右节点的左子节点
     * 2，否则后继节点是祖先父节点延伸出来的左节点
     * @param node
     * @return
     */
    public Node nextNode(Node node) {
        if (node.right != null) {
            Node nextNode = node.right;
            while (nextNode.left != null) {
                nextNode = nextNode.left;
            }
            return nextNode;
        } else {
            Node nextNode = node;
            while (nextNode.parent != null) {
                if (nextNode.parent.left == nextNode) {
                    return nextNode.parent;
                }
                nextNode = nextNode.parent;
            }
            return emptyNode();
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
        return emptyNode();
    }

    public void delete(E ele) {
        Node node = get(ele);
        if (isNull(node)) {
            throw new IllegalArgumentException("can not find node");
        }
        // 无子节点则直接删除
        if (node.left == null && node.right == null) {
            final Node parent = node.parent;
            if (parent != null) {
                if (parent.left == node) {
                    parent.left = null;
                } else {
                    parent.right = null;
                }
            }
        }// 无右节点则直接把左节点替换
        else if (node.right == null) {
            final Node parent = node.parent;
            if (parent != null) {
                parent.left = node.left;
                node.left.parent = parent;
            }
        }// 右节点无子左节点则直接把右节点替换
        else if (node.right.left == null) {
            final Node parent = node.parent;
            if (parent != null) {
                parent.right = node.right;
                node.right.parent = parent;

                node.left.parent = node.right;
                node.right.left = node.left;
            }
        }// 查找右节点的后继节点替换删除的节点
        else {
            final Node parent = node.parent;
            Node left = node.right.left;
            while (left.left != null) {
                left = left.left;
            }
            // 整理left节点的右节点
            if (left.right != null) {
                left.right.parent = left.parent;
                left.parent.left = left.right;
            }
            // 整理被删除的节点的右节点
            if (node.right != null) {
                node.right.parent = left;
                left.right = node.right;
            }
            // 整理被删除的左节点
            if (node.left != null) {
                node.left.parent = left;
                left.left = node.left;
            }
            parent.right = left;
            left.parent = parent;
        }
        cleanNode(node);
    }

    public void insert(E ele) {
        if (ele == null) {
            throw new IllegalArgumentException("value can not be null");
        }
        insert(initNode(ele), root);
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

    protected Node initNode(E ele) {
        return new Node(null, null, ele, null);
    }

    private Node emptyNode() {
        return new Node(null, null, null, null);
    }

    private void cleanNode(Node node) {
        node.parent = null;
        node.right = null;
        node.left = null;
        node.ele = null;
    }

    public boolean isNull(Node node) {
        return node.ele == null;
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
