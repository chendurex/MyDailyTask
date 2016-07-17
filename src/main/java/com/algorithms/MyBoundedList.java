package com.algorithms;

import java.util.NoSuchElementException;

/**
 * Created by Administrator on 2016/7/17.
 */
public class MyBoundedList<E> {
    public static void main(String[] args) {
        MyBoundedList<Integer> list = new MyBoundedList<>(50);
        for (int i = 0; i < 20; i++) {
            list.add(i);
            //System.out.println(list.remove());
        }
        for (int i = 0; i < 20; i++) {
           //list.add(i);
            System.out.println(list.remove());
        }
    }

    private int size;
    private Node<E> head;
    private Node<E> tail;
    private int capacity;

    public MyBoundedList(int capacity) {
        this.capacity = capacity;
    }

    public MyBoundedList() {
        this(10);
    }

    public void add(E e) {
        if (isFull()) {
            throw new IllegalArgumentException("list if full");
        }
        Node<E> newNode = new Node<>(head, e, tail);
        final Node<E> hNode = head;
        head = newNode;
        if (hNode == null) {
            tail = newNode;
            head.next = newNode;
            tail.pre = newNode;
        } else {
            hNode.next = head;
            head.pre = hNode;
            head.next = tail;
        }
        size ++;
    }

    public E remove() {
        final Node<E> tNode = tail;
        if (tNode == null) {
            throw new NoSuchElementException();
        }
        if (head == tail) {
            head = tail = null;
            tNode.pre = tNode.next = null;
        } else {
            final Node<E> nNode = tNode.next;
            tail = nNode;
            nNode.pre = head;
            tNode.pre = tNode.next = null;
        }

        size --;
        return tNode.ele;
    }

    private boolean isFull() {
        return capacity == size;
    }

    private static class Node<E> {
        Node<E> pre;
        Node<E> next;
        E ele;
        public Node(Node<E> pre, E ele, Node<E> next) {
            this.next = next;
            this.ele = ele;
            this.pre = pre;
        }
    }
}
