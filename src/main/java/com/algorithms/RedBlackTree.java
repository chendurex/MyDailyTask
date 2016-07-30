package com.algorithms;

/**
 * 红黑树的五个性质：、
 * 1，所有的节点是红色或者黑色
 * 2，根节点是黑色
 * 3，所有叶节点(没有子节点的节点，即为null的节点)是黑色
 * 4，所有红色节点的左右子节点是黑色
 * 5，对于每一个节点，都有从该节点到叶节点的路径上，有相同数目的黑节点
 */
public class RedBlackTree<E extends Comparable> {

    public static void main(String[] args) {
        RedBlackTree tree = new RedBlackTree();
        int [] array = {5,1,10,15,20,25,18,8,21,29,31,11,17};
        for (int i : array) {
            tree.insert(i);
        }

        for (int i : array) {
            Node node = tree.get(i);
            System.out.println("value = "+node.ele + " ,color = "+ node.color);
        }
    }


    private final String RED = "红";
    private final String BLACK = "黑";
    private Node ROOT;

    /**
     * 左旋是将当前节点与右节点进行逆时针旋转，那么我们规定传入的节点为node，右节点为rNode，父节点为pNode
     * 1，判断rNode的左子节点是否为哨兵，如果不是则修改父节点指针
     * 2，判断pNode是否为哨兵，如果是则为ROOT节点，则将rNode改为ROOT节点
     * 3，判断node节点是pNode的左节点则修改为pNode的左节点指针，否则修改为右节点指针
     * 4，修改指针对应的关系
     * @param node
     */
    public void leftRatation(Node node) {
        Node rNode = node.right;
        final Node pNode = node.parent;
        final Node rNodeLeft = rNode.left;
        if (rNodeLeft != null) {
            rNodeLeft.parent = node;
        }
        if (pNode == null) {
            reCreateRoot(rNode);
        } else if (pNode.left == node) {
            pNode.left = rNode;
        } else {
            pNode.right = rNode;
        }
        node.right = rNodeLeft;
        node.parent = rNode;

        rNode.parent = pNode;
        rNode.left = node;
    }

    /**
     * 右旋是将当前节点与左子节点按照顺时针旋转。我们规定传入的节点为node，左子节点为lNode，父节点为pNode
     * 1，判断lNode的右节点是否为哨兵，如果不是则修改父节点指针
     * 2，判断pNode是否为哨兵，如果是则为ROOT节点，则将lNode改为ROOT节点
     * 3，判断node节点是pNode的左节点则修改为pNode的左节点指针，否则修改为右节点指针
     * 4，修改指针对应的关系
     * @param node
     */
    public void rightRatation(Node node) {
        final Node lNode = node.left;
        final Node pNode = node.parent;
        final Node lrNode = lNode.right;
        if (lrNode != null) {
            lrNode.parent = node;
        }
        if (pNode == null) {
            reCreateRoot(lNode);
        } else if (pNode.left == node) {
            pNode.left = lNode;
        } else {
            pNode.right = lNode;
        }

        lNode.right = node;
        lNode.parent = pNode;

        node.left = lrNode;
        node.parent = lNode;
    }

    /**
     * 修复红黑树
     * 1，如果树为ROOT则直接把ROOT.color = BLACK
     * 2，如果是的父节点是黑色，则不用修改
     * 3，如果树的父节点是红色则根据祖父节点(叔叔节点)有两种情况：
     * 3.1，叔叔节点在父节点左边
     * 3.2，叔叔节点在父节点右边
     * 4，因为修复3情况是对称性，所以我们只考虑3.1情况修复，此时有三种情况：
     * 4.1，获取叔叔节点(ppNode.right因为父节点在祖父节点的左边)
     * 4.2，如果叔叔节点存在，并且颜色是红色，则直接修改颜色即可，并且把祖父节点变为当前节点，变成另外两种情况
     * (如果叔叔节点不存在直接右旋转即可变成红黑树)
     * 4.3，如果当前节点是父节点的右边，则进行左旋(左旋是以父节点为节点，与右子节点旋转)
     * 4.4，否则最后一种情况，即，当前节点是父节点的左节点，则进行右旋(右旋是以父节点为节点，与左子节点旋转)
     * @param node
     */
    public void fixUp(Node node) {
        while (node.parent != null && node.parent.color == RED) {
            final Node pNode = node.parent;
            final Node ppNode = pNode.parent;
            if (pNode == ppNode.left) {
                Node prNode = ppNode.right;
                if (prNode != null && prNode.color == RED) {
                    pNode.color = BLACK;
                    prNode.color = BLACK;
                    ppNode.color = RED;
                    node = ppNode;
                } else if (node == pNode.right) {
                    node = pNode;
                    leftRatation(node);
                } else {
                    pNode.color = BLACK;
                    ppNode.color = RED;
                    rightRatation(ppNode);
                }

            } else {
                Node plNode = ppNode.left;
                if (plNode != null && plNode.color == RED) {
                    pNode.color = BLACK;
                    plNode.color = BLACK;
                    ppNode.color = RED;
                    node = ppNode;
                } else if (node == pNode.left) {
                    node = pNode;
                    rightRatation(node);
                } else {
                    pNode.color = BLACK;
                    ppNode.color = RED;
                    leftRatation(ppNode);
                }
            }
        }
        ROOT.color = BLACK;
    }

    public Node get(E ele) {
        if (ele == null) {
            throw new IllegalArgumentException("value can not be null");
        }
        return get(ele, ROOT);
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

    public void insert(E ele) {
        if (ele == null) {
            throw new IllegalArgumentException("value can not be null");
        }
        if (ROOT == null) {
            ROOT = initNode(ele);
            ROOT.color = BLACK;
            return ;
        }
        insert(initNode(ele), ROOT);
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
        fixUp(node);
    }

    private void reCreateRoot(Node node) {
        ROOT = node;
        ROOT.color = BLACK;
    }

    private Node initNode(E ele) {
        return new Node(null, null, ele, null, RED);
    }

    private Node emptyNode() {
        return new Node(null, null, null, null, RED);
    }

    private static class Node<E extends Comparable> {
        Node left;
        Node right;
        Node parent;
        String color;
        E ele;
        public Node(Node parent,Node left, E ele, Node right,String color) {
            this.parent = parent;
            this.left = left;
            this.right = right;
            this.ele = ele;
            this.color = color;
        }
    }
}
