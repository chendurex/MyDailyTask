package com.algorithms;

/**
 * Created by Administrator on 2016/7/27.
 */
public class RedBlackTree<E extends Comparable> {

    public static void main(String[] args) {
        RedBlackTree tree = new RedBlackTree(5);
        int [] array = {1,10,15,20};
        for (int i : array) {
            tree.insert(i);
        }
        //tree.insert(25);tree.insert(18);tree.insert(8);tree.insert(21);
        //tree.insert(29);tree.insert(31);tree.insert(11);tree.insert(17);

        for (int i : array) {
            Node node = tree.get(i);
            System.out.println("value = "+node.ele + " ,color = "+ node.color);
        }
    }


    private final int RED = 0;
    private final int BLACK = 1;
    private final Node empty = new Node(null, null, null, null, BLACK);
    private final Node SENTINEL = new Node(empty, empty, null, empty, BLACK);
    private Node ROOT;

    public RedBlackTree(E ele) {
        ROOT = new Node(SENTINEL, SENTINEL, ele, SENTINEL, BLACK);
    }
    /**
     * 左旋是将当前节点与右节点进行逆时针旋转，那么我们规定传入的节点为node，右节点为rNode，父节点为pNode
     * 1，判断rNode的左子节点是否为哨兵，如果不是则修改父节点指针
     * 2，判断pNode是否为哨兵，如果是则为ROOT节点，则将rNode改为ROOT节点
     * 3，判断node节点是pNode的左节点则修改为pNode的左节点指针，否则修改为右节点指针
     * 4，修改指针对应的关系
     * @param node
     */
    public void leftRatation(Node node) {
        if (node == SENTINEL) {
            return ;
        }
        Node rNode = node.right;
        final Node pNode = node.parent;
        final Node rNodeLeft = rNode.left;
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
        if (node == SENTINEL) {
            return ;
        }
        final Node lNode = node.left;
        final Node pNode = node.parent;
        if (lNode.right != SENTINEL) {
            lNode.right.parent = node.left;
        }
        if (pNode == SENTINEL) {
            node = ROOT;
        } else if (pNode.left == node) {
            pNode.left = lNode;
        } else {
            pNode.right = lNode;
        }
        lNode.right = node;
        lNode.parent = pNode;

        node.parent = lNode;
        node.left = lNode.right;
    }

    /**
     * 1，如果节点的父节点为黑色，是不是违反任何条件的，所以无需操作
     * 2，如果节点的父节点是ROOT节点，则直接修改颜色为黑色即可
     * 3，如果当前节点的父节点是红色，且祖父节点的另一个子节点(叔叔节点)是红色。此时父节点的父节点一定存在，否则插入前
     * 就已经不是红黑树。于此同时，又分为父节点是祖父节点的左孩子还是右孩子，根据对称性，我们只要解开一个方向就可以了。
     * @param node
     */
    public void fixUp(Node node) {
        if (node.parent.color != BLACK) {
            if (node == ROOT) {
                node.color = BLACK;
            } else {
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
                        } else if (node == pNode.right) {
                            node = pNode;
                            leftRatation(node);
                        } else {
                            pNode.color = BLACK;
                            ppNode.color = RED;
                            rightRatation(ppNode);
                        }
                    }
                }
            }
            ROOT.color = BLACK;
        }

    }

    public Node get(E ele) {
        if (ele == null) {
            throw new IllegalArgumentException("value can not be null");
        }
        return get(ele, ROOT);
    }

    private Node get(E ele, Node node) {
        while (node != SENTINEL) {
            final int compare = ele.compareTo(node.ele);
            if (compare == 0) {
                return node;
            } else if (compare < 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return SENTINEL;
    }

    public void insert(E ele) {
        if (ele == null) {
            throw new IllegalArgumentException("value can not be null");
        }
        insert(initNode(ele), ROOT);
    }

    private void insert(Node node, Node pNode) {
        Node<E> parent = pNode;
        while (pNode != SENTINEL) {
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



    private Node initNode(E ele) {
        return new Node(SENTINEL, SENTINEL, ele, SENTINEL, RED);
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
